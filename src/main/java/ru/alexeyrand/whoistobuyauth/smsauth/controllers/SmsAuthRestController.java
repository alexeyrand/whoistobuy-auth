package ru.alexeyrand.whoistobuyauth.smsauth.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexeyrand.whoistobuyauth.dtos.TelephoneCodeResponse;
import ru.alexeyrand.whoistobuyauth.services.JwtSimpleService;
import ru.alexeyrand.whoistobuyauth.dtos.TelephoneCodeRequest;
import ru.alexeyrand.whoistobuyauth.services.TelephoneCodeService;


@RestController
@RequestMapping("api/v1/sms-auth")
@RequiredArgsConstructor
@Slf4j
public class SmsAuthRestController {
    private final TelephoneCodeService telephoneCodeService;
    private final JwtSimpleService jwtSimpleService;

    @PostMapping("/generate")
    public ResponseEntity<TelephoneCodeRequest> generateTelephoneCode(@RequestBody TelephoneCodeRequest telephoneCodeRequest) {
        log.info("Starting SMS code generation for telephone: {}", telephoneCodeRequest.getTelephone());
        String code = telephoneCodeService.generateTelephoneCode(telephoneCodeRequest);
        TelephoneCodeResponse response = TelephoneCodeResponse.builder()
                .telephone(telephoneCodeRequest.getTelephone())
                .build();
        if (code == null) {
            response.setStatus("SUCCESS");
            response.setMessage("Code sent successfully");
            return ResponseEntity.ok(telephoneCodeRequest);
        }
        log.warn("Code already requested for telephone: {}", telephoneCodeRequest.getTelephone());
        response.setStatus("ALREADY_REQUESTED");
        response.setMessage("Code " + code + " sent by phone number: " + telephoneCodeRequest.getTelephone());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(telephoneCodeRequest);
    }

    @PostMapping("/verify")
    public ResponseEntity<TelephoneCodeResponse> verifyTelephoneCode(@RequestBody TelephoneCodeRequest telephoneCodeRequest) {
        log.warn("Начинаю верификацию кода");
        boolean isVerified = telephoneCodeService.verifyTelephoneCode(telephoneCodeRequest);
        if (isVerified) {
            log.warn("Закончил верификацию смс. Генерируем JWT токен");
            String token = jwtSimpleService.generateToken(telephoneCodeRequest.getTelephone());
            TelephoneCodeResponse response = TelephoneCodeResponse.builder()
                    .telephone(telephoneCodeRequest.getTelephone())
                    .token(token)
                    .status("SUCCESS")
                    .message("Token success generated")
                    .build();

            return ResponseEntity.ok(response);
        } else {
            TelephoneCodeResponse response = TelephoneCodeResponse.builder()
                    .telephone(telephoneCodeRequest.getTelephone())
                    .status("CODE_NOT_VALID")
                    .message("Verification failed")
                    .build();
            log.warn("Верификация по смс не удалась. Неверный код: " + telephoneCodeRequest.getCode());
            return ResponseEntity.ok(response);
        }

    }
}

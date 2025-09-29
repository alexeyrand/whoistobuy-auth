package ru.alexeyrand.whoistobuyauth.smsauth.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexeyrand.whoistobuyauth.smsauth.entities.TelephoneCode;
import ru.alexeyrand.whoistobuyauth.smsauth.services.TelephoneCodeService;


@RestController
@RequestMapping("api/v1/sms-auth")
@RequiredArgsConstructor
@Slf4j
public class MtsController {
    private final TelephoneCodeService telephoneCodeService;

    @PostMapping("/generate")

    public ResponseEntity<TelephoneCode> generateTelephoneCode(@RequestBody TelephoneCode telephoneCode) {
        log.info("Starting SMS code generation for telephone: {}", telephoneCode.getTelephone());
        String code = telephoneCodeService.generateTelephoneCode(telephoneCode);
        if (code == null) {
            telephoneCode.setStatus("Code has already sent");
            return ResponseEntity.ok(telephoneCode);
        }
        log.info("SMS code generated successfully for telephone: {}", telephoneCode.getTelephone());
        telephoneCode.setStatus("Code " + code + " sent by phone number: " + telephoneCode.getTelephone());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(telephoneCode);
    }

    @GetMapping("/verify_code/{code}")
    public ResponseEntity<String> verifyTelephoneCode(@PathVariable String code) {
        TelephoneCode telephoneCode = TelephoneCode.builder().code(code).build();
        System.out.println("Начинаю верификацию кода");
        boolean isVerified = telephoneCodeService.verifyTelephoneCode(telephoneCode);
        if (isVerified) {
            System.out.println("Закончил верификацию смс");
            return ResponseEntity.ok("ok");
        } else {
            System.out.println("Верификация по смс не удалась. Неверный код");
            return ResponseEntity.ok("Верификация по смс не удалась. Неверный код: " + code);
        }

    }





}

package ru.alexeyrand.whoistobuyauth.smsauth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexeyrand.whoistobuyauth.smsauth.dto.SmsAuthRequestDto;
import ru.alexeyrand.whoistobuyauth.smsauth.entities.TelephoneCode;
import ru.alexeyrand.whoistobuyauth.smsauth.services.CodeGeneratorService;
import ru.alexeyrand.whoistobuyauth.smsauth.services.TelephoneCodeService;
import ru.alexeyrand.whoistobuybase.entities.User;
import ru.alexeyrand.whoistobuybase.rest.WitbHttpClient;
import ru.alexeyrand.whoistobuybase.services.UserService;

@RestController
@RequestMapping("api/v1/sms-auth")
@RequiredArgsConstructor
public class MtsController {
    private final UserService userService;
    private final WitbHttpClient witbHttpClient;
    private final TelephoneCodeService telephoneCodeService;

    @GetMapping("/generate_code/{telephone}")
    public ResponseEntity<String> generateCode(@PathVariable String telephone) {
        TelephoneCode telephoneCode = TelephoneCode.builder().telephone(telephone).build();
        System.out.println("Начинаю отправку смс");
        String code = telephoneCodeService.generateTelephoneCode(telephoneCode);
        if (code == null) {
            return ResponseEntity.ok("Код уже запрошен");
        }
        System.out.println("Закончил отправку смс");
        return ResponseEntity.ok("Код отправлен по номеру телефона: " + telephone);
    }

    @GetMapping("/verify_code")
    public ResponseEntity<String> verifyCode() {
        System.out.println("Начинаю верификацию кода");
        witbHttpClient.sendMessagePost("https://api.exolve.ru/messaging/v1/SendSMS", "{\"number\": \"79346626088\", \"destination\": \"79150187848\", \"text\": \"test\"}");
        System.out.println("Закончил отправку смс");
        return ResponseEntity.ok("ok");
    }





}

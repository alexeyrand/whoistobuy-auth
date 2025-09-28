package ru.alexeyrand.whoistobuyauth.smsauth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexeyrand.whoistobuyauth.smsauth.entities.TelephoneCode;
import ru.alexeyrand.whoistobuyauth.smsauth.services.TelephoneCodeService;


@RestController
@RequestMapping("api/v1/sms-auth")
@RequiredArgsConstructor
public class MtsController {
    private final TelephoneCodeService telephoneCodeService;

    @GetMapping("/generate_code/{telephone}")
    public ResponseEntity<String> generateTelephoneCode(@PathVariable String telephone) {
        TelephoneCode telephoneCode = TelephoneCode.builder().telephone(telephone).build();
        System.out.println("Начинаю отправку смс");
        String code = telephoneCodeService.generateTelephoneCode(telephoneCode);
        if (code == null) {
            return ResponseEntity.ok("Код уже запрошен");
        }
        System.out.println("Закончил отправку смс");
        return ResponseEntity.ok("Код " + code + " отправлен по номеру телефона: " + telephone);
    }

    @GetMapping("/verify_code/{code}")
    public ResponseEntity<String> verifyTelephoneCode(@PathVariable String code) {
        TelephoneCode telephoneCode = TelephoneCode.builder().code(code).build();
        System.out.println("Начинаю верификацию кода");
        boolean isVerify = telephoneCodeService.verifyTelephoneCode(telephoneCode);
        if (isVerify) {
            System.out.println("Закончил верификацию смс");
            return ResponseEntity.ok("ok");
        } else {
            System.out.println("Верификация по смс не удалась. Неверный код");
            return ResponseEntity.ok("Верификация по смс не удалась. Неверный код: " + code);
        }

    }





}

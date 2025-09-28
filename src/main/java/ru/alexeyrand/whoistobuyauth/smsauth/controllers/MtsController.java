package ru.alexeyrand.whoistobuyauth.smsauth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexeyrand.whoistobuyauth.smsauth.dto.SmsAuthRequestDto;
import ru.alexeyrand.whoistobuyauth.smsauth.services.CodeGeneratorService;
import ru.alexeyrand.whoistobuybase.entities.User;
import ru.alexeyrand.whoistobuybase.rest.WitbHttpClient;
import ru.alexeyrand.whoistobuybase.services.UserService;

@RestController
@RequestMapping("api/v1/sms-auth")
@RequiredArgsConstructor
public class MtsController {
    private final UserService userService;
    private final WitbHttpClient witbHttpClient;
    private final CodeGeneratorService codeGeneratorService;

    @GetMapping("/generate_code")
    public ResponseEntity<String> generateCode() {
        System.out.println("Начинаю отправку смс");
        String code = codeGeneratorService.generateCode();
        System.out.println(code);

        witbHttpClient.sendMessagePost("https://api.exolve.ru/messaging/v1/SendSMS", "{\"number\": \"79346626088\", \"destination\": \"79150187848\", \"text\": \"test\"}");
        System.out.println("Закончил отправку смс");
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/verify_code")
    public ResponseEntity<String> verifyCode() {
        System.out.println("Начинаю верификацию кода");
        witbHttpClient.sendMessagePost("https://api.exolve.ru/messaging/v1/SendSMS", "{\"number\": \"79346626088\", \"destination\": \"79150187848\", \"text\": \"test\"}");
        System.out.println("Закончил отправку смс");
        return ResponseEntity.ok("ok");
    }





}

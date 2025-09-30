package ru.alexeyrand.whoistobuyauth.smsauth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexeyrand.whoistobuyauth.smsauth.dto.SmsAuthRequestDto;
import ru.alexeyrand.whoistobuybase.entities.User;
import ru.alexeyrand.whoistobuybase.services.UserService;

@RestController
@RequestMapping("api/v1/sms-auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final UserService userService;

    /**
     * Регистрация пользователя
     * @param request - запрос регистрации
     * @return ok()
     */
    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SmsAuthRequestDto request) {
        User user = new User();
        user.setTelephone(request.getTelephone());
        user.setUsername(request.getUsername());
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    /**
     * Авторизация пользователя
     * @param request - запрос авторизации
     * @return ok()
     */
    @PostMapping("/signin")
    public ResponseEntity<Boolean> signIn(@RequestBody SmsAuthRequestDto request) {
        boolean ex = userService.existsByTelephone(request.getTelephone());
        return ResponseEntity.ok(ex);
    }

}

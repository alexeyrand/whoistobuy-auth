package ru.alexeyrand.whoistobuyauth.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexeyrand.whoistobuyauth.dtos.SignInRequest;
import ru.alexeyrand.whoistobuyauth.dtos.SignInResponse;
import ru.alexeyrand.whoistobuyauth.dtos.SignUpRequest;
import ru.alexeyrand.whoistobuybase.entities.User;
import ru.alexeyrand.whoistobuyauth.services.AuthService;
import ru.alexeyrand.whoistobuybase.exceptions.IHandleException;
import ru.alexeyrand.whoistobuybase.exceptions.LogicalException;
import ru.alexeyrand.whoistobuybase.services.UserService;

import javax.security.auth.login.LoginException;


@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController implements IHandleException {
    private final AuthService authService;
    private final UserService userService;

    /**
     * Регистрация пользователя
     * @param request - запрос регистрации
     * @return ok()
     */
    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest request) {
        if (userService.existsByUsername(request.getUsername())) {
            throw new LogicalException("Пользователь с таким именем уже существует. Укажите другое имя.", HttpStatus.CONFLICT);
        }
        if (userService.existsByEmail(request.getEmail())) {
            throw new LogicalException("Пользователь с таким адресом электронной почты уже существует. Укажите другое адрес.", HttpStatus.CONFLICT);
        }
        User user = authService.register(request);
        return ResponseEntity.ok(user);
    }

    /**
     * Авторизация пользователя
     * @param request - запрос авторизации
     * @return response(jwt token)
     */
    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request) {
        SignInResponse response = authService.verify(request);
        return ResponseEntity.ok(response);
    }

    @Override
    @ExceptionHandler
    public ResponseEntity<LogicalException> handleLogicalException(LogicalException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e);
    }
}

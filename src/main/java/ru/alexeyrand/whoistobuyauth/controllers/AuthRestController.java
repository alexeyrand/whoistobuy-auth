package ru.alexeyrand.whoistobuyauth.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexeyrand.whoistobuyauth.dtos.SignInRequest;
import ru.alexeyrand.whoistobuyauth.dtos.SignInResponse;
import ru.alexeyrand.whoistobuyauth.dtos.SignUpRequest;
import ru.alexeyrand.whoistobuybase.entities.User;
import ru.alexeyrand.whoistobuyauth.services.AuthService;
import ru.alexeyrand.whoistobuybase.services.UserService;


@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthService authService;
    private final UserService userService;


    /**
     * Регистрация пользователя
     * @param request - запрос регистрации
     * @return ok()
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different name");
        }
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different email");
        }
        User user = authService.register(request);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
        SignInResponse response = authService.verify(request);
        return ResponseEntity.ok(response);
    }

}

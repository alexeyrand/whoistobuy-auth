package ru.alexeyrand.whoistobuyauth.dtos;

import lombok.Getter;

/**
 * Запрос на регистрацию
 */
@Getter
public class SignUpRequest {

    private String username;
    private String password;
    private String email;

}

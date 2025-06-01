package ru.alexeyrand.whoistobuyauth.dtos;

import lombok.Getter;

/**
 * Запрос на аутентификацию
 */
@Getter
public class SignInRequest {
    private String username;
    private String password;
}

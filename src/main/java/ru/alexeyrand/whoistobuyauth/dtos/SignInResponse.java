package ru.alexeyrand.whoistobuyauth.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Ответ на аутентификацию
 */
@Getter
@Setter
public class SignInResponse {
    private String username;
    private String token;
}

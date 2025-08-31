package ru.alexeyrand.whoistobuyauth.smsauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsAuthRequestDto {
    String telephone;
    String username;
}

package ru.alexeyrand.whoistobuyauth.smsauth.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Builder
public class TelephoneCode {
    String telephone;
    String code;
}

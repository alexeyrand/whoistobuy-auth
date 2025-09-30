package ru.alexeyrand.whoistobuyauth.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor()
public class TelephoneCodeResponse {
    private String token;
    private String telephone;
    private String type = "Bearer";
    private String status;
    private String message;
}

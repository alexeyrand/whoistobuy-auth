package ru.alexeyrand.whoistobuyauth.smsauth.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TelephoneCode {
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^7\\d{10}$", message = "Phone must be in format 7XXXXXXXXXX")
    private String telephone;
    @Pattern(regexp = "\\d{6}", message = "Code must be 6 digits")
    private String code;
    private String status;
    private String message;
}

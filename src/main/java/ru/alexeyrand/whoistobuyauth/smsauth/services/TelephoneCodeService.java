package ru.alexeyrand.whoistobuyauth.smsauth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexeyrand.whoistobuybase.rest.WitbHttpClient;

@Service
@RequiredArgsConstructor
public class TelephoneCodeService {
    private final TelephoneCodeRedisService telephoneCodeRedisService;
    private final CodeGeneratorService codeGeneratorService;
    private final WitbHttpClient witbHttpClient;

    public String generateTelephoneCode(String telephone) {
        String code = codeGeneratorService.generateCode();
        telephoneCodeRedisService.saveCode(telephone, code);
        return code;
    }

}

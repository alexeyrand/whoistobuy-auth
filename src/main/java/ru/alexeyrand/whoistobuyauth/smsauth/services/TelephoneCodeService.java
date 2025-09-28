package ru.alexeyrand.whoistobuyauth.smsauth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexeyrand.whoistobuyauth.smsauth.entities.TelephoneCode;
import ru.alexeyrand.whoistobuybase.rest.WitbHttpClient;

@Service
@RequiredArgsConstructor
public class TelephoneCodeService {
    private final TelephoneCodeRedisService telephoneCodeRedisService;
    private final CodeGeneratorService codeGeneratorService;
    private final WitbHttpClient witbHttpClient;

    public String generateTelephoneCode(TelephoneCode telephoneCode) {
        if (telephoneCodeRedisService.hasCode(telephoneCode))
            return null;
        String code = codeGeneratorService.generateCode();
        System.out.println(code);
        telephoneCode.setCode(code);
        witbHttpClient.sendMessagePost("https://api.exolve.ru/messaging/v1/SendSMS", "{\"number\": \"79346626088\", \"destination\": \"79150187848\", \"text\": \"test\"}");
        telephoneCodeRedisService.saveCode(telephoneCode);
        return code;
    }



    public boolean verifyTelephoneCode(TelephoneCode telephoneCode) {
        if (telephoneCodeRedisService.hasCode(telephoneCode)) {
            telephoneCodeRedisService.deleteCode(telephoneCode);
            return true;
        }
        return false;
    }
}

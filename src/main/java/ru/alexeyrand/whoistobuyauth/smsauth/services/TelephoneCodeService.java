package ru.alexeyrand.whoistobuyauth.smsauth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexeyrand.whoistobuyauth.dtos.TelephoneCodeRequest;
import ru.alexeyrand.whoistobuybase.rest.WitbHttpClient;

@Service
@RequiredArgsConstructor
public class TelephoneCodeService {
    private final TelephoneCodeRedisService telephoneCodeRedisService;
    private final CodeGeneratorService codeGeneratorService;
    private final WitbHttpClient witbHttpClient;

    public String generateTelephoneCode(TelephoneCodeRequest telephoneCodeRequest) {
        if (telephoneCodeRedisService.hasCode(telephoneCodeRequest))
            return null;
        String code = codeGeneratorService.generateCode();
        System.out.println(code);
        telephoneCodeRequest.setCode(code);
        witbHttpClient.sendMessagePost("https://api.exolve.ru/messaging/v1/SendSMS", "{\"number\": \"79346626088\", \"destination\": \"79150187848\", \"text\": \"test\"}");
        telephoneCodeRedisService.saveCode(telephoneCodeRequest);
        return code;
    }



    public boolean verifyTelephoneCode(TelephoneCodeRequest telephoneCodeRequest) {
        if (telephoneCodeRedisService.hasCode(telephoneCodeRequest)) {
            telephoneCodeRedisService.deleteCode(telephoneCodeRequest);
            return true;
        }
        return false;
    }
}

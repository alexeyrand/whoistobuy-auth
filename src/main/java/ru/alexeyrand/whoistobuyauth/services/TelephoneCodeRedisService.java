package ru.alexeyrand.whoistobuyauth.services;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import ru.alexeyrand.whoistobuyauth.dtos.TelephoneCodeRequest;

import java.time.Duration;

@Service
public class TelephoneCodeRedisService {
    private final StringRedisTemplate redisTemplate;
    private final ValueOperations<String, String> valueOperations;

    private static final long EXPIRATION_TIME = 5;

    public TelephoneCodeRedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    /**
     * Сохраняет код для указанного номера телефона на 5 минут
     */
    public void saveCode(TelephoneCodeRequest telephoneCodeRequest) {
        String key =  generateKey(telephoneCodeRequest.getTelephone());
        String code = telephoneCodeRequest.getCode();
        valueOperations.set(key, code, Duration.ofMinutes(EXPIRATION_TIME));
    }

    /**
     * Получает код для указанного номера телефона
     */
    public String getCode(String telephone) {
        String key = (String) generateKey(telephone);
        return valueOperations.get(key);
    }

    /**
     * Проверяет существование кода для номера телефона
     */
    public boolean hasCode(TelephoneCodeRequest telephoneCodeRequest) {
        String key = generateKey(telephoneCodeRequest.getTelephone());
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * Удаляет код для указанного номера телефона
     */
    public void deleteCode(TelephoneCodeRequest telephoneCodeRequest) {
        String key = generateKey(telephoneCodeRequest.getTelephone());
        redisTemplate.delete(key);
    }

    /**
     * Проверяет код для указанного номера телефона
     */
    public boolean verifyCode(String telephone, String code) {
        String storedCode = getCode(telephone);
        return storedCode != null && storedCode.equals(code);
    }

    private String generateKey(String telephone) {
        return "verification_code:" + telephone;
    }
}
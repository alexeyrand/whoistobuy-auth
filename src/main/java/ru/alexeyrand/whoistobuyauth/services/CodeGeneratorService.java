package ru.alexeyrand.whoistobuyauth.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CodeGeneratorService {

    /**
     * Генерирует случайный цифровой код указанной длины
     */
    public String generateCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }

        return code.toString();
    }

    /**
     * Генерирует 6-значный код (по умолчанию)
     */
    public String generateCode() {
        return generateCode(6);
    }
}

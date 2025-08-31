package ru.alexeyrand.whoistobuyauth.springtest;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class JavaBean {

    String name;

    public JavaBean() {
        name = "f";
        System.out.println("Конструктор класса JavaBean");
    }

    @PostConstruct
    public void init() {
        System.out.println("Конструктор init() в JavaBean");
    }
}

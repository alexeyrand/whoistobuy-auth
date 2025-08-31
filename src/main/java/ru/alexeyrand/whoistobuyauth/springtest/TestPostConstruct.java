package ru.alexeyrand.whoistobuyauth.springtest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestPostConstruct implements BeanPostProcessor {

    List<Object> beans = new ArrayList<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        beans.add(bean);
        if (beanName.contains("javaBean"))
            System.out.println("Before Init in BPP");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.contains("javaBean"))
            System.out.println("After Init in BPP");
        return bean;
    }
}

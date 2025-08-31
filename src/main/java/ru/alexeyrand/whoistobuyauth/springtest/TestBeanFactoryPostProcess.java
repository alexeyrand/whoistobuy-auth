package ru.alexeyrand.whoistobuyauth.springtest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class TestBeanFactoryPostProcess implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanDef = beanFactory.getBeanDefinitionNames();
        System.out.println();
        BeanDefinition bf = beanFactory.getBeanDefinition("javaBean");
        bf.getBeanClassName();
    }
}

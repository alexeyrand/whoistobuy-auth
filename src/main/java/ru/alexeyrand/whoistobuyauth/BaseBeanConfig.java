package ru.alexeyrand.whoistobuyauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.alexeyrand.whoistobuybase.fsm.FinalStateMachine;
import ru.alexeyrand.whoistobuybase.rest.WitbHttpClient;



@Configuration
public class BaseBeanConfig {

    @Bean
    public WitbHttpClient witbHttpClient() {
        return new WitbHttpClient();
    }


}

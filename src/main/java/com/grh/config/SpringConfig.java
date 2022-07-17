package com.grh.config;

import com.grh.other.MainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig{

        @Bean
        MainService getMainService(){
            return new MainService();
        }
}
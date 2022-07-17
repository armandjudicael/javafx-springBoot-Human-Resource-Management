package com.grh.config;

import com.grh.GrhApplication;
import com.grh.other.MainService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;

@Configuration
public class SpringConfig{
        @Bean
        MainService getMainService(){
            return new MainService();
        }
}
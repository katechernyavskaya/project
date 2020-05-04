package com.gmail.ek.chernyavskaya.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class,
        scanBasePackages = {
                "com.gmail.ek.chernyavskaya.boot",
                "com.gmail.ek.chernyavskaya.service",
                "com.gmail.ek.chernyavskaya.repository"}
)
public class WebModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebModuleApplication.class, args);
    }


}
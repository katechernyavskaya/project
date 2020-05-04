package com.gmail.ek.chernyavskaya.boot.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.gmail.ek.chernyavskaya.service", "com.gmail.ek.chernyavskaya.repository"})
public class AppConfig {
}
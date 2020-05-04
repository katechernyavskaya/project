package com.gmail.ek.chernyavskaya.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.gmail.ek.chernyavskaya.repository.model.RoleEnum.ADMINISTRATOR;
import static com.gmail.ek.chernyavskaya.repository.model.RoleEnum.CUSTOMER_USER;
import static com.gmail.ek.chernyavskaya.repository.model.RoleEnum.SECURE_API_USER;
import static com.gmail.ek.chernyavskaya.repository.model.RoleEnum.SALE_USER;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users", "/users/add", "/users{id}/password", "/user/{id}/role", "/reviews", "/reviews/{id}/delete")
                .hasRole(ADMINISTRATOR.name())
                .antMatchers("/articles/add", "/articles/add/{id}", "/articles/add/{id}/edit", "/comments/{id}/delete",
                        "/items/{id}/delete", "/items/{id}/copy", "/orders", "/orders/{id}", "/orders/{id}/status")
                .hasRole(SALE_USER.name())
                .antMatchers("/users/profile", "/users/updatePassword", "/orders/add/{itemId}/quantity/{quantityId}", "/orders/myOrders", "/reviews/add")
                .hasRole(CUSTOMER_USER.name())
                .antMatchers("/api/**")
                .hasRole(SECURE_API_USER.name())
                .antMatchers("/items", "/items/{id}", "/articles")
                .hasAnyRole(CUSTOMER_USER.name(), SALE_USER.name())
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/welcome")
                .permitAll()
                .and()
                .logout()
                .and()
                .csrf()
                .disable()
                .httpBasic();
    }
}
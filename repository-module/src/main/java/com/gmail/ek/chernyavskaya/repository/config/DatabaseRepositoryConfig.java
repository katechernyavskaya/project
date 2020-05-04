package com.gmail.ek.chernyavskaya.repository.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class DatabaseRepositoryConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityMangerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.gmail.ek.chernyavskaya.repository.model");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }
}

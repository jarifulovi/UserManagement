package com.example.usermanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.usermanagement.infrastructure.persistence")
public class BeanConfig {
    // Add any additional beans or configurations here if needed
}
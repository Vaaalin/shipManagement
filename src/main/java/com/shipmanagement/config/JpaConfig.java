package com.shipmanagement.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.shipmanagement.model")
@EnableJpaRepositories(basePackages = "com.shipmanagement.repository")
public class JpaConfig {
    // Configuration is done through annotations
}

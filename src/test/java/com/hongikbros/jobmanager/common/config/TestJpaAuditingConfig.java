package com.hongikbros.jobmanager.common.config;

import java.util.Optional;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@TestConfiguration
@EnableJpaAuditing(auditorAwareRef = "testAuditorProvider")
public class TestJpaAuditingConfig {
    @Bean
    public AuditorAware<String> testAuditorProvider() {
        return () -> Optional.of("Test auditor");
    }
}

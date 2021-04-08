package com.hongikbros.jobmanager.common.core.config;

import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hongikbros.jobmanager.security.oauth2.CustomOAuth2UserService;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {

    private final HttpSession httpSession;

    public JpaAuditingConfig(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return () -> Optional.of(String.valueOf(httpSession.getAttribute(
                    CustomOAuth2UserService.PRINCIPAL_OAUTHID_BEFORE_SAVING)));
        }
        return () -> Optional.of(authentication.getName());
    }
}

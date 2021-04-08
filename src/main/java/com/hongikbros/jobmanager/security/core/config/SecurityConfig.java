package com.hongikbros.jobmanager.security.core.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.hongikbros.jobmanager.security.oauth2.CustomOAuth2UserService;
import com.hongikbros.jobmanager.security.oauth2.CustomOauth2SuccessHandler;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOauth2SuccessHandler customOauth2SuccessHandler;
    private final Environment environment;

    public SecurityConfig(
            CustomOAuth2UserService customOAuth2UserService,
            CustomOauth2SuccessHandler customOauth2SuccessHandler,
            Environment environment) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customOauth2SuccessHandler = customOauth2SuccessHandler;
        this.environment = environment;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final ConfigMode config = ConfigMode.from(getActiveProfile());

        config.configApply(http, customOauth2SuccessHandler, customOAuth2UserService);
    }

    private String getActiveProfile() {
        return environment.getActiveProfiles().length > 0 ? environment.getActiveProfiles()[0] :
                "prod";
    }

}

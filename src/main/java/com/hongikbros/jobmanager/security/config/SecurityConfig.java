package com.hongikbros.jobmanager.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
        if (!isProdMode()) {
            setLocalMode(http);
        }
    }

    private void setLocalMode(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .httpBasic().disable()
                .formLogin().disable()
                .headers().frameOptions().sameOrigin()
                .and()
                    .csrf()
                        .csrfTokenRepository(new CookieCsrfTokenRepository())
                        .requireCsrfProtectionMatcher(new AntPathRequestMatcher("!/h2/**"))
                .and()
                    .authorizeRequests()
                    .antMatchers("/h2","/docs/**","/api/notice/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .logout()
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                .and()
                    .oauth2Login()
                        .successHandler(customOauth2SuccessHandler)
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService);
        //@formatter:on
    }

    private boolean isProdMode() {
        String profile =
                environment.getActiveProfiles().length > 0 ? environment.getActiveProfiles()[0] :
                        "prod";
        return profile.equals("prod");
    }

}
package com.hongikbros.jobmanager.common.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.hongikbros.jobmanager.common.domain.security.CustomOauth2SuccessHandler;
import com.hongikbros.jobmanager.member.application.oauth.CustomOAuth2UserService;

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
        if (isLocalMode()) {
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
                    .antMatcher("/**").authorizeRequests()
                    .antMatchers("/h2").permitAll()
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

    private boolean isLocalMode() {
        String profile =
                environment.getActiveProfiles().length > 0 ? environment.getActiveProfiles()[0] :
                        "dev";
        return profile.equals("dev");
    }

}

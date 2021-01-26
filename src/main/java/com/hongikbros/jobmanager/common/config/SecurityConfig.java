package com.hongikbros.jobmanager.common.config;

import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.hongikbros.jobmanager.member.application.oauth.CustomOAuth2UserService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final Environment environment;

    public SecurityConfig(
            CustomOAuth2UserService customOAuth2UserService,
            Environment environment) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.environment = environment;
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
                    .csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("!/h2/**"))
                .and()
                    .antMatcher("/**")
                    .authorizeRequests()
                    .antMatchers("/h2").permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                .and()
                    .oauth2Login()
                        // .loginPage("/")
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

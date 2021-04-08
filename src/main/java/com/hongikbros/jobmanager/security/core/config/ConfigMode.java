package com.hongikbros.jobmanager.security.core.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.hongikbros.jobmanager.common.function.ThrowingConsumer;
import com.hongikbros.jobmanager.security.exception.SecurityConfigException;
import com.hongikbros.jobmanager.security.oauth2.CustomOAuth2UserService;
import com.hongikbros.jobmanager.security.oauth2.CustomOauth2SuccessHandler;

// default profile prod
public enum ConfigMode {
    PROD("prod", ConfigMode::prodMode),
    DEV("dev", ConfigMode::devMode),
    TEST("test", ConfigMode::testMode);

    private final String profile;
    private final ThrowingConsumer<HttpSecurity, CustomOauth2SuccessHandler, CustomOAuth2UserService, Exception> config;

    ConfigMode(String profile,
            ThrowingConsumer<HttpSecurity, CustomOauth2SuccessHandler, CustomOAuth2UserService, Exception> config) {
        this.profile = profile;
        this.config = config;
    }

    public static ConfigMode from(String profile) {
        for (ConfigMode value : ConfigMode.values()) {
            if (value.profile.equals(profile)) {
                return value;
            }
        }
        return PROD;
    }

    public void configApply(HttpSecurity httpSecurity,
            CustomOauth2SuccessHandler customOauth2SuccessHandler,
            CustomOAuth2UserService customOAuth2UserService) throws SecurityConfigException {
        try {
            config.accept(httpSecurity, customOauth2SuccessHandler, customOAuth2UserService);
        } catch (Exception e) {
            throw new SecurityConfigException(e);
        }
    }

    private static void prodMode(HttpSecurity httpSecurity,
            CustomOauth2SuccessHandler customOauth2SuccessHandler,
            CustomOAuth2UserService customOAuth2UserService) {
        throw new UnsupportedOperationException();
    }

    private static void devMode(HttpSecurity http,
            CustomOauth2SuccessHandler customOauth2SuccessHandler,
            CustomOAuth2UserService customOAuth2UserService) throws Exception {
        //@formatter:off
        http
                .httpBasic().disable()
                .formLogin().disable()
                .headers().frameOptions().sameOrigin()
                .and()
                    .csrf()
                        .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
                .and()
                    .authorizeRequests()
                        .antMatchers("/h2","/docs/**").permitAll()
                        .anyRequest().authenticated()
                .and()
                    .sessionManagement()
                        .sessionFixation().changeSessionId()
                        .invalidSessionUrl("/")
                .and()
                    .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                .and()
                    .oauth2Login()
                        .loginPage("/")
                        .successHandler(customOauth2SuccessHandler)
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService);
        //@formatter:on
    }

    private static void testMode(HttpSecurity http,
            CustomOauth2SuccessHandler customOauth2SuccessHandler,
            CustomOAuth2UserService customOAuth2UserService) throws Exception {
        //@formatter:off
        http
                .httpBasic().disable()
                .formLogin().disable()
                    .headers().frameOptions().sameOrigin()
                .and()
                    .csrf()
                        .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
                        .ignoringAntMatchers("/h2/**")
                .and()
                    .authorizeRequests()
                        .antMatchers("/h2","/docs/**").permitAll()
                        .anyRequest().authenticated()
                .and()
                    .sessionManagement()
                        .sessionFixation().changeSessionId()
                        .invalidSessionUrl("/")
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                .and()
                    .oauth2Login()
                        .loginPage("/")
                        .successHandler(customOauth2SuccessHandler)
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService);
        //@formatter:on
    }

}

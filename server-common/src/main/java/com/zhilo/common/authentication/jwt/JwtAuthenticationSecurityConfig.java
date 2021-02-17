package com.zhilo.common.authentication.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * JWT 认证配置 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2021/2/9
 */
@Component
public class JwtAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        jwtAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider();
        jwtAuthenticationProvider.setUserDetailsService(userDetailsService);
        jwtAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        // 将 JwtAuthenticationProvider 加入到 AuthenticationManager 中管理
        http.authenticationProvider(jwtAuthenticationProvider)
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}

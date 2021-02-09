package com.zhilo.authentication;

import com.zhilo.authentication.jwt.JwtAuthenticationSecurityConfig;
import com.zhilo.constant.AuthConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Web 安全配置 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2019/9/18
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final AppLogoutSuccessHandler logoutSuccessHandler;

    private final JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, AppAuthenticationSuccessHandler authenticationSuccessHandler, AppAuthenticationFailureHandler authenticationFailureHandler, AppLogoutSuccessHandler logoutSuccessHandler, JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.jwtAuthenticationSecurityConfig = jwtAuthenticationSecurityConfig;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // JWT 认证相关配置
                .apply(jwtAuthenticationSecurityConfig)
                .and()
                .authorizeRequests()
                // 允许未经身份验证的访问 actuator 端点
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .antMatchers(AuthConstants.JWT_AUTH_PROCESSING_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                // .formLogin().and()
                // .httpBasic().and()
                .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .csrf().disable();

    }

}

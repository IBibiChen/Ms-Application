package com.zhilo.authorization;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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

    private final AuthTokenSecurityConfig authTokenSecurityConfig;

    public WebSecurityConfig(AuthTokenSecurityConfig authTokenSecurityConfig) {
        this.authTokenSecurityConfig = authTokenSecurityConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // JWT 认证相关配置
                .apply(authTokenSecurityConfig)
                .and()
                .authorizeRequests()
                .antMatchers("/users/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable();

    }

}

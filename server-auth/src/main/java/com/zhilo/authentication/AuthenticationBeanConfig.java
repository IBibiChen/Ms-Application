package com.zhilo.authentication;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 认证相关的扩展点配置 .
 * 配置在这里的 bean，业务系统都可以通过声明同类型或同名的 bean 来覆盖安全模块默认的配置
 *
 * @author BibiChen
 * @version v1.0
 * @since 2021/2/8
 */
@Configuration
public class AuthenticationBeanConfig {

    /**
     * 默认密码处理器
     *
     * @return DelegatingPasswordEncoder
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 默认认证器
     *
     * @return {@link AuthUserDetailsService}
     */
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new AuthUserDetailsService();
    }

}

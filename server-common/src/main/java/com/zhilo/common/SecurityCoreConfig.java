package com.zhilo.common;

import com.zhilo.common.properties.JwtSecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使 SecurityProperties 配置读取生效 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2021/2/9
 */
@Configuration
public class SecurityCoreConfig {

    @Bean
    @ConfigurationProperties(prefix = "jwt")
    public JwtSecurityProperties jwtSecurityProperties() {
        return new JwtSecurityProperties();
    }

}

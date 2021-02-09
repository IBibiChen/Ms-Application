package com.zhilo.common.properties;

import lombok.Data;

/**
 * 系统配置封装 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2021/2/9
 */
@Data
public class JwtSecurityProperties {

    /**
     * Request Headers：Authorization
     */
    private String header;

    /**
     * 令牌前缀，最后留个空格 Bearer
     */
    private String tokenStartWith;

    /**
     * 必须使用最少 88 位的 Base64 对该令牌进行编码
     */
    private String base64Secret;

    /**
     * 令牌有效期，单位秒
     */
    private Long expirationTimeInSecond;

}

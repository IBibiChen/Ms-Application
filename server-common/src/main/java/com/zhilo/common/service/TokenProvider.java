package com.zhilo.common.service;

import com.zhilo.common.model.AuthUser;
import com.zhilo.common.model.AuthUserToken;
import com.zhilo.common.properties.JwtSecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 操作工具类 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2021/2/9
 */
@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private final JwtSecurityProperties properties;
    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;

    public TokenProvider(JwtSecurityProperties properties) {
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        jwtBuilder = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256);
    }

    /**
     * 计算 Token 的过期时间
     *
     * @return 过期时间
     */
    private Date getExpirationTime() {
        return new Date(System.currentTimeMillis() + this.properties.getExpirationTimeInSecond() * 1000);
    }

    /**
     * 创建 Token 设置过期时间
     *
     * @param authentication 认证成功的 Authentication
     * @return token
     */
    public String createToken(Authentication authentication) {

        Date createTime = new Date();
        Date expirationTime = this.getExpirationTime();

        AuthUser principal = (AuthUser) authentication.getPrincipal();
        AuthUserToken authUserToken = new AuthUserToken(principal);

        // 颁发 token
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", authUserToken.getId());
        userInfo.put("username", authUserToken.getUsername());
        userInfo.put("authorities", authUserToken.getAuthorities());

        return jwtBuilder
                .setClaims(userInfo)
                .setIssuedAt(createTime)
                .setExpiration(expirationTime)
                .setSubject(authentication.getName())
                .compact();
    }

    // /**
    //  * 依据 Token 获取鉴权信息
    //  *
    //  * @param token token
    //  * @return Authentication
    //  */
    // Authentication getAuthentication(String token) {
    //     Claims claims = getClaims(token);
    //     AuthUser authUser = new AuthUser();
    //     authUser.setId(claims.get("id", Long.class));
    //     authUser.setUsername((String) claims.get("username"));
    //     authUser.setAuthorities(claims.get("authorities"));
    //     return new JwtAuthenticationToken(authUser, "******", authUser.getAuthorities());
    // }

    /**
     * 从 Token 中获取 Claim
     *
     * @param token token
     * @return claim
     */
    public Claims getClaims(String token) {
        try {
            return jwtParser
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            log.error("token 解析错误", e);
            throw new IllegalArgumentException("Token invalided.");
        }
    }

    /**
     * 获取 Token 的过期时间
     *
     * @param token token
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaims(token).getExpiration();
    }

    /**
     * 判断 Token 是否过期
     *
     * @param token token
     * @return 已过期返回 true，未过期返回 false
     */
    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 判断 Token 是否非法
     *
     * @param token token
     * @return 未过期返回 true，否则返回 false
     */
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

}

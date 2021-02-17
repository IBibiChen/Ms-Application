package com.zhilo.authorization;

import com.zhilo.common.authentication.jwt.JwtAuthenticationToken;
import com.zhilo.common.exception.AuthSecurityException;
import com.zhilo.common.properties.JwtSecurityProperties;
import com.zhilo.common.result.ResultCode;
import com.zhilo.common.service.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 过滤器 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2021/2/11
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final TokenProvider tokenProvider;
    private final JwtSecurityProperties properties;

    @Autowired
    public JwtAuthenticationTokenFilter(AuthenticationFailureHandler authenticationFailureHandler, TokenProvider tokenProvider, JwtSecurityProperties properties) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.tokenProvider = tokenProvider;
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 1. 从 header 里面获取 token
            String token = resolveToken(request);
            // 2. 检验 token 是否合法&是否过期，如果不合法或已过期直接抛异常；如果合法则放行

            Boolean isValidate = tokenProvider.validateToken(token);

            if (!isValidate) {
                AuthSecurityException exception = new AuthSecurityException(ResultCode.TOKEN_INVALID);
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }

            // 3. 如果校验成功，那么就将用户的信息设置到 SecurityContext 里面
            JwtAuthenticationToken authentication = (JwtAuthenticationToken) tokenProvider.getAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            AuthSecurityException exception = new AuthSecurityException(ResultCode.TOKEN_INVALID);
            authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
        }

    }

    /**
     * 从请求头中解析 Token
     *
     * @param request HttpServletRequest
     * @return Token
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(properties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(properties.getTokenStartWith())) {
            // 去掉令牌前缀
            return bearerToken.replace(properties.getTokenStartWith(), "");
        } else {
            log.debug("非法Token：{}", bearerToken);
        }
        return null;
    }

}

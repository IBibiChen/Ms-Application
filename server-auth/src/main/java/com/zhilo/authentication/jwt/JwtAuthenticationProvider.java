package com.zhilo.authentication.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * JWT 登录认证提供者 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2021/2/8
 */
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;

    /**
     * 使用 UserDetailsService 获取用户信息，然后重新组装一个已认证的 Authentication
     *
     * @param authentication Authentication
     * @return 认证成功的 Authentication
     * @throws AuthenticationException e
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        String username = (String) authenticationToken.getPrincipal();
        String password = (String) authenticationToken.getCredentials();

        // 先使用 userDetailsService 从数据库中获取用户，如果有异常情况抛出
        UserDetails user = null;
        try {
            user = userDetailsService.loadUserByUsername(username);

            // 检查用户状态
            if (!user.isEnabled()) {
                throw new DisabledException("账号被禁用");
            }

            // 密码比较
            if (!passwordEncoder.matches(password, user.getPassword())) {
                log.debug("Authentication failed: password does not match stored value");

                throw new BadCredentialsException("身份验证失败：密码错误");
            }

            // 检查完成，创建认证成功的 token 返回
            JwtAuthenticationToken authenticationResult = new JwtAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());
            // 将未认证之前存放到 token 中的请求的详情(ip、SessionId) copy 到认证成功后的 token 中
            authenticationResult.setDetails(authentication.getDetails());

            return authenticationResult;
        } catch (UsernameNotFoundException e) {
            log.debug("User '" + username + "' not found");
            throw e;
        } catch (DisabledException | BadCredentialsException e) {
            throw e;
        } catch (Exception ex) {
            throw new AuthenticationServiceException(ex.getMessage(), ex);
        }

    }

    /**
     * JwtAuthenticationToken 类型的 token 才支持处理
     *
     * @param authentication Authentication
     * @return 如果传入的 Authentication 为 JwtAuthenticationToken 类型的返回 true，否则返回 false
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}

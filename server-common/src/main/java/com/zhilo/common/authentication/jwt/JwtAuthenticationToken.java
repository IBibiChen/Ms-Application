package com.zhilo.common.authentication.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * JWT 认证 Token，参考 UsernamePasswordAuthenticationToken .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2021/2/8
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 认证信息，认证之前存放用户名，认证成功之后存放登录的用户信息
     */
    private final Object principal;
    /**
     * 存放的为密码，认证成功后会清除
     */
    private Object credentials;

    /**
     * 未认证之前创建 JwtAuthenticationToken 时调用
     *
     * @param username 用户名
     * @param password 密码
     */
    public JwtAuthenticationToken(Object username, Object password) {
        super(null);
        this.principal = username;
        this.credentials = password;
        setAuthenticated(false);
    }

    /**
     * 此构造函数只应由 <code>AuthenticationManager</code> 或者
     * <code>AuthenticationProvider</code> 的实现且认证成功后使用
     * (i.e. {@link #isAuthenticated()} = <code>true</code>).
     *
     * @param principal   认证成功后存放用户信息
     * @param credentials 用户密码
     * @param authorities 用户的权限
     */
    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        // 必须调用父类的方法，由父类覆盖
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    /**
     * 删除凭证，认证成功后会删除用户密码
     */
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }

}

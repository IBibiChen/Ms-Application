package com.zhilo.common.authentication.jwt;

import com.zhilo.common.authentication.jwt.JwtAuthenticationToken;
import com.zhilo.common.constant.AuthConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 认证过滤器 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2021/2/8
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String usernameParameter = AuthConstants.PARAMETER_NAME_USERNAME;

    private String passwordParameter = AuthConstants.PARAMETER_NAME_PASSWORD;

    private boolean postOnly = true;

    /**
     * 请求匹配 /auth/token
     */
    public JwtAuthenticationFilter() {
        super(new AntPathRequestMatcher(AuthConstants.JWT_AUTH_PROCESSING_URL, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        // 获取用户名、密码
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(username, password);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 获取用户名
     *
     * @param request HttpServletRequest
     * @return 用户名
     */
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    /**
     * 获取密码
     *
     * @param request HttpServletRequest
     * @return 密码
     */
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    /**
     * 将请求的详情(ip、SessionId)设置到验证请求中
     *
     * @param request     正在为其创建身份验证的请求
     * @param authRequest 应该设置其详细信息的身份验证请求对象
     */
    protected void setDetails(HttpServletRequest request, JwtAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * 设置参数名称，用于从登录请求中获取用户名
     *
     * @param usernameParameter 用户名参数名，默认为“username”
     */
    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    /**
     * 设置参数名称，用于从登录请求中获取密码
     *
     * @param passwordParameter 密码参数名，默认为“password”
     */
    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    /**
     * 定义此过滤器是否只允许 HTTP POST 请求。如果设置为 true，并且接收到不是 POST 请求的身份验证请求，则会立即引发异常，不会尝试身份验证。
     * 将调用 <tt>unsuccessfulAuthentication()<tt> 方法，就像处理一个失败的身份验证一样
     * 默认值 <tt>true</tt>，但可能被子类覆盖
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

}

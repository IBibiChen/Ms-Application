package com.zhilo.common.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhilo.common.constant.AuthConstants;
import com.zhilo.common.result.Result;
import com.zhilo.common.service.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证成功处理 .
 * 为什么不实现接口，而是继承 SavedRequestAwareAuthenticationSuccessHandler 类的方式？
 * 因为 SavedRequestAwareAuthenticationSuccessHandler 这个类记住了你上一次的请求路径，比如：
 * 你请求 user.html。然后被拦截到了登录页，这时候你输入完用户名密码点击登录，会自动跳转到 user.html，而不是主页面。
 *
 * @author BibiChen
 * @version v1.0
 * @since 2019/3/21
 */
@Slf4j
@Component
public class AppAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * ObjectMapper：jackson 框架的工具类，用于转换对象为 json 字符串
     */
    private final ObjectMapper objectMapper;

    private final TokenProvider tokenProvider;

    @Autowired
    public AppAuthenticationSuccessHandler(ObjectMapper objectMapper, TokenProvider tokenProvider) {
        this.objectMapper = objectMapper;
        this.tokenProvider = tokenProvider;
    }

    /**
     * 认证成功处理
     *
     * @param request        HttpServletRequest
     * @param response       HttpServletResponse
     * @param authentication 认证成功后的 Authentication
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("【认证成功】");

        String token = tokenProvider.createToken(authentication);

        Result<Object> result = Result.success(token);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(AuthConstants.APPLICATION_JSON_UTF8);
        response.getWriter().write(objectMapper.writeValueAsString(result));

    }

}

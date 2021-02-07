package com.zhilo.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证失败处理 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2019/3/21
 */
@Slf4j
@Component("appAuthenticationFailureHandler")
public class AppAuthenticationFailureHandler implements AuthenticationFailureHandler {
// public class ApiAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * ObjectMapper：jackson 框架的工具类，用于转换对象为 json 字符串
     */
    @Autowired
    private ObjectMapper objectMapper;

    // @Autowired
    // private SecurityProperties securityProperties;

    /**
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("【认证失败】");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        // response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));

    }

}

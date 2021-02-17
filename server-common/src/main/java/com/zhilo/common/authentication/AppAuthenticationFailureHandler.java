package com.zhilo.common.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhilo.common.constant.AuthConstants;
import com.zhilo.common.result.Result;
import com.zhilo.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@Component
public class AppAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * ObjectMapper：jackson 框架的工具类，用于转换对象为 json 字符串
     */
    private final ObjectMapper objectMapper;

    @Autowired
    public AppAuthenticationFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 认证失败处理
     *
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     * @param exception 认证失败的异常
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("【认证失败，{}】", exception.getMessage());

        Result<Object> result = Result.fail(ResultCode.AUTHENTICATION_FAIL.getCode(), exception.getMessage());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(AuthConstants.APPLICATION_JSON_UTF8);
        response.getWriter().write(objectMapper.writeValueAsString(result));

    }

}

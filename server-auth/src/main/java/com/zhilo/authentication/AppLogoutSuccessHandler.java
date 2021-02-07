package com.zhilo.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出成功处理器，如果退出请求携带了 redirect_uri，则重定向到该地址
 * 如果没携带 redirect_uri，则返回 json 格式的响应
 *
 * @author BibiChen
 * @version v1.0
 * @since 2019-11-28
 */
@Slf4j
@Component
public class AppLogoutSuccessHandler implements LogoutSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("【退出成功】");

        String redirectUri = request.getParameter("redirect_uri");
        if (StringUtils.isEmpty(redirectUri)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString("退出成功"));
        } else {
            response.sendRedirect(redirectUri);
        }

    }

}

package com.zhilo.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

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
    @Autowired
    private ObjectMapper objectMapper;

    // @Autowired
    // private SecurityProperties securityProperties;
    //
    // @Autowired
    // private ClientDetailsService clientDetailsService;
    //
    // @Autowired
    // private AuthorizationServerTokenServices authorizationServerTokenServices;

    /**
     * @param request
     * @param response
     * @param authentication 认证成功后的信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("【认证成功】");

        String header = request.getHeader("Authorization");

        // if (header == null || !header.toLowerCase().startsWith("basic ")) {
        //     throw new UnapprovedClientAuthenticationException("请求头中无 Client 信息");
        // }
        //
        // String[] tokens = extractAndDecodeHeader(header, request);
        // assert tokens.length == 2;
        //
        // String clientId = tokens[0];
        // String clientSecret = tokens[1];
        //
        // ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        // if (null == clientDetails) {
        //     throw new UnapprovedClientAuthenticationException("clientId 对应的配置信息不存在：" + clientId);
        // } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
        //     throw new UnapprovedClientAuthenticationException("clientSecret 不匹配：" + clientSecret);
        // }
        //
        // // requestParameters 参数存放的是每一个授权模式特有的参数，SpringSecurityOAuth 标准流程会根据这些参数去组建 Authentication
        // // 但是在我自己的流程中这里已经获取到了 Authentication；grantType 为自己定义的“custom”
        // TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");
        //
        // OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        //
        // OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        //
        // OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        //
        // response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        // response.getWriter().write(objectMapper.writeValueAsString(accessToken));
    }

    /**
     * Decodes the header into a username and password.
     *
     * @throws BadCredentialsException if the Basic header is not present or is not valid
     *                                 Base64
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }

}

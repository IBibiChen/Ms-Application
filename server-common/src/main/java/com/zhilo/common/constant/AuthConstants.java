package com.zhilo.common.constant;

/**
 * AuthConstants .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2021/2/2
 */
public interface AuthConstants {

    /**
     * JWT 登录认证请求处理 URL
     */
    String JWT_AUTH_PROCESSING_URL = "/auth/token";
    /**
     * 认证请求中，传递用户名的参数的名称
     */
    String PARAMETER_NAME_USERNAME = "username";
    /**
     * 认证请求中，传递密码的参数的名称
     */
    String PARAMETER_NAME_PASSWORD = "password";
    /**
     * 互联网媒体类型，设置 Content-Type
     */
    String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    /**
     * 认证信息 Http 请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT 令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * JWT 载体 key
     */
    String JWT_PAYLOAD_KEY = "payload";

    /**
     * Redis 缓存权限规则 key
     */
    String PERMISSION_RULES_KEY = "auth:permission:rules";

    /**
     * 黑名单 token 前缀
     */
    String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:";

    String CLIENT_DETAILS_FIELDS = "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    String BASE_CLIENT_DETAILS_SQL = "select " + CLIENT_DETAILS_FIELDS + " from oauth_client_details";

    String FIND_CLIENT_DETAILS_SQL = BASE_CLIENT_DETAILS_SQL + " order by client_id";

    String SELECT_CLIENT_DETAILS_SQL = BASE_CLIENT_DETAILS_SQL + " where client_id = ?";

    /**
     * 密码加密方式
     */
    String BCRYPT = "{bcrypt}";

    String JWT_USER_ID_KEY = "id";

    String JWT_CLIENT_ID_KEY = "client_id";

    String JWT_JTI_KEY = "client_id";

    /**
     * JWT 存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT 存储权限属性
     */
    String JWT_AUTHORITIES_KEY = "authorities";

    /**
     * 后台管理客户端 ID
     */
    String ADMIN_CLIENT_ID = "admin";

    /**
     * 后台管理接口路径匹配
     */
    String ADMIN_URL_PATTERN = "/admin/**";

}

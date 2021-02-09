package com.zhilo.common.result;

/**
 * REST API 状态码 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2020-02-28
 */
public enum ResultCode implements IResultCode {
    /**
     * 失败
     */
    FAIL("-1", "操作失败"),
    /**
     * 成功
     */
    SUCCESS("200", "操作成功"),
    /**
     * Token
     */
    AUTHENTICATION_FAIL("401", "认证失败"),
    TOKEN_INVALIDED("401", "Token 非法，用户不允许访问！"),
    UN_AUTHORIZATION("403", "用户无权访问！");

    private final String code;

    private final String msg;

    ResultCode(final String code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultCode fromCode(String code) {
        ResultCode[] ecs = ResultCode.values();
        for (ResultCode ec : ecs) {
            if (ec.getCode().equals(code)) {
                return ec;
            }
        }
        return SUCCESS;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return String.format(" ErrorCode:{code=%s, msg=%s} ", code, msg);
    }
}

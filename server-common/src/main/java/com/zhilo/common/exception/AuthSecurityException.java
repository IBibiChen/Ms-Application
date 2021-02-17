package com.zhilo.common.exception;

import com.zhilo.common.result.IResultCode;
import org.springframework.security.core.AuthenticationException;

/**
 * 自定义安全认证异常 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2020-02-28
 */
public class AuthSecurityException extends AuthenticationException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5885155226898287919L;

    /**
     * 状态码
     */
    private IResultCode resultCode;

    public AuthSecurityException(String message) {
        super(message);
    }

    public AuthSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthSecurityException(IResultCode resultCode, Throwable cause) {
        super(resultCode.getMsg(), cause);
        this.resultCode = resultCode;
    }

    public AuthSecurityException(IResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public IResultCode getRCode() {
        return resultCode;
    }
}

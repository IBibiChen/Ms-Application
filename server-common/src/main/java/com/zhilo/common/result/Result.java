package com.zhilo.common.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

/**
 * REST API 返回结果 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2020-02-28
 */
@Data
public class Result<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 业务状态码 .
     */
    private String code;
    /**
     * 描述 .
     */
    private String msg;
    /**
     * 结果集 .
     */
    private T data;

    public Result() {

    }

    public Result(IResultCode resultCode) {
        resultCode = Optional.ofNullable(resultCode).orElse(ResultCode.FAIL);
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public static <T> Result<T> success(T data) {
        ResultCode aec = ResultCode.SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            aec = ResultCode.FAIL;
        }
        return result(aec, data);
    }

    public static <T> Result<T> fail(String msg) {
        return result(ResultCode.FAIL.getCode(), msg, null);
    }

    public static <T> Result<T> fail(String code, String msg) {
        return result(code, msg, null);
    }

    public static <T> Result<T> fail(IResultCode resultCode) {
        return result(resultCode, null);
    }

    public static <T> Result<T> result(IResultCode resultCode, T data) {
        return result(resultCode.getCode(), resultCode.getMsg(), data);
    }

    private static <T> Result<T> result(String code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

}

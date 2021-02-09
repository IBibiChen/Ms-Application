package com.zhilo.common.result;

/**
 * REST API 状态码接口 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2020-02-28
 */
public interface IResultCode {

    /**
     * 业务状态码
     *
     * @return code
     */
    String getCode();

    /**
     * 描述
     *
     * @return msg
     */
    String getMsg();
}

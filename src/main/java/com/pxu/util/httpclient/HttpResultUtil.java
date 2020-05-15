package com.pxu.util.httpclient;

import com.pxu.util.httpclient.entity.HttpResult;
import org.apache.http.HttpStatus;

public class HttpResultUtil {
    /**
     * 处理200响应
     *
     * @param httpResult
     * @return
     */
    public static String process200(HttpResult httpResult) {
        if (httpResult == null || HttpStatus.SC_OK != httpResult.getStatusCode()) {
            return null;
        }
        return httpResult.getBody();
    }
}

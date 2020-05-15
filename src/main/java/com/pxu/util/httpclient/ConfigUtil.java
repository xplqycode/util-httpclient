package com.pxu.util.httpclient;

import org.apache.http.client.config.RequestConfig;

public class ConfigUtil {

    /**
     * 获取请求超时配置
     *
     * @param connectTimeoutMills 连接超时
     * @param socketTimeoutMills  等待数据超时
     * @return
     */
    public static RequestConfig getRequestConfig(int connectTimeoutMills, int socketTimeoutMills) {
        return RequestConfig.custom()
                .setConnectTimeout(connectTimeoutMills)//设置连接超时时间
                .setSocketTimeout(socketTimeoutMills)//设置等待数据超时时间
                .setConnectionRequestTimeout(20)//默认-1，会一直排队等待获取连接池资源，单位毫秒（如不设置，则请求时间就控制不了了）
                .setRedirectsEnabled(false)
                .build();
    }

}

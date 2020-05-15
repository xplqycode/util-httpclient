package com.pxu.util.httpclient;

import com.pxu.util.httpclient.entity.HttpResult;

/**
 * @author 徐鹏
 * @date 2020/5/15 16:26
 */
public class HttpclientDemo {
        public static void main(String[] args) {
            //配置一个httpClient
            HttpClient httpClient1 = HttpClientBuilder.create()
                    .setConnectTimeout(500)//设置connect timeout
                    .setSocketTimeout(2000)//设置read timeout
                    .setMaxTotal(50)//设置所有域的最大请求限制
                    .setMaxPerRoute(10)//设置单个域的最大请求限制
                    .setThrowException(true)//设置是否抛出异常
                    .build();

            String url = "http://www.163.com";
            HttpResult httpResult = httpClient1.doGet(url);//响应含有statusCode,body,header
            String result200 = HttpResultUtil.process200(httpResult);//处理200响应
            System.out.println(result200);
            //配置另一个httpClient
            HttpClient httpClient2 = HttpClientBuilder.create()
                    .setConnectTimeout(500)//设置connect timeout
                    .setSocketTimeout(2000)//设置read timeout
                    .setMaxTotal(50)//设置所有域的最大请求限制
                    .setMaxPerRoute(10)//设置单个域的最大请求限制
                    .setThrowException(true)//设置是否抛出异常
                    .build();

            url = "http://www.baidu.com";
            httpResult = httpClient2.doGet(url);//响应含有statusCode,body,header
            result200 = HttpResultUtil.process200(httpResult);//处理200响应
            System.out.println(result200);

        }

}

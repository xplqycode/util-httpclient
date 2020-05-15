package com.pxu.util.httpclient;

import com.pxu.util.httpclient.entity.HttpDownloadResult;
import com.pxu.util.httpclient.entity.HttpResult;

import java.util.Map;

public interface HttpClient {
    /**
     * GET请求
     *
     * @param url 资源地址
     * @return
     */
    HttpResult doGet(String url);

    /**
     * GET请求
     *
     * @param url        资源地址
     * @param reqHeaders 请求头
     * @return
     */
    HttpResult doGet(String url, Map<String, String> reqHeaders);

    /**
     * GET请求
     *
     * @param url         资源地址
     * @param respCharset 响应内容编码
     * @return
     */
    HttpResult doGet(String url, String respCharset);

    /**
     * GET请求
     *
     * @param url                 资源地址
     * @param connectTimeoutMills 连接超时
     * @param socketTimeoutMills  等待数据超时
     * @return
     */
    HttpResult doGet(String url, int connectTimeoutMills, int socketTimeoutMills);

    /**
     * GET请求
     *
     * @param url                 资源地址
     * @param respCharset         响应内容编码
     * @param connectTimeoutMills 连接超时
     * @param socketTimeoutMills  等待数据超时
     * @return
     */
    HttpResult doGet(String url, String respCharset, int connectTimeoutMills, int socketTimeoutMills);

    /**
     * GET请求
     *
     * @param url    资源地址
     * @param cookie cookie
     * @return
     */
    HttpResult doGetWithCookie(String url, String cookie);

    /**
     * DELETE请求
     *
     * @param url 资源地址
     * @return
     */
    HttpResult doDelete(String url);

    /**
     * DELETE请求
     *
     * @param url         资源地址
     * @param respCharset 响应内容编码
     * @return
     */
    HttpResult doDelete(String url, String respCharset);

    /**
     * DELETE请求
     *
     * @param url                 资源地址
     * @param connectTimeoutMills 连接超时
     * @param socketTimeoutMills  等待数据超时
     * @return
     */
    HttpResult doDelete(String url, int connectTimeoutMills, int socketTimeoutMills);

    /**
     * DELETE请求
     *
     * @param url                 资源地址
     * @param respCharset         响应内容编码
     * @param connectTimeoutMills 连接超时
     * @param socketTimeoutMills  等待数据超时
     * @return
     */
    HttpResult doDelete(String url, String respCharset, int connectTimeoutMills, int socketTimeoutMills);

    /**
     * POST请求
     *
     * @param url 资源地址
     * @return
     */
    HttpResult doPost(String url);

    /**
     * POST请求
     *
     * @param url         资源地址
     * @param respCharset 响应内容编码
     * @return
     */
    HttpResult doPost(String url, String respCharset);

    /**
     * POST请求
     *
     * @param url    资源地址
     * @param params 请求参数
     * @return
     */
    HttpResult doPost(String url, Map<String, String> params);

    /**
     * POST请求
     *
     * @param url                 资源地址
     * @param params              请求参数
     * @param connectTimeoutMills 连接超时
     * @param socketTimeoutMills  等待数据超时
     * @return
     */
    HttpResult doPost(String url, Map<String, String> params, int connectTimeoutMills, int socketTimeoutMills);

    /**
     * POST请求
     *
     * @param url         资源地址
     * @param params      请求参数
     * @param respCharset 响应内容编码
     * @return
     */
    HttpResult doPost(String url, Map<String, String> params, String respCharset);

    /**
     * POST请求
     *
     * @param url                 资源地址
     * @param params              请求参数
     * @param respCharset         响应内容编码
     * @param connectTimeoutMills 连接超时
     * @param socketTimeoutMills  等待数据超时
     * @return
     */
    HttpResult doPost(String url, Map<String, String> params, String respCharset, int connectTimeoutMills, int socketTimeoutMills);

    /**
     * POST请求
     *
     * @param url                 资源地址
     * @param params              请求参数
     * @param respCharset         响应内容编码
     * @param inputCharset        请求参数编码
     * @param connectTimeoutMills 连接超时
     * @param socketTimeoutMills  等待数据超时
     * @return
     */
    HttpResult doPost(String url, Map<String, String> params, String respCharset, String inputCharset, int connectTimeoutMills, int socketTimeoutMills);

    /**
     * POST请求
     * 发送字符串参数
     *
     * @param url   资源地址
     * @param param 请求参数
     * @return
     */
    HttpResult doPostString(String url, String param);


    /**
     * POST请求
     * 发送字符串参数
     *
     * @param url        资源地址
     * @param param      请求参数
     * @param reqHeaders 请求头
     * @return
     */
    HttpResult doPostString(String url, String param, Map<String, String> reqHeaders);

    /**
     * POST请求
     * 发送字符串参数
     *
     * @param url         资源地址
     * @param param       请求参数
     * @param respCharset 响应内容编码
     * @return
     */
    HttpResult doPostString(String url, String param, String respCharset);


    /**
     * POST请求
     * 发送字符串参数
     *
     * @param url         资源地址
     * @param param       请求参数
     * @param respCharset 响应内容编码
     * @param reqHeaders  请求头
     * @return
     */
    HttpResult doPostString(String url, String param, String respCharset, Map<String, String> reqHeaders);

    /**
     * POST请求
     * 发送字符串参数
     *
     * @param url  资源地址
     * @param json 请求json字符串
     * @return
     */
    HttpResult doPostJsonString(String url, String json);

    /**
     * POST请求
     * 发送字符串参数
     *
     * @param url                 资源地址
     * @param json                请求json字符串
     * @param connectTimeoutMills 连接超时
     * @param socketTimeoutMills  等待数据超时
     * @return
     */
    HttpResult doPostJsonString(String url, String json, int connectTimeoutMills, int socketTimeoutMills);

    /**
     * POST请求
     * 发送字符串参数
     *
     * @param url  资源地址
     * @param json 请求json字符串
     * @return
     */
    HttpResult doPostJsonString(String url, String json, Map<String, String> reqHeaders);

    /**
     * POST请求
     * 发送字符串参数
     *
     * @param url         资源地址
     * @param json        请求json字符串
     * @param respCharset 响应内容编码
     * @return
     */
    HttpResult doPostJsonString(String url, String json, String respCharset);

    /**
     * POST请求
     * 发送字符串参数
     *
     * @param url    资源地址
     * @param json   请求json字符串
     * @param cookie cookie
     * @return
     */
    HttpResult doPostJsonStringWithCookie(String url, String json, String cookie);

    /**
     * PUT请求
     * 发送字符串参数
     *
     * @param url    资源地址
     * @param json   请求json字符串
     * @param cookie cookie
     * @return
     */
    HttpResult doPutJsonStringWithCookie(String url, String json, String cookie);

    /**
     * PURGE请求
     *
     * @param url        资源地址
     * @param reqHeaders 请求头
     * @return
     */
    HttpResult doPurge(String url, Map<String, String> reqHeaders);

    /**
     * PURGE请求
     *
     * @param url                 资源地址
     * @param reqHeaders          请求头
     * @param connectTimeoutMills 连接超时
     * @param socketTimeoutMills  等待数据超时
     * @return
     */
    HttpResult doPurge(String url, Map<String, String> reqHeaders, int connectTimeoutMills, int socketTimeoutMills);


    /**
     * 下载资源
     *
     * @param url 资源地址
     * @return 字节数组
     */
    HttpDownloadResult doGetDownload(String url);

    /**
     * 下载资源
     *
     * @param url 资源地址
     * @return 字节数组
     */
    HttpDownloadResult doPostDownload(String url, Map<String, String> params);

    /**
     * POST请求
     * 发送字符串参数
     * 返回二进制数组
     *
     * @param url  资源地址
     * @param json 请求json字符串
     * @return
     */
    HttpDownloadResult doPostJsonDownload(String url, String json);


}

package com.pxu.util.httpclient;

import com.pxu.util.httpclient.entity.HttpDownloadResult;
import com.pxu.util.httpclient.entity.HttpPurge;
import com.pxu.util.httpclient.entity.HttpResult;
import org.apache.commons.collections.MapUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientImpl implements HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientImpl.class);

    private final static String APPLICATION_JSON_VALUE = "application/json";

    private static final String DEFAULT_ENCODING = "UTF-8";

    private CloseableHttpClient httpClient;

    private boolean throwException;

    public HttpClientImpl(CloseableHttpClient httpClient, boolean throwException) {
        this.httpClient = httpClient;
        this.throwException = throwException;
    }

    @Override
    public HttpResult doGet(String url) {
        return doGet(url, DEFAULT_ENCODING);
    }

    @Override
    public HttpResult doGet(String url, Map<String, String> reqHeaders) {
        HttpGet httpGet = new HttpGet(url);
        configHeaders(httpGet, reqHeaders);//设置header
        return executeMethod(httpGet);
    }

    @Override
    public HttpResult doGet(String url, String respCharset) {
        HttpGet httpGet = new HttpGet(url);
        return executeMethod(httpGet, respCharset);
    }


    @Override
    public HttpResult doGet(String url, int connectTimeoutMills, int socketTimeoutMills) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(getRequestConfig(connectTimeoutMills, socketTimeoutMills));
        return executeMethod(httpGet);
    }

    private RequestConfig getRequestConfig(int connectTimeoutMills, int socketTimeoutMills) {
        return ConfigUtil.getRequestConfig(connectTimeoutMills, socketTimeoutMills);
    }

    @Override
    public HttpResult doGet(String url, String respCharset, int connectTimeoutMills, int socketTimeoutMills) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(getRequestConfig(connectTimeoutMills, socketTimeoutMills));
        return executeMethod(httpGet, respCharset);
    }

    @Override
    public HttpResult doGetWithCookie(String url, String cookie) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Cookie", cookie);
        return executeMethod(httpGet);
    }

    @Override
    public HttpResult doDelete(String url) {
        HttpDelete httpDelete = new HttpDelete(url);
        return executeMethod(httpDelete);
    }

    @Override
    public HttpResult doDelete(String url, String respCharset) {
        HttpDelete httpDelete = new HttpDelete(url);
        return executeMethod(httpDelete, respCharset);
    }

    @Override
    public HttpResult doDelete(String url, int connectTimeoutMills, int socketTimeoutMills) {
        return doDelete(url, DEFAULT_ENCODING, connectTimeoutMills, socketTimeoutMills);
    }

    @Override
    public HttpResult doDelete(String url, String respCharset, int connectTimeoutMills, int socketTimeoutMills) {
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setConfig(getRequestConfig(connectTimeoutMills, socketTimeoutMills));
        return executeMethod(httpDelete, respCharset);
    }

    @Override
    public HttpResult doPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        return executeMethod(httpPost);
    }

    @Override
    public HttpResult doPost(String url, String respCharset) {
        HttpPost httpPost = new HttpPost(url);
        return executeMethod(httpPost, respCharset);
    }

    @Override
    public HttpResult doPost(String url, Map<String, String> params) {
        return doPost(url, params, DEFAULT_ENCODING);
    }

    @Override
    public HttpResult doPost(String url, Map<String, String> params, int connectTimeoutMills, int socketTimeoutMills) {
        HttpPost httpPost = new HttpPost(url);
        try {
            UrlEncodedFormEntity formEntity = getUrlEncodedFormEntity(params, DEFAULT_ENCODING);
            if (formEntity != null) {
                httpPost.setEntity(formEntity);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的编码, url=" + url, e);
            return null;
        } catch (UnsupportedCharsetException e2) {
            logger.error("不支持的编码, url=" + url, e2);
            return null;
        }
        httpPost.setConfig(getRequestConfig(connectTimeoutMills, socketTimeoutMills));
        return executeMethod(httpPost);
    }

    @Override
    public HttpResult doPost(String url, Map<String, String> params, String respCharset) {
        HttpPost httpPost = new HttpPost(url);
        try {
            UrlEncodedFormEntity formEntity = getUrlEncodedFormEntity(params, DEFAULT_ENCODING);
            if (formEntity != null) {
                httpPost.setEntity(formEntity);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的编码, url=" + url, e);
            return null;
        } catch (UnsupportedCharsetException e2) {
            logger.error("不支持的编码, url=" + url, e2);
            return null;
        }

        return executeMethod(httpPost, respCharset);
    }

    @Override
    public HttpResult doPost(String url, Map<String, String> params, String respCharset, int connectTimeoutMills, int socketTimeoutMills) {
        return doPost(url, params, respCharset, DEFAULT_ENCODING, connectTimeoutMills, socketTimeoutMills);
    }

    @Override
    public HttpResult doPost(String url, Map<String, String> params, String respCharset, String inputCharset, int connectTimeoutMills, int socketTimeoutMills) {
        HttpPost httpPost = new HttpPost(url);
        try {
            UrlEncodedFormEntity formEntity = getUrlEncodedFormEntity(params, inputCharset);
            if (formEntity != null) {
                httpPost.setEntity(formEntity);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的编码, url=" + url, e);
            return null;
        } catch (UnsupportedCharsetException e2) {
            logger.error("不支持的编码, url=" + url, e2);
            return null;
        }
        httpPost.setConfig(getRequestConfig(connectTimeoutMills, socketTimeoutMills));
        return executeMethod(httpPost, respCharset);
    }

    @Override
    public HttpResult doPostString(String url, String param) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(param, DEFAULT_ENCODING);
        httpPost.setEntity(entity);
        return executeMethod(httpPost);
    }

    @Override
    public HttpResult doPostString(String url, String param, Map<String, String> reqHeaders) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(param, DEFAULT_ENCODING);
        httpPost.setEntity(entity);
        configHeaders(httpPost, reqHeaders);//设置header
        return executeMethod(httpPost);
    }

    @Override
    public HttpResult doPostString(String url, String param, String respCharset) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(param, DEFAULT_ENCODING);
        httpPost.setEntity(entity);
        return executeMethod(httpPost, respCharset);
    }

    @Override
    public HttpResult doPostString(String url, String param, String respCharset, Map<String, String> reqHeaders) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(param, DEFAULT_ENCODING);
        httpPost.setEntity(entity);
        configHeaders(httpPost, reqHeaders);//设置header
        return executeMethod(httpPost, respCharset);
    }

    @Override
    public HttpResult doPostJsonString(String url, String json) {
        return doPostJsonString(url, json, DEFAULT_ENCODING);
    }

    @Override
    public HttpResult doPostJsonString(String url, String json, int connectTimeoutMills, int socketTimeoutMills) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(json, DEFAULT_ENCODING);
        entity.setContentType(APPLICATION_JSON_VALUE);
        httpPost.setEntity(entity);
        httpPost.setConfig(getRequestConfig(connectTimeoutMills, socketTimeoutMills));
        return executeMethod(httpPost);
    }

    @Override
    public HttpResult doPostJsonString(String url, String json, Map<String, String> reqHeaders) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(json, DEFAULT_ENCODING);
        entity.setContentType(APPLICATION_JSON_VALUE);
        httpPost.setEntity(entity);
        configHeaders(httpPost, reqHeaders);//设置header
        return executeMethod(httpPost);
    }

    @Override
    public HttpResult doPostJsonString(String url, String json, String respCharset) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(json, DEFAULT_ENCODING);
        entity.setContentType(APPLICATION_JSON_VALUE);
        httpPost.setEntity(entity);
        return executeMethod(httpPost, respCharset);
    }

    @Override
    public HttpResult doPostJsonStringWithCookie(String url, String json, String cookie) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(json, DEFAULT_ENCODING);
        entity.setContentType(APPLICATION_JSON_VALUE);
        httpPost.setEntity(entity);
        httpPost.setHeader("Cookie", cookie);
        return executeMethod(httpPost);
    }

    @Override
    public HttpResult doPutJsonStringWithCookie(String url, String json, String cookie) {
        HttpPut httpPut = new HttpPut(url);
        StringEntity entity = new StringEntity(json, DEFAULT_ENCODING);
        entity.setContentType(APPLICATION_JSON_VALUE);
        httpPut.setEntity(entity);
        httpPut.setHeader("Cookie", cookie);
        return executeMethod(httpPut);
    }

    @Override
    public HttpResult doPurge(String url, Map<String, String> reqHeaders) {
        HttpPurge httpPurge = new HttpPurge(url);
        configHeaders(httpPurge, reqHeaders);
        return executeMethod(httpPurge);
    }

    @Override
    public HttpResult doPurge(String url, Map<String, String> reqHeaders, int connectTimeoutMills, int socketTimeoutMills) {
        HttpPurge httpPurge = new HttpPurge(url);
        httpPurge.setConfig(getRequestConfig(connectTimeoutMills, socketTimeoutMills));
        configHeaders(httpPurge, reqHeaders);
        return executeMethod(httpPurge);
    }

    @Override
    public HttpDownloadResult doGetDownload(String url) {
        HttpGet httpGet = new HttpGet(url);
        return executeMethodForBytesResp(httpGet);
    }

    @Override
    public HttpDownloadResult doPostDownload(String url, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(url);
        try {
            UrlEncodedFormEntity formEntity = getUrlEncodedFormEntity(params, DEFAULT_ENCODING);
            if (formEntity != null) {
                httpPost.setEntity(formEntity);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的编码, url=" + url, e);
            return null;
        } catch (UnsupportedCharsetException e2) {
            logger.error("不支持的编码, url=" + url, e2);
            return null;
        }
        return executeMethodForBytesResp(httpPost);
    }

    @Override
    public HttpDownloadResult doPostJsonDownload(String url, String json) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(json, DEFAULT_ENCODING);
        entity.setContentType(APPLICATION_JSON_VALUE);
        httpPost.setEntity(entity);
        return executeMethodForBytesResp(httpPost);
    }

    /**
     * 获取请求参数包装实体，带有编码
     *
     * @param params
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    private UrlEncodedFormEntity getUrlEncodedFormEntity(Map<String, String> params, String charset) throws UnsupportedEncodingException {
        if (MapUtils.isNotEmpty(params)) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                nvps.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (nvps.size() > 0) {
                return new UrlEncodedFormEntity(nvps, charset);
            }
        }
        return null;
    }

    /**
     * 配置请求头
     *
     * @param method  请求方法
     * @param headers 请求头
     */
    private void configHeaders(HttpRequestBase method, Map<String, String> headers) {
        //设置请求头
        if (MapUtils.isNotEmpty(headers)) {
            Iterator iterator = headers.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                method.setHeader(elem.getKey(), elem.getValue());
            }
        }
    }

    /**
     * 执行http调用
     *
     * @param httpMethod
     * @return 响应内容，字符串类型
     */
    private HttpResult executeMethod(HttpRequestBase httpMethod) {
        return executeMethod(httpMethod, DEFAULT_ENCODING);
    }

    /**
     * 执行http调用
     *
     * @param httpMethod
     * @return 响应内容，字符串类型
     */
    private HttpResult executeMethod(HttpRequestBase httpMethod, String respCharset) {
        CloseableHttpResponse httpResponse = null;
        HttpEntity httpEntity = null;
        try {
            httpResponse = httpClient.execute(httpMethod);
            httpEntity = httpResponse.getEntity();
            HttpResult httpResult = new HttpResult();
            httpResult.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            httpResult.setBody(EntityUtils.toString(httpEntity, respCharset));
            for (Header header : httpResponse.getAllHeaders()) {
                httpResult.getHeaders().put(header.getName(), header.getValue());
            }
            return httpResult;
        } catch (Exception e) {
            if (throwException) {
                throw new RuntimeException(e);
            } else {
                logger.warn("http request error", e);
                return null;
            }
        } finally {
            finallyProcess(httpEntity, httpResponse, httpMethod);
        }
    }

    /**
     * 执行http调用
     *
     * @param httpMethod
     * @return 响应内容，字节数据
     */
    private HttpDownloadResult executeMethodForBytesResp(HttpRequestBase httpMethod) {
        CloseableHttpResponse httpResponse = null;
        HttpEntity httpEntity = null;
        try {
            httpResponse = httpClient.execute(httpMethod);
            httpEntity = httpResponse.getEntity();
            HttpDownloadResult httpDownloadResult = new HttpDownloadResult();
            httpDownloadResult.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            httpDownloadResult.setBody(EntityUtils.toByteArray(httpEntity));
            for (Header header : httpResponse.getAllHeaders()) {
                httpDownloadResult.getHeaders().put(header.getName(), header.getValue());
            }
            return httpDownloadResult;
        } catch (Exception e) {
            if (throwException) {
                throw new RuntimeException(e);
            } else {
                logger.warn("http request error", e);
                return null;
            }
        } finally {
            finallyProcess(httpEntity, httpResponse, httpMethod);
        }
    }

    private void finallyProcess(HttpEntity httpEntity, CloseableHttpResponse httpResponse, HttpRequestBase httpMethod) {
        if (httpEntity != null) {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                logger.error("Ensures that the entity content is fully consumed occur error,uri=" + httpMethod.getURI(), e);
            }
        }
        if (httpResponse != null) {
            try {
                httpResponse.close();
            } catch (IOException e) {
                logger.error("close httpResponse error,uri=" + httpMethod.getURI(), e);
            }
        }
    }
}

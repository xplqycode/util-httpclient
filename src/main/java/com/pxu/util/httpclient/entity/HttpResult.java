package com.pxu.util.httpclient.entity;

import java.util.HashMap;
import java.util.Map;

public class HttpResult {

    private int statusCode;

    private String body;

    private Map<String, String> headers = new HashMap();

    public HttpResult() {
    }

    public HttpResult(int statusCode, String body, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

}

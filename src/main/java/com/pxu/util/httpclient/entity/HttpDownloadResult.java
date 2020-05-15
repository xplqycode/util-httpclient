package com.pxu.util.httpclient.entity;

import java.util.HashMap;
import java.util.Map;

public class HttpDownloadResult {

    private int statusCode;

    private byte[] body;

    private Map<String, String> headers = new HashMap();

    public HttpDownloadResult() {
    }

    public HttpDownloadResult(int statusCode, byte[] body, Map<String, String> headers) {
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

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}

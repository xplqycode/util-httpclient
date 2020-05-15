package com.pxu.util.httpclient.entity;

import org.apache.http.client.methods.HttpRequestBase;

import java.net.URI;

public class HttpPurge extends HttpRequestBase {


    private final static String METHOD_NAME = "PURGE";

    public HttpPurge() {
        super();
    }

    @Override
    public String getMethod() {
        return this.METHOD_NAME;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public HttpPurge(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    public String getName() {
        return "PURGE";
    }

}

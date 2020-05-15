package com.pxu.util.httpclient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HttpClientBuilder {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientBuilder.class);

    //整个连接池的最大连接数
    private int maxTotal = Runtime.getRuntime().availableProcessors();

    //每个路由最大连接数
    private int maxPerRoute = Runtime.getRuntime().availableProcessors();

    private int connectTimeout = 2 * 1000;

    private int socketTimeout = 5 * 1000;

    //http请求发生错误时，true：抛出异常，false：打印日志并返回null
    private boolean throwException = false;

    private HttpClientBuilder() {

    }

    public static HttpClientBuilder create() {
        return new HttpClientBuilder();
    }

    public HttpClient build() {

        //注册访问协议相关的Socket工厂
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", SSLConnectionSocketFactory.getSystemSocketFactory())//支持https
                .build();

        //HttpConnection 工厂：配置写请求/解析响应处理器
        org.apache.http.conn.HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
                DefaultHttpRequestWriterFactory.INSTANCE,
                DefaultHttpResponseParserFactory.INSTANCE);

        //DNS解析器
        DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;

        //创建池化连接管理器
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connFactory, dnsResolver);

        //默认为Socket配置
        SocketConfig defaultSocketConfig = SocketConfig.custom()
                .setTcpNoDelay(true).build();
        manager.setDefaultSocketConfig(defaultSocketConfig);
        manager.setMaxTotal(maxTotal);//设置整个连接池的最大连接数
        manager.setDefaultMaxPerRoute(maxPerRoute);//每个路由最大连接数
        manager.setValidateAfterInactivity(5 * 1000);//在从连接池获取连接时，连接不活跃多长时间后需要进行一次验证，默认为2s

        //默认请求配置
        RequestConfig defaultRequestConfig = ConfigUtil.getRequestConfig(connectTimeout, socketTimeout);

        //创建HttpClient
        final CloseableHttpClient httpClient = HttpClients.custom()
                //.setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36")
                .setConnectionManager(manager)
                .setConnectionManagerShared(false)//连接池不是共享模式
                .evictIdleConnections(60, TimeUnit.SECONDS)//定期回收空闲连接
                .evictExpiredConnections()//定期回收过期连接
                .setConnectionTimeToLive(60, TimeUnit.SECONDS)//连接存活时间，如果不设置，则根据长连接信息决定
                .setDefaultRequestConfig(defaultRequestConfig)//设置默认请求配置
                .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)//连接重用策略，即是否能keepAlive
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)//长连接配置，即获取长连接生产多长时间
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))//设置重试次数，默认3次；当前是禁止掉的
                .build();

        //JVM停止或重启时，关闭连接池释放掉连接
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.info("将释放http client连接池");
                if (httpClient != null) {
                    try {
                        httpClient.close();
                    } catch (IOException e) {
                        logger.error("释放http client连接池error", e);
                    }
                }
                logger.info("已释放http client连接池");
            }
        });

        return new HttpClientImpl(httpClient, throwException);
    }

    public HttpClientBuilder setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
        return this;
    }

    public HttpClientBuilder setMaxPerRoute(int maxPerRoute) {
        this.maxPerRoute = maxPerRoute;
        return this;
    }

    public HttpClientBuilder setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public HttpClientBuilder setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public HttpClientBuilder setThrowException(boolean throwException) {
        this.throwException = throwException;
        return this;
    }

}

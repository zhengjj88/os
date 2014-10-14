/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp;

import com.kingmed.dp.connection.ImageServer;
import com.kingmed.dp.connection.impl.DefaultCommunicator;
import com.kingmed.dp.ndp.impl.SignInResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NDP.Serve的通信类
 *
 * @author zhengjunjie
 */
public class NDPCommunicator extends DefaultCommunicator {

    private NDPServe ndpServe;
    private HttpClient httpClient;
    private String cookie;

    private static final Logger log = LoggerFactory.getLogger(NDPCommunicator.class);

    public NDPCommunicator() {
        httpClient = HttpClients.createDefault();
    }

    @Override
    public void queryServer(ImageServer server) throws Exception {

    }

    /**
     * 登录
     *
     * @return cookie 登录成功，则返回cookie
     */
    public String signin() {
        String urlForSignIn = ndpServe.getUrlSignin();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        SignInResponseHandler responseHandler = new SignInResponseHandler();
        try {
            HttpGet httpget = new HttpGet(urlForSignIn);
            log.info("执行请求 " + httpget.getRequestLine());
            httpclient.execute(httpget, responseHandler);
            cookie = responseHandler.getCookie();
        } catch (Exception e) {
            log.error("认证出错", e);
        } finally {
            try {
                httpclient.close();
            } catch (Exception e) {
                log.error("关闭http连接出错", e);
            }
        }
        return null;
    }
    
    public void singOut(){
        String urlForSignOut = ndpServe.getUrlSignout();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        SignInResponseHandler responseHandler = new SignInResponseHandler();
        try {
            HttpGet httpget = new HttpGet(urlForSignOut);
            log.info("执行请求 " + httpget.getRequestLine());
            httpclient.execute(httpget, responseHandler);
            cookie = responseHandler.getCookie();
        } catch (Exception e) {
            log.error("请求出错", e);
        } finally {
            try {
                httpclient.close();
            } catch (Exception e) {
                log.error("关闭http连接出错", e);
            }
        }
        return null;
    }

}

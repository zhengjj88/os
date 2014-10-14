/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.impl;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.util.Assert;

/**
 *
 * @author zhengjunjie
 */
public class SignOutResponseHandlerTest {

    private NDPImageServerImpl ndpServe;

    public SignOutResponseHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ndpServe = new NDPImageServerImpl();
        ndpServe.setProtocl("http");
        ndpServe.setHost("www.kingmed.com.cn");
        ndpServe.setPort(81);
        ndpServe.setUsername("upmcportal");
        ndpServe.setPassword("upmcportalking");
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testSignOut() {
        String signinUrl = ndpServe.getUrlSignin();
        String signOutUrl =ndpServe.getUrlSignout();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        NDPServeResponseHandler responeHandler = new SignInResponseHandler();
        String cookie =null;
        try {
            HttpGet httpget = new HttpGet(signinUrl);
            httpclient.execute(httpget, responeHandler);
            cookie = responeHandler.getCookie();
            Assert.notNull(cookie);
        } catch (Exception e) {
            e.printStackTrace();
            fail("认证失败");
        }finally{
            try {
                httpclient.close();
            } catch (IOException ex) {
                fail("关闭连接失败");
            }
        }
        
        httpclient = HttpClients.createDefault();
        responeHandler = new SignOutResponseHandler();
        try {
            HttpGet httpget = new HttpGet(signOutUrl);
            httpget.setHeader("Cookie",cookie);
            httpclient.execute(httpget, responeHandler);
        } catch (Exception e) {
            e.printStackTrace();
            fail("注销失败");
        }finally{
            try {
                httpclient.close();
            } catch (IOException ex) {
                fail("关闭连接失败");
            }
        }
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.impl;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 *
 * @author zhengjunjie
 */
public class SignInResponseHandlerTest {

    private NDPImageServerImpl ndpServe;

    public SignInResponseHandlerTest() {
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
        ndpServe.setHost("192.168.0.45");
        ndpServe.setPort(80);
        ndpServe.setUsername("upmc-test");
        ndpServe.setPassword("123456-1");
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testSignIn() {
        String signinUrl = ndpServe.getUrlSignin();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        NDPServeResponseHandler responeHandler = new SignInResponseHandler();
        try {
            HttpGet httpget = new HttpGet(signinUrl);
            httpclient.execute(httpget, responeHandler);
            String cookie = responeHandler.getCookie();
            Assert.notNull(cookie);
        } catch (Exception e) {
            e.printStackTrace();
            fail("认证失败");
        }
    }
}

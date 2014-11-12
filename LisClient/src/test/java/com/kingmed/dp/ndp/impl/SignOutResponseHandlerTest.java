/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.impl;

import com.kingmed.dp.ndp.NDPServe;
import java.io.IOException;
import java.util.Set;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
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

    private static Set<NDPServe> allNDPServes;

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
        allNDPServes = NDPServeFactory.getAllNDPServes();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSignOut() {
        for (NDPServe ndpServe : allNDPServes) {
            String signinUrl = ndpServe.getUrlSignin();
            String signOutUrl = ndpServe.getUrlSignout();
            CloseableHttpClient httpclient = HttpClients.createDefault();
            NDPServeResponseHandler responeHandler = new SignInResponseHandler();
            String cookie = null;
            try {
                HttpGet httpget = new HttpGet(signinUrl);
                httpclient.execute(httpget, responeHandler);
                cookie = responeHandler.getCookie();
                Assert.notNull(cookie);
            } catch (Exception e) {
                e.printStackTrace();
                fail("认证失败");
            } finally {
                try {
                    httpclient.close();
                } catch (IOException ex) {
                    fail("关闭连接失败");
                }
            }

            Header header = new BasicHeader("Cookie", cookie);
            httpclient = HttpClients.createDefault();
            responeHandler = new SignOutResponseHandler();
            try {
                HttpGet httpget = new HttpGet(signOutUrl);
                httpget.setHeader(header.getName(), header.getValue());
                String status = httpclient.execute(httpget, responeHandler);
                assertTrue("注销成功", NDPServeImpl.STATUS_SUCCEEDED.equals(status));
            } catch (Exception e) {
                e.printStackTrace();
                fail("注销失败");
            } finally {
                try {
                    httpclient.close();
                } catch (IOException ex) {
                    fail("关闭连接失败");
                }
            }

        }
    }
}

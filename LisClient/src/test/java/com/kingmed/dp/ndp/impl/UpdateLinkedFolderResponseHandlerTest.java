/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.impl;

import com.kingmed.dp.ndp.Constants;
import java.io.IOException;
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
public class UpdateLinkedFolderResponseHandlerTest {

    private NDPServeImpl ndpServe;

    public UpdateLinkedFolderResponseHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ndpServe = NDPServeFactory.getNDPServe();
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testUpdateLinkedFolder() {

        String signinUrl = ndpServe.getUrlSignin();
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

        String uri = ndpServe.getUrlForUpdateLinkedFolders();
        httpclient = HttpClients.createDefault();
        responeHandler = new UpdateLinkedFoldersResponseHandler();

        try {
            HttpGet httpget = new HttpGet(uri);
            httpget.setHeader("Cookie", cookie);
            String status = httpclient.execute(httpget, responeHandler);
            System.out.println("status=================="+status);
            assertTrue("连接状态应该是succeed", status.equals(NDPServeImpl.STATUS_SUCCEEDED));
        } catch (Exception e) {
            e.printStackTrace();
            fail("更新失败");
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                fail("关闭连接失败");
            }
        }

    }
}

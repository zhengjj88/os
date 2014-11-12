/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.impl;

import com.kingmed.dp.ndp.NDPServe;
import java.util.Set;
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
    private static Set<NDPServe> allNDPServes;
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
    public void testSignIn() {
        for(NDPServe ndpServe: allNDPServes){
            String signinUrl = ndpServe.getUrlSignin();
            CloseableHttpClient httpclient = HttpClients.createDefault();
            NDPServeResponseHandler responeHandler = new SignInResponseHandler();
            try {
                HttpGet httpget = new HttpGet(signinUrl);
                String cookie =httpclient.execute(httpget, responeHandler);
                Assert.notNull(cookie);
            } catch (Exception e) {
                e.printStackTrace();
                fail("认证失败");
            }
        }
    }
}

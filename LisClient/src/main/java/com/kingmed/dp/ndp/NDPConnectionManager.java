/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp;

import com.kingmed.dp.ndp.impl.NDPServeImpl;
import com.kingmed.dp.connection.ConnectionManager;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author zhengjunjie
 */
public class NDPConnectionManager implements ConnectionManager {

    private NDPServeImpl ndpServe;

    @Override
    public void connect() throws Exception {
        String signinUrl = ndpServe.getUrlSignin();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(signinUrl);
            String responseBody = httpclient.execute(httpget, new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse hr) throws ClientProtocolException, IOException {
                    int status = hr.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = hr.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpclient.close();
        }
    }

    @Override
    public void disconnect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

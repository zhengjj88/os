/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.aperio;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author zhengjunjie
 */
public class DsClient {

    public static void main(String[] args) throws Exception {
        DsClient.logon();
    }

    public static String logon() throws Exception {
        String token = null;
        String url = "http://192.168.180.132:86/Aperio.Security/Security2.asmx";
        String body = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns=\"http://www.aperio.com/webservices/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"> <SOAP-ENV:Body> <Logon><Token>leQJYfWQ6wv_tJa6hhBZlWwgrRZ-mDywnfb9F4EfC1752Pt07NZDEGvFNYYPvpxkN0IvPTrPi0M=</Token><LoginName>gzuser</LoginName><Password>gzking</Password></Logon></SOAP-ENV:Body> </SOAP-ENV:Envelope> ";

        HttpClient hc = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //httpPost.addHeader("Content-Type", "text/xml;charset=utf-8");
        httpPost.addHeader("SOAPAction", "http://www.aperio.com/webservices/#Logon");
        StringEntity myEntity = new StringEntity(body, ContentType.create("text/xml", "UTF-8"));
        httpPost.setEntity(myEntity);
        System.out.println( myEntity.getContentType());
        System.out.println("Content-Length" + myEntity.getContentLength());
        HttpResponse res = null;
            res = (CloseableHttpResponse) hc.execute(httpPost);
            HttpEntity entity = res.getEntity();
            System.out.println(EntityUtils.toString(entity));
      

        return token;
    }
}

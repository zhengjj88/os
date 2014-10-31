/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhengjunjie
 */
public class NDPServeResponseHandler implements ResponseHandler<String> {

    private static final Logger log = LoggerFactory.getLogger(NDPServeResponseHandler.class);
    private String cookie = null;

    @Override
    public String handleResponse(HttpResponse hr) throws ClientProtocolException, IOException {
        String responseBody = null;
        int status = hr.getStatusLine().getStatusCode();
        HttpEntity entity = hr.getEntity();
        responseBody = (entity != null ? EntityUtils.toString(entity) : null);
        log.info("请求，状态字：" + status+"\r\n请求，结果\r\n[" + responseBody+"]");
        
        if (status >= 200 && status < 300) {
            Header[] cookies = hr.getHeaders("Set-Cookie");
            for (Header c : cookies) {
                String v = c.getValue();
                if (!v.endsWith("=")) {
                    setCookie(v);
                    log.info("cookie=[" + cookie + "]");
                }
            }
//            HttpEntity entity = hr.getEntity();
//            responseBody = (entity != null ? EntityUtils.toString(entity) : null);
//            log.info("请求，结果\r\n" + responseBody);
        }
        return responseBody;
    }

    private void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }

    protected List<Element> checkStatus(String responseBody, String expression) throws JDOMException, IOException {
        List<Element> items = null;
        Reader reader = new StringReader(responseBody);
        SAXBuilder builder = new SAXBuilder();
        Document jdomDoc = null;
        jdomDoc = builder.build(reader);
        XPathFactory xFactory = XPathFactory.instance();
        XPathExpression<Element> expr = xFactory.compile(expression, Filters.element());
        items = expr.evaluate(jdomDoc);
        return items;
    }

}

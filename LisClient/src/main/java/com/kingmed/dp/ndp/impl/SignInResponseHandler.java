/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.impl;

import com.google.common.base.Strings;
import com.kingmed.dp.ndp.NDPServe;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.jdom2.Document;
import org.jdom2.Element;
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
public class SignInResponseHandler extends NDPServeResponseHandler {

    private static final Logger log = LoggerFactory.getLogger(SignInResponseHandler.class);

    @Override
    public String handleResponse(HttpResponse hr) throws ClientProtocolException, IOException {
        String responseBody = null;
        String connectionStatus = null;
        String cookie = null;
        try {
            responseBody = super.handleResponse(hr);
            connectionStatus = checkStatus(responseBody);
            //检测是否登陆成功
            if (Strings.isNullOrEmpty(connectionStatus) || !connectionStatus.equals(NDPServeImpl.STATUS_SUCCEEDED)) {
                log.error("认证失败");
                throw new IOException("认证失败");
            }
        } catch (Exception e) {
            log.error("认证失败", e);
            throw new IOException(e);
        }
        cookie = getCookie();
        log.info("cookie is ["+cookie+"]");
        return cookie;
    }

    /**
     * 处理NDP.serve返回的数据，如XML
     *
     * @param responseBody
     * @return true : NDP.serve处理请求成功,
     * <br/>
     * false : NDP.serve 处理请求失败
     * @throws Exception
     */
    private String checkStatus(String responseBody) throws Exception {
        String status = null;
        String expression = "//" + NDPServeImpl.CONNECTION;
        List<Element> items = null;
        String username;
        String message;
        
        items = checkStatus(responseBody, expression);
        for (Element itemElement : items) {
            status = itemElement.getChildText(NDPServeImpl.STATUS);
            message = itemElement.getChildText(NDPServeImpl.CONNECTION_STATUS_MESSAGE);
            username = itemElement.getChildText(NDPServeImpl.CONNECTION_STATUS_USERNAME);
            log.info("connectin Status=" + status + ",message=" + message + ",username=" + username);
            if (NDPServeImpl.STATUS_SUCCEEDED.equals(status)) {
                break;
            }
        }
        return status;
    }

}

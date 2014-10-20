/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.impl;

import com.google.common.base.Strings;
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
public class SignOutResponseHandler extends NDPServeResponseHandler {

    private static final Logger log = LoggerFactory.getLogger(SignOutResponseHandler.class);

    @Override
    public String handleResponse(HttpResponse hr) throws ClientProtocolException, IOException {
        String responseBody = null;
        String connectionStatus = null;
        try {
            responseBody = super.handleResponse(hr);
            connectionStatus = checkStatus(responseBody);
            if (Strings.isNullOrEmpty(connectionStatus) || !connectionStatus.equals("succeeded")) {
                log.error("注销失败");
                throw new IOException("注销失败");
            }
        } catch (Exception ex) {
            log.error("注销失败", ex);
            throw new IOException(ex);
        }
        return responseBody;
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
        String username;
        String message;
        Reader reader = new StringReader(responseBody);
        SAXBuilder builder = new SAXBuilder();
        Document jdomDoc = null;
        try {
            jdomDoc = builder.build(reader);
        } catch (Exception e) {
            log.error("返回结果出错", e);
            return status;
        }
        XPathFactory xFactory = XPathFactory.instance();
        XPathExpression<Element> expr = xFactory.compile("//" + NDPImageServerImpl.CONNECTION, Filters.element());
        List<Element> items = expr.evaluate(jdomDoc);

        for (Element itemElement : items) {
            status = itemElement.getChildText(NDPImageServerImpl.CONNECTION_STATUS);
            message = itemElement.getChildText(NDPImageServerImpl.CONNECTION_STATUS_MESSAGE);
            username = itemElement.getChildText(NDPImageServerImpl.CONNECTION_STATUS_USERNAME);
            
            log.info("connectin status=" + status + ",message" + message + ",username=" + username);
            if (NDPImageServerImpl.CONNECTION_STATUS_SUCCEEDED.equals(status)) {
                break;
            }
        }
        return status;
    }
    
}

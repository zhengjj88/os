/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.impl;

import com.google.common.base.Strings;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
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
        try {
            responseBody = super.handleResponse(hr);
            connectionStatus = super.checkStatus(responseBody);
            if (Strings.isNullOrEmpty(connectionStatus) || !connectionStatus.equals("succeeded")) {
                log.error("认证失败");
                throw new IOException("认证失败");
            }
        } catch (Exception ex) {
            log.error("认证失败", ex);
            throw new IOException(ex);
        }
        return responseBody;
    }

}

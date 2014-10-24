/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.impl;

import com.google.common.base.Strings;
import java.io.IOException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhengjunjie
 */
public class UpdateLinkedFoldersResponseHandler extends NDPServeResponseHandler{
   
    private static final Logger log = LoggerFactory.getLogger(UpdateLinkedFoldersResponseHandler.class);
    
    @Override
    public String handleResponse(HttpResponse hr) throws ClientProtocolException, IOException {
        String responseBody = null;
        String status = null;
        try {
            responseBody = super.handleResponse(hr);
            if(responseBody == null){
                log.warn("更新文件夹，返回内容为空");
                return status;
            }
            status = checkStatus(responseBody);
            //检测是否登陆成功
            if (Strings.isNullOrEmpty(status) || !status.equals(NDPImageServerImpl.STATUS_SUCCEEDED)) {
                log.error("更新失败");
            }
        } catch (Exception e) {
            log.error("更新失败", e);
            throw new IOException(e);
        }
        return status;
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
    private String checkStatus(String responseBody) throws JDOMException, IOException{
        String status = null;
        String expression = "//" + NDPImageServerImpl.UPDATERESULT;
        List<Element> items = null;
        items = checkStatus(responseBody, expression);
        if(items==null){
            log.warn("更新文件夹，状态字为空");
            return status;
        }
        for (Element itemElement : items) {
            status = itemElement.getChildText(NDPImageServerImpl.STATUS);
            log.info("更新文件夹，Status=" + status);
            if (NDPImageServerImpl.STATUS_SUCCEEDED.equals(status)) {
                break;
            }
            //如果没有登录，服务器返回500代码
            //如果status是failed，会有message提示
        }
        return status;
    }
    
}

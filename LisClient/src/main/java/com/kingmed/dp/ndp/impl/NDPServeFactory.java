/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.impl;

import com.kingmed.dp.ndp.NDPServe;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author zhengjunjie
 */
public class NDPServeFactory {
    private static NDPServeImpl ndpServe = null;
    private static Set<NDPServe> allNdpServes = null;
    public static synchronized NDPServeImpl getNDPServe() {
        if (ndpServe == null) {
            ndpServe = new NDPServeImpl();
            ndpServe.setProtocl("http");
            ndpServe.setHost("www.kingmed.com.cn");
            ndpServe.setPort(81);
            ndpServe.setUsername("");
            ndpServe.setPassword("");
        }
        return ndpServe;
    }
    
    /**
     * 查询所有的NDP服务器
     * @return 
     */
    public static synchronized Set<NDPServe> getAllNDPServes() {
        if (allNdpServes == null) {
            allNdpServes = new HashSet<NDPServe>();
            
            NDPServeImpl ndpServe = new NDPServeImpl();
            
            ndpServe.setProtocl("http");
            ndpServe.setHost("www.kingmed.com.cn");
            ndpServe.setPort(81);
            ndpServe.setUsername("");
            ndpServe.setPassword("");
            ndpServe.setRootLinkedFolderItemId(130L);
            allNdpServes.add(ndpServe);
            
            ndpServe = new NDPServeImpl();
            ndpServe.setProtocl("http");
            ndpServe.setHost("www.kingmed.com.cn");
            ndpServe.setPort(7090);
            ndpServe.setUsername("");
            ndpServe.setPassword("");
            ndpServe.setRootLinkedFolderItemId(88L);
            
            allNdpServes.add(ndpServe);
            
        }
        return allNdpServes;
    }
}

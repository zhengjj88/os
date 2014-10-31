/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.impl;

import com.kingmed.dp.ndp.impl.NDPServeImpl;

/**
 *
 * @author zhengjunjie
 */
public class NDPServeFactory {
    private static NDPServeImpl ndpServe = null;

    public static synchronized NDPServeImpl getNDPServe() {
        if (ndpServe == null) {
            ndpServe = new NDPServeImpl();
            ndpServe.setProtocl("http");
            ndpServe.setHost("www.kingmed.com.cn");
            ndpServe.setPort(81);
            ndpServe.setUsername("upmcportal");
            ndpServe.setPassword("upmcportalking");
        }
        return ndpServe;
    }
}

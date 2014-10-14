/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.connection;

/**
 *
 * @author zhengjunjie
 */
public interface ImageServer {
    
    String getHost();

    int getPort();

    void setHost(String host);

    void setPort(int port);

    public String getInfo();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.connection;

/**
 * 负责和Web服务器通信
 * @author zhengjunjie
 */
public interface WebCommunicator extends Communicator{
    public String getCookie();
}

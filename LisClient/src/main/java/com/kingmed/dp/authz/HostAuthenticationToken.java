/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.authz;

/**
 * 发起认证请求的主机
 * @author zhengjunjie
 */
public interface HostAuthenticationToken extends AuthenticationToken{
    String getHost();
}

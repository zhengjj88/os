/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.authz;

import java.io.Serializable;

/**
 *
 * @author zhengjunjie
 */
public interface AuthenticationToken extends Serializable{
    /**
     * 用户名或用户ID
     * @return 账号的标识
     */
    public Object getPrincipal();
    
    /**
     * 用户提交的密码
     * @return 密码
     */
    public Object getCredentials();
}

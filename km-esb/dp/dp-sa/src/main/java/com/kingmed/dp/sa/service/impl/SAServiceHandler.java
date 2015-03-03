/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.sa.service.impl;

import com.kingmed.dp.sa.dao.SimpleUserDAO;
import com.kingmed.dp.sa.model.SimpleUser;
import com.kingmed.dp.sa.model.SimpleUserContainer;
import java.util.List;

/**
 *
 * @author zhengjunjie
 */
public class SAServiceHandler {
    private SimpleUserDAO simpleUserDAO;

    public void setSimpleUserDAO(SimpleUserDAO simpleUserDAO) {
        this.simpleUserDAO = simpleUserDAO;
    }
    
    public String getAllExperts(){
        return "{'name':'test'}";
    }
}

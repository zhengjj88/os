/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.sa.service.impl;

import com.kingmed.dp.sa.dao.SimpleUserDAO;
import com.kingmed.dp.sa.service.SimpleUserService;

/**
 *
 * @author zhengjunjie
 */
public class SimpleUserServiceImpl implements SimpleUserService{
    private SimpleUserDAO simpleUserDAO;

    public void setSimpleUserDAO(SimpleUserDAO simpleUserDAO) {
        this.simpleUserDAO = simpleUserDAO;
    }
    
    @Override
    public String getAllExperts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

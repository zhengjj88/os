/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.sa.dao;

import com.kingmed.dp.sa.model.SimpleUser;
import java.util.List;

/**
 *
 * @author zhengjunjie
 */
public interface SimpleUserDAO {
    
    /**
     * 根据角色查询域用户信息
     * @param roleName
     * @return 
     */
    public List<SimpleUser> getSimpleUserByRole(String roleName);
}

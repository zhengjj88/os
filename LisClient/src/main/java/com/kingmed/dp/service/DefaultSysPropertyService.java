/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.service;

import com.kingmed.dp.dao.SysPropertyDAO;
import com.kingmed.dp.model.SysProperty;
import com.kingmed.dp.ndp.NDPServe;
import com.kingmed.dp.ndp.impl.NDPServeImpl;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zhengjunjie
 */
@Transactional
@Service("sysPropertyService")
public class DefaultSysPropertyService implements SysPropertyService{
    private SysPropertyDAO sysPropertyDAO;

    @Autowired
    public void setSysPropertyDAO(SysPropertyDAO sysPropertyDAO) {
        this.sysPropertyDAO = sysPropertyDAO;
    }
    
    @Override
    public List<SysProperty> getAllSysProperties() {
        return sysPropertyDAO.getAllSysProperties();
    }

    @Override
    public List<NDPServe> getAllNDPServes() {
        List<NDPServe> ndpServes = new ArrayList<NDPServe>();
        String section="NDP-UPMC";
        List<SysProperty> sysProperties = sysPropertyDAO.findSysProperties(section);
        NDPServeImpl serve = new NDPServeImpl();
        for(SysProperty p : sysProperties){
            try {
                String code = p.getCode();
                String value =p.getValue();
                BeanUtils.setProperty(serve, code, value);
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
        return ndpServes;
    }
    
}

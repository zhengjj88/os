/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.service;

import com.kingmed.dp.model.SysProperty;
import com.kingmed.dp.ndp.NDPServe;
import java.util.List;

/**
 *
 * @author zhengjunjie
 */
public interface SysPropertyService {
    List<SysProperty> getAllSysProperties();
    List<NDPServe> getAllNDPServes();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.sa.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zhengjunjie
 */
@XmlRootElement
public class SimpleUserContainer {
    
    public List<SimpleUser> simpleUserList;

    public List<SimpleUser> getSimpleUserList() {
        return simpleUserList;
    }

    public void setSimpleUserList(List<SimpleUser> simpleUserList) {
        this.simpleUserList = simpleUserList;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.job.scheduler;

import com.kingmed.dp.model.SysProperty;
import com.kingmed.dp.service.SysPropertyService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author zhengjunjie
 */
public class TestApp {

    private static ApplicationContext ctx;

    public static void main(String[] args) {
        String[] xml = new String[]{"applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(xml);
        List<SysProperty> allSysProperties = null;
        SysPropertyService serv = (SysPropertyService) ctx.getBean("sysPropertyService");
        allSysProperties = serv.getAllSysProperties();
        for (SysProperty p : allSysProperties) {
            System.out.println("..........." + p);
        }
    }
}

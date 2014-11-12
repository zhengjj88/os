/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author zhengjunjie
 */
public class ApplicationContextFactory {
   private static ApplicationContext context = null;
   
   public static ApplicationContext getContext(){
       if(context == null){
           context = new ClassPathXmlApplicationContext("applicationContext.xml");
       }
       return context;
   }
   
}

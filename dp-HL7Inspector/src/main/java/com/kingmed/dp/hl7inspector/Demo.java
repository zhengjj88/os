/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.hl7inspector;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author zhengjunjie
 */
public class Demo {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("file:f:/camel/from/?delay=3000").to("file:f:/camel/to");
            }
        });
        context.start();
        boolean loop = true;
        while (loop) {
            Thread.sleep(25000);
        }
        context.stop();
    }
}


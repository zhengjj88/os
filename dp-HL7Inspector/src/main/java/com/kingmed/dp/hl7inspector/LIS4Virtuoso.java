/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.hl7inspector;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.Main;

/**
 *
 * @author zhengjunjie
 */
public class LIS4Virtuoso {
    public static void main(String[] args) throws Exception {
        System.out.println("...........................................");
        // Main makes it easier to run a Spring application
        Main main = new Main();
        // configure the location of the Spring XML file
        main.setApplicationContextUri("META-INF/spring/camel-context.xml");
        main.addRouteBuilder(new RouteBuilder() {
            public void configure() {
                from("hl7listener").to("patientLookupService");
            }
        });
        // enable hangup support allows Camel to detect when the JVM is terminated
        main.enableHangupSupport();
        // run and block until Camel is stopped (or JVM terminated)
        main.run();
    }
}

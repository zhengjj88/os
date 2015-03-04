/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.tc.msg.consumer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 上传标本信息处理器
 * @author zhengjunjie
 */
public class SpecimenRequestProcessor implements Processor{
    private static final Logger log = LoggerFactory.getLogger(SpecimenRequestProcessor.class);
    @Override
    public void process(Exchange msg) throws Exception {
        log.trace("Processing msg {}", msg);
        String record = msg.getIn().getBody(String.class);
        log.info("Processing record {}", record);
        // Do something useful with this record.
    }
    
    private String processSpecimen(String record){
        return null;
    }
    
}

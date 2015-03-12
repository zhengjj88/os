/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.bean;

import com.kingmed.yuyt.util.Constants;
import com.kingmed.yuyt.util.XMLHandler;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理从LIS返回的消息
 *
 * @author zhengjunjie
 */
public class SpecimenResponseBean implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(SpecimenResponseBean.class);

    @Override
    public void process(Exchange msg) throws Exception {
        logger.trace("处理上传标本的返回值，消息 {}", msg);
        String record = msg.getIn().getBody(String.class);
        logger.info("处理上传标本的返回值，记录 {}", record);
        
        Map<String, String> map = XMLHandler.transXmltoMapForSpecRes(record);
        String status = map.get("status");
        String docId = map.get("docId");
        if(status.equals(Constants.LIS_S)){
            msg.getOut().setBody(map);
        }
    }

}

/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.bean;

import com.kingmed.yuyt.util.Constants;
import com.kingmed.yuyt.util.XMLHandler;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhengjunjie
 */
public class SMSResponseBean implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(SMSResponseBean.class);

    /**
     * 处理从MQ监听到的磐志返回值，处理并插入到域医通的数据库中
     * @param msg
     * @throws Exception 
     */
    @Override
    public void process(Exchange msg) throws Exception {
        String record = msg.getIn().getBody(String.class);

        Map<String, String> response = XMLHandler.transSimpleXmltoMap(record);
        String CompanyCode = response.get("CompanyCode") != null ? response.get("CompanyCode").toString() : null;
        String HospCode = response.get("HospCode") != null ? response.get("HospCode").toString() : null;
        String RequestCode = response.get("RequestCode") != null ? response.get("RequestCode").toString() : null;
        
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("enable", Constants.ENABLE_YES);
        map.put("msg_type", Constants.MSG_TYPE_SEND_SMS_RESPONSE);
        map.put("doc_id", response.get("doc_id"));//磐志返回的状态值
        map.put("sub_company",CompanyCode);
        map.put("hospital",HospCode);
        map.put("km_barcode",RequestCode);
        map.put("direction", Constants.MSG_IN);
        map.put("msg", record);
        map.put("create_time", new Timestamp(System.currentTimeMillis()));
        map.put("status", Constants.MSG_STATUS_NEW);//创建成功
        map.put("sms_status", response.get("status"));//磐志返回的状态值
        msg.getOut().setBody(map);
    }

}

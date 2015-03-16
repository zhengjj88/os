/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.bean;

import com.kingmed.yuyt.model.SMSResponseVO;
import com.kingmed.yuyt.util.Constants;
import com.kingmed.yuyt.util.XMLHandler;
import java.sql.Timestamp;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhengjunjie
 */
public class SMSBean implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(SMSBean.class);

    private PanzhiBean panZhiBean;

    public PanzhiBean getPanZhiBean() {
        return panZhiBean;
    }

    public void setPanZhiBean(PanzhiBean panZhiBean) {
        this.panZhiBean = panZhiBean;
    }
    
    /**
     * 转换报文，将其上传到消息队列
     * @param map 
     */
    public void convertSMS(Map<String, Object> map){
        String CompanyCode = (String) map.get("sub_company");//子公司代码
        String HospCode = (String) map.get("hospital");//医院代码
        String RequestCode = (String)map.get("km_barcode");//金域条码
        String doc_id = (String)map.get("doc_id");//金域条码
        
        String msg = (String) map.get("msg");//短信内容，包括短信签名
        msg = msg.trim();
        int idx = msg.indexOf("【");
        String msgContent =msg.substring(0,idx);//短信内容;
        String smsSignature = msg.substring(idx);//短信签名
        String destnumbers = (String) map.get("destnumbers");
        
        StringBuilder sb = new StringBuilder();
        sb.append("<request>")
          .append("<CompanyCode>").append(CompanyCode).append("</CompanyCode>")
          .append("<HospCode>").append(HospCode).append("</HospCode>")
          .append("<RequestCode>").append(RequestCode).append("</RequestCode>")
          .append("<doc_id>").append(doc_id).append("</doc_id>")     
          .append("<destnumbers>").append(destnumbers).append("</destnumbers>")
          .append("<msgContent>").append(msgContent).append("</msgContent>")
          .append("<smsSignature>").append(smsSignature).append("</smsSignature>")
          .append("</request>");
        
        map.put("enable", Constants.ENABLE_YES);
        map.put("msg_type", Constants.MSG_TYPE_SEND_SMS);
        map.put("direction", Constants.MSG_OUT);//上传标本信息
        map.put("msg", sb.toString());
        map.put("create_time", new Timestamp(System.currentTimeMillis()));
        map.put("status", Constants.MSG_STATUS_NEW);//创建成功
    }
    
    /**
     * 从消息队列中中监听消息，然后代用磐志的接口，发送短信息
     * @param msg
     * @throws Exception 
     */
    @Override
    public void process(Exchange msg) throws Exception {
        logger.trace("处理阳性召回SMS，消息 {}", msg);
        String record = msg.getIn().getBody(String.class);
        logger.info("处理阳性召回SMS，记录 {}", record);
        Map paramMap = XMLHandler.transSimpleXmltoMap(record);
        SMSResponseVO sMSResponseVO=this.panZhiBean.sendMsgByPost(paramMap);
        String CompanyCode = paramMap.get("CompanyCode") != null ? paramMap.get("CompanyCode").toString() : null;
        String HospCode = paramMap.get("HospCode") != null ? paramMap.get("HospCode").toString() : null;
        String RequestCode = paramMap.get("RequestCode") != null ? paramMap.get("RequestCode").toString() : null;
        
        String doc_id = paramMap.get("doc_id") != null ? paramMap.get("doc_id").toString() : null;
        StringBuilder res = new StringBuilder();
        res.append("<response>")
           .append("<CompanyCode>").append(CompanyCode).append("</CompanyCode>")
           .append("<HospCode>").append(HospCode).append("</HospCode>")
           .append("<RequestCode>").append(RequestCode).append("</RequestCode>")   
           .append("<src>").append(Constants.APP_PANZHI).append("</src>")
           .append("<dest>").append(Constants.APP_YUYT).append("</dest>")
           .append("<tranType>").append(Constants.MSG_TYPE_SEND_SMS_RESPONSE).append("</tranType>")     
           .append("<doc_id>").append(doc_id).append("</doc_id>")
           .append("<status>").append(sMSResponseVO.getState()).append("</status>")
           .append("<remark>").append(sMSResponseVO.getStateRemark()).append("</remark>")
           .append("</response>");
        msg.getOut().setBody(res.toString());
    }
    
    

}

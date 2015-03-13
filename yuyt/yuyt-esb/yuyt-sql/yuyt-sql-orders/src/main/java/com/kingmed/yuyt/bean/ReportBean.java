/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.bean;

import com.kingmed.lis.ws.client.LISClient;
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
public class ReportBean implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(ReportBean.class);
    private Map<String, LISClient> lisClients;

    public void setLisClients(Map<String, LISClient> lisClients) {
        this.lisClients = lisClients;
    }

    public Map<String, LISClient> getLisClients() {
        return lisClients;
    }

    @Override
    public void process(Exchange msg) throws Exception {
        logger.trace("处理查询报告单，消息 {}", msg);
        String record = msg.getIn().getBody(String.class);
        logger.info("处理查询报告单，记录 {}", record);
        
        Map<String, String> paramMap = XMLHandler.transSimpleXmltoMap(record);
        String companyCode = null;
        String hospCode = null;
        
        companyCode = paramMap.get("companyCode") != null ? paramMap.get("companyCode") : null;
        hospCode = paramMap.get("hospCode") != null ? paramMap.get("hospCode") : null;
        String docId = paramMap.get("docId") != null ? paramMap.get("docId") : null;
        
        String requestCode = paramMap.get("requestCode") != null ? paramMap.get("requestCode") : null;
        
        LISClient lisClient = this.lisClients.get(companyCode);//根据子公司查询lis客户端
        String t = lisClient.sendRequest4QueryReport(hospCode, requestCode);
        t = t.replace("<?xml version=\"1.0\" encoding=\"gb2312\"?>", "");
        StringBuilder res = new StringBuilder();
        res.append("<response>")
           .append("<docid>").append(docId).append("</docid>")
           .append("<src>").append(Constants.APP_LIS).append("</src>")
           .append("<dest>").append(Constants.APP_YUYT).append("</dest>")
           .append("<sub_company>").append(companyCode).append("</sub_company>")
           .append("<hospital>").append(hospCode).append("</hospital>")
           .append("<km_barcode>").append(requestCode).append("</km_barcode>")
           .append("<msg_type>").append(Constants.MSG_TYPE_QUERY_REPORT).append("</msg_type>")     
           .append(t)
           .append("</response>");
        
        msg.getOut().setBody(res.toString());
    }
    
    public void convertReport(Map<String, Object> data){
        String docId = (String) data.get("id");
        String kmbarcode = (String)data.get("item_p_kmbarcode");
        String hospitalcode = (String) data.get("item_p_hospitalcode");
        String companycode = (String) data.get("item_p_companycode");
        
        String msg = "";
        msg += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        msg += "<Data>";
        msg += "<hospCode>" + hospitalcode + "</hospCode>";
        msg += "<companyCode>" + companycode + "</companyCode>";
        msg += "<docId>" + docId + "</docId>";
        msg += "<requestCode>" + kmbarcode + "</requestCode>";
        msg += "</Data>";
        
        data.put("docid", docId);
        data.put("enable", Constants.ENABLE_YES);
        data.put("msg_type", Constants.MSG_TYPE_QUERY_REPORT);
        data.put("sub_company", companycode);
        data.put("hospital", hospitalcode);
        data.put("km_barcode", kmbarcode);
        data.put("direction", Constants.MSG_OUT);//上传标本信息
        data.put("msg", msg);
        data.put("create_time", new Timestamp(System.currentTimeMillis()));
        data.put("status", Constants.MSG_STATUS_NEW);//创建成功
        
    }
    
    

}

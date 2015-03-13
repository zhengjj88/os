/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.bean;

import com.kingmed.yuyt.util.Constants;
import com.kingmed.yuyt.util.XMLHandler;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhengjunjie
 */
public class ReportResponseBean implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(ReportResponseBean.class);
    private List<String> hpvHighRish17 =null;

    public List<String> getHpvHighRish17() {
        return hpvHighRish17;
    }

    public void setHpvHighRish17(List<String> hpvHighRish17) {
        this.hpvHighRish17 = hpvHighRish17;
    }

    @Override
    public void process(Exchange msg) throws Exception {
        String record = msg.getIn().getBody(String.class);

        Map<String, Object> map = XMLHandler.transXmltoMapForReport(record);

        map.put("enable", Constants.ENABLE_YES);
        map.put("msg_type", Constants.MSG_TYPE_QUERY_REPORT_RESPONSE);
        map.put("direction", Constants.MSG_IN);
        map.put("msg", map.get("Data"));
        map.put("create_time", new Timestamp(System.currentTimeMillis()));
        map.put("status", Constants.MSG_STATUS_NEW);//创建成功

        //检测
        checkPositive(map);
        msg.getOut().setBody(map);

    }

    public static void main(String[] args) throws Exception{
        Map <String,Object> map = new HashMap<String,Object>();
        String xml="";
        map.put("msg", xml);
        new ReportResponseBean().checkPositive(map);
    }
    
    /**
     * 增加阳性标示
     * @param map
     * @throws DocumentException 
     */
    public void checkPositive(Map<String, Object> map) throws DocumentException {
        String xml = (String)map.get("msg");
        String expDataRow ="/Data/Data_Row" ;
        String expNaturalItem = "/Data/Data_Row[1]/NaturalItemName";//自然项目代码表达式

        String cp = "/Data/Data_Row[NaturalItem=5105]";//细胞病理项目代码
        String cp1 = "/Data/Data_Row[NaturalItem=5903]";//
        String expResult = "/Data/Data_Row/Result";
        String result = null;
        Document doc = DocumentHelper.parseText(xml);
        Node node = doc.selectSingleNode(expNaturalItem);
        String naturalItemName = node.getText();
        String singleItemName =null;
        List<Element> cpElms = doc.selectNodes(cp);
        map.put(Constants.POSITIVE, Constants.POSITIVE_U);
        if (cpElms != null && cpElms.size() > 0) {            
            String positive = checkPositive4CP(doc, expResult);
            map.put(Constants.POSITIVE, positive);
            return;
        }
        
        cpElms = doc.selectNodes(cp1);
        if (cpElms != null && cpElms.size() > 0) {            
            String positive = checkPositive4CP(doc, expResult);
            map.put(Constants.POSITIVE, positive);
            return;
        }

        if (naturalItemName.contains("高危型HPV")) {//高危型HPV
            result = doc.selectSingleNode(expResult).getText();
            if (result.contains("阳")) {
                map.put(Constants.POSITIVE, Constants.POSITIVE_Y);
            } else {
                map.put(Constants.POSITIVE, Constants.POSITIVE_N);
            }
            return;
        }
        
        if (naturalItemName.contains("HPV分型检测")) {//高危型HPV
            List<Element> es = doc.selectNodes(expDataRow);
            for(Element e: es){
                singleItemName = e.element("SingleItemName").getName();
                result = e.element("Result").getText();
                if(this.hpvHighRish17.contains(singleItemName)&&result.contains("阳")){
                    map.put(Constants.POSITIVE, Constants.POSITIVE_Y);
                    break;
                }
            }
            map.put(Constants.POSITIVE, Constants.POSITIVE_N);
            return;
        }
    }

    private String checkPositive4CP(Document doc, String expResult) {//细胞病理检测项目
        String result;
        result = doc.selectSingleNode(expResult).getText();
        if (result.contains("ASCUS") 
            || result.contains("AGUS") 
            || result.contains("ASC-H") 
            || result.contains("LSIL") 
            || result.contains("HSIL") 
            || result.contains("CA") 
            || result.contains("CIN")) {
            
            return Constants.POSITIVE_Y;
        } else {
            return Constants.POSITIVE_N;
        }
    }

}

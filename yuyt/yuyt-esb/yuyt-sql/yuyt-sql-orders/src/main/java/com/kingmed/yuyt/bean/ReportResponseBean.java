/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.bean;

import com.kingmed.yuyt.util.Constants;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    private List<String> hpvHighRish17 = new ArrayList<String>();

    public List<String> getHpvHighRish17() {
        return hpvHighRish17;
    }

    public void setHpvHighRish17(List<String> hpvHighRish17) {
        this.hpvHighRish17 = hpvHighRish17;
    }

    @Override
    public void process(Exchange msg) throws Exception {
        String record = msg.getIn().getBody(String.class);

        Document doc = DocumentHelper.parseText(record);
        Map<String, Object> map = new HashMap<String, Object>();
        String report_status = doc.selectSingleNode(Constants.EXP_REPORT_STATUS).getText();
        String report_detail_status = doc.selectSingleNode(Constants.EXP_REPORT_DETAIL_STATUS).getText();
        String isReimbu = doc.selectSingleNode(Constants.EXP_RESPONSE_IS_ISREIMBU).getText();
        String speStatus = doc.selectSingleNode(Constants.EXP_RESPONSE_SPE_STATUS).getText();
        String reportFile = "";
        String isPositive = Constants.POSITIVE_X;
        String docId = doc.selectSingleNode(Constants.EXP_DOC_ID).getText();
        String subCompany = doc.selectSingleNode(Constants.EXP_SUB_COMPANY).getText();
        String hospitial = doc.selectSingleNode(Constants.EXP_HOSPITAL).getText();
        String kmBarcode = doc.selectSingleNode(Constants.EXP_KM_BARCODE).getText();
        String spec_doc_id = doc.selectSingleNode(Constants.EXP_SPEC_DOC_ID).getText();

        map.put("enable", Constants.ENABLE_YES);
        map.put("msg_type", Constants.MSG_TYPE_QUERY_REPORT_RESPONSE);
        map.put("doc_id", docId);// 报告单 id
        map.put("spec_doc_id", spec_doc_id);// 标本 id
        map.put("sub_company", subCompany);
        map.put("hospital", hospitial);
        map.put("km_barcode", kmBarcode);
        map.put("direction", Constants.MSG_IN);
        map.put("msg", record);
        map.put("create_time", new Timestamp(System.currentTimeMillis()));
        map.put("status", Constants.MSG_STATUS_NEW);// 创建成功
        map.put("report_status", report_status);
        map.put("report_detail_status", report_detail_status);
        map.put("IsReimbu", isReimbu);
        map.put("speStatus", speStatus);

        // 处理正常报告单并判断是否阳性
        if (report_status.equals(Constants.LIS_S)) {
            reportFile = doc.selectSingleNode(Constants.EXP_REPORT_FILE).getText();
            isPositive = this.checkPositive(doc);// 检测是否阳性
        }

        // 处理退单报告单并获取报告内容
        if (Constants.LIS_ISREIMBU_Y.equals(isReimbu)) {
            reportFile = doc.selectSingleNode(Constants.EXP_REPORT_FILE).getText();
        }
        map.put("reportfile", reportFile);
        map.put(Constants.POSITIVE, isPositive);

        msg.getOut().setBody(map);

    }

    /**
     * 增加阳性标示
     *
     * @param map LIS返回的报告单详情，参考LIS双向对接服务器的接口文档 queryRequestDetail
     * @throws DocumentException
     */
    public String checkPositive(Document doc) throws DocumentException {
        String re = Constants.POSITIVE_X;
        String expDataRow = "/response/report_detail/Data/Data_Row";
        String expNaturalItem = "/response/report_detail/Data/Data_Row[1]/NaturalItemName";// 自然项目代码表达式

        String cp = "/response/report_detail/Data/Data_Row[NaturalItem=5105]";// 细胞病理项目代码
        String cp1 = "/response/report_detail/Data/Data_Row[NaturalItem=5903]";// 细胞病理项目代码
        String expResult = "/response/report_detail/Data/Data_Row/Result";
        String result = null;
        Node node = doc.selectSingleNode(expNaturalItem);
        String naturalItemName = node.getText();
        String singleItemName = null;
        List<Element> cpElms = doc.selectNodes(cp);
        if (cpElms != null && cpElms.size() > 0) {
            re = checkPositive4CP(doc, expResult);
            return re;
        }
        cpElms = doc.selectNodes(cp1);
        if (cpElms != null && cpElms.size() > 0) {
            re = checkPositive4CP(doc, expResult);
            return re;
        }

        if (naturalItemName.contains("高危型HPV")) {// 高危型HPV
            re = Constants.POSITIVE_N;
            result = doc.selectSingleNode(expResult).getText();
            if (result.contains("阳")) {
                re = Constants.POSITIVE_Y;
            }
            return re;
        }

        if (naturalItemName.contains("HPV分型检测")) {// 高危型HPV
            re = Constants.POSITIVE_N;
            List<Element> es = doc.selectNodes(expDataRow);
            for (Element e : es) {
                singleItemName = e.element("SingleItemName").getText();
                result = e.element("Result").getText();
                if (this.hpvHighRish17.contains(singleItemName) && result.contains("阳")) {
                    re = Constants.POSITIVE_Y;
                    return re;
                }
            }
        }
        return re;
    }

    /**
     * @author spike
     * @date 2015/04/13
     * @desc 检查细胞病理检测项目是否为阳性
     */
	private String checkPositive4CP(Document doc, String expResult) {
		String result = Constants.EMPTY_STR;
		result = doc.selectSingleNode(expResult).getText();

		/**
		 * 筛选关键字
		 * 原有 (ASCUS/AGUS/ASC-H/LSIL/HSIL/CA/CIN)
		 * 新增 (非典型鳞状细胞/上皮内低度病变/上皮内高度病变/非典型腺细胞/腺癌/鳞状细胞癌)
		 */
		if (result.contains("ASCUS")
				|| result.contains("AGUS")
				|| result.contains("ASC-H")
				|| result.contains("LSIL")
				|| result.contains("HSIL")
				|| result.contains("CA")
				|| result.contains("CIN")
				|| result.contains("非典型鳞状细胞")
				|| result.contains("上皮内低度病变")
				|| result.contains("上皮内高度病变")
				|| result.contains("非典型腺细胞")
				|| result.contains("腺癌")
				|| result.contains("鳞状细胞癌")) {
			return Constants.POSITIVE_Y;
		} else {
			return Constants.POSITIVE_N;
		}
	}

}

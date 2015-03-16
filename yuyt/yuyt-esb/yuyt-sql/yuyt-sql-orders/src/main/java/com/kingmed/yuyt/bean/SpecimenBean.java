/**
 * Licensed to the Kingmed .
 */
package com.kingmed.yuyt.bean;

import com.kingmed.yuyt.util.Constants;
import com.kingmed.lis.ws.client.LISClient;
import com.kingmed.yuyt.util.XMLHandler;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 上传标本信息
 *
 * @author zhengjunjie
 */
public class SpecimenBean implements Processor {

    private static final Logger log = LoggerFactory.getLogger(SpecimenBean.class);
    private static final SimpleDateFormat DF_CREATE_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//LIS中要求的创建日期格式

    private Map<String, LISClient> lisClients;

    public SpecimenBean() {
        log.info("构造函数");
    }

    public void setLisClients(Map<String, LISClient> lisClients) {
        this.lisClients = lisClients;
    }

    public Map<String, LISClient> getLisClients() {
        return lisClients;
    }

    @Override
    public void process(Exchange msg) throws Exception {
        log.trace("处理上传标本，消息 {}", msg);
        String record = msg.getIn().getBody(String.class);
        log.info("处理上传标本，记录 {}", record);
        
        Map<String, String> paramMap = XMLHandler.transXmltoMapForSpec(record);
        String companyCode = null;
        String hospCode = null;

        String systemCode = paramMap.get("systemCode") != null ? paramMap.get("systemCode") : null;
        String doc_id = paramMap.get("doc_id") != null ? paramMap.get("doc_id") : null;
        companyCode = paramMap.get("companyCode") != null ? paramMap.get("companyCode") : null;
        hospCode = paramMap.get("hospCode") != null ? paramMap.get("hospCode") : null;
        String specimen = paramMap.get("specimen") != null ? paramMap.get("specimen") : null;
        //requestInfoXml = URLDecoder.decode(requestInfoXml,"UTF-8");
        String domainUserId = paramMap.get("domainUserId") != null ? paramMap.get("domainUserId") : null;
        String applicationId = paramMap.get("applicationId") != null ? paramMap.get("applicationId") : null;
        String errorMsg = null;

        LISClient lisClient = this.lisClients.get(companyCode);//根据子公司查询lis客户端
        String r = lisClient.sendRequestInfo(hospCode, specimen);
        if(!r.equals(Constants.LIS_S)){
            throw new Exception("上传标本信息失败");
        }
        StringBuilder res = new StringBuilder();
        res.append("<response>")
           .append("<src>").append(Constants.APP_LIS).append("</src>")
           .append("<dest>").append(Constants.APP_YUYT).append("</dest>")
           .append("<tranType>").append(Constants.MSG_TYPE_UPLOAD_SPECIMEN).append("</tranType>")     
           .append("<doc_id>").append(doc_id).append("</doc_id>")
           .append("<status>").append(r).append("</status>")
           .append("</response>");
        msg.getOut().setBody(res.toString());
    }

    /**
     * 转换标本信息
     *
     * @param spec
     */
    public void convertSpecimen(Map<String, Object> spec) throws UnsupportedEncodingException {
        String doc_id = (String) spec.get("doc_id");
        String userId = (String) spec.get("author");
        String applicationId = (String) spec.get("applicationid");
        String checktype = (String) spec.get("item_p_checktype");
        String testprogram = (String) spec.get("item_p_programs");
        String kmbarcode = (String) spec.get("item_p_kmbarcode");
        String hospitalbarcode = (String) spec.get("item_p_hospitalbarcode");
        String mobileno = (String) spec.get("item_p_mobileno");
        String name = (String) spec.get("item_p_name");
        String sex = (String) spec.get("item_p_sex");
        String nation = (String) spec.get("item_p_nation");
        Timestamp borndate = (Timestamp) spec.get("item_p_borndate");
        String idno = (String) spec.get("item_p_idno");
        String address = (String) spec.get("item_p_address");
        String collector = (String) spec.get("item_p_collector");
        Timestamp collecttime = (Timestamp) spec.get("item_p_collectiontiime");
        String registrar = (String) spec.get("item_p_registrar");
        Timestamp registrationtime = (Timestamp) spec.get("item_p_registrationtime");
        String companycode = (String) spec.get("item_p_companycode");
        String hospitalcode = (String) spec.get("item_p_hospitalcode");
        String statu = (String) spec.get("item_p_status");
        String testStr = (String) spec.get("item_p_test_name");
        String hospitalname = (String) spec.get("item_p_hospitalname");
        if (kmbarcode.indexOf(Constants.KMBARCODE_SPLITTER) > 0) {
            kmbarcode = kmbarcode.substring(0, kmbarcode.indexOf(Constants.KMBARCODE_SPLITTER));  // 截取金域条码的母码。
        }

        if (hospitalbarcode == null || hospitalbarcode == "") {
            hospitalbarcode = kmbarcode;
        }

        if (companycode.toUpperCase().indexOf(Constants.PRE_COMPANY_CODE) < 0) {
            companycode = Constants.PRE_COMPANY_CODE + companycode;
        }
        if (hospitalcode.toUpperCase().indexOf(Constants.PRE_HOSPTITAL_CODE) < 0) {
            hospitalcode = Constants.PRE_HOSPTITAL_CODE + hospitalcode;
        }

        companycode = companycode.toUpperCase().replace(Constants.PRE_COMPANY_CODE, "");
        hospitalcode = hospitalcode.toUpperCase().replace(Constants.PRE_HOSPTITAL_CODE, "");

        // LIS系统要求传入年龄参数。
        String age = getAge(borndate);
        String ageUnit = Constants.AGE_UNIT_YEAR;

        String sexStr = getSexByCode(sex);

        String registrationtimeStr = DF_CREATE_DATE.format(new Date(registrationtime.getTime()));
        
        String patientInfoXml="";
        patientInfoXml += "<Data>";
        patientInfoXml += "<Data_Row>";
        patientInfoXml += "<biztype>" + Constants.BIZTYPE + "</biztype>";
        patientInfoXml += "<HospCode>" + hospitalcode + "</HospCode>";
        patientInfoXml += "<HospName>" + hospitalname + "</HospName>";
        patientInfoXml += "<OutSourceCode></OutSourceCode>";
        patientInfoXml += "<SampleNumber></SampleNumber>";
        patientInfoXml += "<RecorderName></RecorderName>";
        patientInfoXml += "<CreateDate>" + registrationtimeStr + "</CreateDate>";     // 2013-09-03 11:00:58
        patientInfoXml += "<Remark/>";
        patientInfoXml += "<RequestCode>" + kmbarcode + "</RequestCode>";
        patientInfoXml += "<HospSampleID>" + kmbarcode + "</HospSampleID>";
        patientInfoXml += "<HospSampleNumber></HospSampleNumber>";
        patientInfoXml += "<PatientNumber>" + kmbarcode + "</PatientNumber>";
        patientInfoXml += "<BedNumber></BedNumber>";
        patientInfoXml += "<SamplingDate></SamplingDate>";
        patientInfoXml += "<Name>" + name + "</Name>";
        patientInfoXml += "<Sex>" + sexStr + "</Sex>";
        patientInfoXml += "<Age>" + age + "</Age>";
        patientInfoXml += "<AgeUnit>" + ageUnit + "</AgeUnit>";
        patientInfoXml += "<PatientTel/>";
        patientInfoXml += "<SectionOffice></SectionOffice>";
        patientInfoXml += "<Dcotor></Dcotor>";
        patientInfoXml += "<DcotorTel/>";
        patientInfoXml += "<Diagnostication/>";
        patientInfoXml += "<NaturalItem>" + testprogram + "</NaturalItem>";
        patientInfoXml += "<NaturalItemDesc>" + testStr + "</NaturalItemDesc>";
        patientInfoXml += "<HospItem>" + testprogram + "</HospItem>";
        patientInfoXml += "<HospItemCode>" + testStr + "</HospItemCode>";
        patientInfoXml += "<Birt/>";
        patientInfoXml += "<Ori/>";
        patientInfoXml += "<SamName/>";
        patientInfoXml += "</Data_Row>";
        patientInfoXml += "</Data>";

        String patientInfo = patientInfoXml;

        String strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        strXml += "<Data>";
        strXml += "<systemCode>KMTC</systemCode>";
        strXml += "<hospCode>" + hospitalcode + "</hospCode>";
        strXml += "<companyCode>" + companycode + "</companyCode>";
        strXml += "<domainUserId>" + userId + "</domainUserId>";
        strXml += "<applicationId>" + applicationId + "</applicationId>";
        strXml += "<doc_id>" + doc_id + "</doc_id>";
        strXml += "<specimen>" + patientInfo + "</specimen>"; 
        strXml += "</Data>";

        //upload
        spec.put("doc_id", doc_id);
        spec.put("enable", Constants.ENABLE_YES);
        spec.put("msg_type", Constants.MSG_TYPE_UPLOAD_SPECIMEN);
        spec.put("sub_company", companycode);
        spec.put("hospital", hospitalcode);
        spec.put("km_barcode", kmbarcode);
        spec.put("direction", Constants.MSG_OUT);//上传标本信息
        spec.put("msg", strXml);
        spec.put("create_time", new Timestamp(System.currentTimeMillis()));
        spec.put("status", Constants.MSG_STATUS_NEW);//创建成功

    }

    private String getSexByCode(String sex) {
        if ("1001".equals(sex)) {
            return Constants.SEX_F;
        } else if ("1002".equals(sex)) {
            return Constants.SEX_M;
        } else if ("1003".equals(sex)) {
            return Constants.SEX_U;
        } else {
            return "";
        }
    }

    private String getAge(Timestamp borndate) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(borndate.getTime());
        int yearBorn = c.get(Calendar.YEAR);//出生年份;
        Calendar today = Calendar.getInstance();
        int yearNow = today.get(Calendar.YEAR);
        return String.valueOf(yearNow - yearBorn + 1);
    }

}

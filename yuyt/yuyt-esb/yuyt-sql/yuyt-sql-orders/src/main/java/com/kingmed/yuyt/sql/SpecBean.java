/**
 * Licensed to the Kingmed .
 */
package com.kingmed.yuyt.sql;

import java.sql.Timestamp;
import java.util.Map;

/**
 * 上传标本信息
 *
 * @author zhengjunjie
 */
public class SpecBean {

    public void processSpec(Map<String, Object> spec) {
        String docid = (String) spec.get("id");
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
        String testStr =  (String) spec.get("item_p_test_name");
        String hospitalname =(String)spec.get("item_p_hospitalname"); 
        if (kmbarcode.indexOf("-") > 0 ) {
	          kmbarcode = kmbarcode.substring(0, kmbarcode.indexOf("-"));  // 截取金域条码的母码。
	    }

        if ( hospitalbarcode == null || hospitalbarcode =="" ) {
           hospitalbarcode = kmbarcode;
        }
        
        if (companycode.toUpperCase().indexOf("C-") < 0) {
	          companycode = "c-" + companycode;
        }
        if (hospitalcode.toUpperCase().indexOf("H-") < 0) {
           hospitalcode = "h-" + hospitalcode;
        }
        
        String domainId = "11e4-9001-771306cc-8f62-43c551eed6f4";
 
        companycode = companycode.toUpperCase().replace("C-", "");
        hospitalcode = hospitalcode.toUpperCase().replace("H-", "");

        // LIS系统要求传入年龄参数。
        String age = "";
        String ageUnit = "岁";
        
        String sexStr = getSexByCode( sex );
        
        String patientInfoXml =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        patientInfoXml += "<Data>";
        patientInfoXml += "<Data_Row>";
        patientInfoXml += "<biztype>1</biztype>";
        patientInfoXml += "<HospCode>" + hospitalcode + "</HospCode>";
        patientInfoXml += "<HospName>" + hospitalname + "</HospName>";  
        patientInfoXml += "<OutSourceCode></OutSourceCode>";          
        patientInfoXml += "<SampleNumber></SampleNumber>";              
        patientInfoXml += "<RecorderName></RecorderName>";             
        patientInfoXml += "<CreateDate>" + registrationtime + "</CreateDate>";     // 2013-09-03 11:00:58
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
   
        String patientInfo = "";
        //patientInfo = urlUtil.urlEncoding(patientInfoXml, "utf-8");
     	
	    String strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        strXml +=  "<Data>";
        strXml +=  "<systemCode>KMTC</systemCode>";
        strXml +=  "<hospCode>" + hospitalcode + "</hospCode>";             
        strXml +=  "<companyCode>" + companycode + "</companyCode>";       
        strXml +=  "<domainUserId>" + userId + "</domainUserId>";           
        strXml +=  "<applicationId>" + applicationId + "</applicationId>";  
        strXml +=  "<docId>" + docid + "</docId>";                          
        strXml +=  "<requestInfoXml>"+ patientInfo +"</requestInfoXml>";    // 病人样本信息按URLEncode进行编码
        strXml +=  "</Data>";
        
        //upload
    }

    private String getSexByCode(String sex) {
        if("1001".equals(sex)){
            return "女";
        }else if("1002".equals(sex)){
            return "男";
        }else if("1003".equals(sex)){
            return "不详";
        }else{
            return "";
        }
    }

    private String getAge(String borndate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

/**
 * Lincensed to Kingmed
 */
package com.kingmed.yuyt.util;

/**
 *
 * @author zhengjunjie
 */
public class Constants {
    public static final String SEX_F="女";
    public static final String SEX_M="男";
    public static final String SEX_U="不详";
    public static final String AGE_UNIT_YEAR="岁"; //年龄单位
    public static final String MSG_OUT ="O"; //消息外发
    public static final String MSG_IN ="I";//消息接收
    public static final String MSG_STATUS_NEW="N";//消息状态
    public static final String MSG_STATUS_SEND_SUCCESS="S";//消息已经发送成功
    public static final String ENABLE_YES="Y";// 状态Y
    
    public static final String BIZTYPE="1"; //状态字，用以区分双向对接模式上传的标本类型
    
    public static final String PRE_HOSPTITAL_CODE="H-";//医院代码前缀
    public static final String PRE_COMPANY_CODE="C-";//子公司代码前缀
    
    public static final String KMBARCODE_SPLITTER="-";//金域条码分隔符
    
    public static final String LIS_F_SID="201";//LIS 无效的SID    
    
    public static final String LIS_S="0";//LIS 上传标本信息成功
    
    public static final String LIS_EMPTY="211";//报告单未出
    
    public static final String LIS_ISREIMBU_Y="1";//LIS退单
    public static final String LIS_ISREIMBU_N="0";//LIS未退单
    
    public static final String F_EXCEPTION="E";//异常退出
    
    public static final String MSG_TYPE_UPLOAD_SPECIMEN="US";// 消息类型，上传标本
    public static final String MSG_TYPE_QUERY_REPORT="QR";// 消息类型，查询报告单
    public static final String MSG_TYPE_QUERY_REPORT_RESPONSE="QR_RES";// 消息类型，查询报告单
    public static final String MSG_TYPE_SEND_SMS="SMS";// 消息类型，上传标本
    public static final String MSG_TYPE_SEND_SMS_RESPONSE="SMS_RES";// 消息类型，上传标本
    
    public static final String APP_LIS="LIS";// 消息类型，查询报告单
    public static final String APP_YUYT="YUYT";// 消息类型，查询报告单
    public static final String APP_PANZHI="APP_PANZHI"; //磐志短消息供应商
    
    public static final String POSITIVE="POSITIVE";
    public static final String POSITIVE_Y="Y";
    public static final String POSITIVE_N="N";
    public static final String POSITIVE_X="X";//未知阳性、阴性
    
    public static final String DOC_ID="doc_id";
    
    public static final String LIS_XML_DECLARATION ="<?xml version=\"1.0\" encoding=\"gb2312\"?>";
    
    public static final String EXP_SPEC_DOC_ID="/response/spec_doc_id";//正在表达式，标本的文档ID
    
    public static final String EXP_DOC_ID="/response/doc_id";//正在表达式，文档ID
    
    public static final String EXP_SUB_COMPANY="/response/sub_company";//正则表达式，子公司代码
    
    public static final String EXP_HOSPITAL="/response/hospital";//正则表达式，医院代码
    
    public static final String EXP_KM_BARCODE="/response/km_barcode";//正则表达式，金域母码
    
    public static final String EXP_RESPONSE_IS_ISREIMBU="/response/IsReimbu";//是否退单的正则表达式
    
    public static final String EXP_IS_ISREIMBU="/Data/Data_Row/IsReimbu";//是否退单的正则表达式
    
    public static final String EXP_REPORT_STATUS="/response/report_status";//报告单的状态的正则表达式
    
    public static final String EXP_REPORT_DETAIL_STATUS="/response/report_detail_status";//报告单信息的状态的正则表达式
    
    public static final String EXP_REPORT_FILE="/response/report/Data/Data_Row/Report";//报告单的正则表达式
    
    public static final String EXP_REPORT_DETAIL="/response/reportDetail";//报告单详情的正则表达式
    
    
        
}

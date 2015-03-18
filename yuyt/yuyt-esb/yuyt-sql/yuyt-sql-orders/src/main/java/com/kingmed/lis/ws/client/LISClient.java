/**
 * Licensed to Kingmed
 */
package com.kingmed.lis.ws.client;

import com.kingmed.yuyt.cache.HospitalCache;
import com.kingmed.yuyt.model.Hospital;
import com.kingmed.yuyt.cache.SIDCache;
import com.kingmed.lis.ws.ILisProxy;
import java.rmi.RemoteException;
import javax.xml.rpc.holders.StringHolder;
import com.kingmed.yuyt.util.Constants;
import com.kingmed.yuyt.util.XMLHandler;
import java.util.List;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhengjunjie
 */
public class LISClient {

    private String companyCode;     //子公司代码
    private String companyName;
    private String ilisPortAddress;//子公司双向对接的LIS地址
    private HospitalCache hospitalCache;
    private ILisProxy iLis = null;
    private static final Logger logger = LoggerFactory.getLogger(LISClient.class);

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setIlisPortAddress(String ilisPortAddress) {
        this.ilisPortAddress = ilisPortAddress;
    }

    public void setHospitalCache(HospitalCache hospitalCache) {
        this.hospitalCache = hospitalCache;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getIlisPortAddress() {
        return ilisPortAddress;
    }

    public HospitalCache getHospitalCache() {
        return hospitalCache;
    }

    public void init() {
        iLis = new ILisProxy(this.ilisPortAddress);
    }

    public Object login(Object body) {
        logger.info(hospitalCache.get("01.1", "test").toString());
        StringHolder SID = new StringHolder();
        StringHolder _return = new StringHolder();

        ILisProxy p = new ILisProxy("http://192.168.4.46:60080/GZWSTEST/Lis.dll/soap/ilis");
        try {
            p.login("01.10.0010", "01100010", "", false, SID, _return);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }

        logger.info(SID.value + "," + _return.value);
        return SID.value + "," + _return.value;
    }

    /**
     *
     * @param hospCode
     * @param requestInfo
     * @return e，异常退出；0：成功上传；201：无效的SID
     */
    public String sendRequestInfo(String hospCode, String specimen) {
        logger.info("上传标本信息到LIS");
        String r = null;
        String sid = SIDCache.getInstance().get(companyCode, hospCode);
        Hospital hospital = hospitalCache.get(this.companyCode, hospCode);
        if (sid == null) {
            this.login(hospital);
            sid = SIDCache.getInstance().get(companyCode, hospCode);
            if (sid == null) {
                logger.error("SID为空，登录但是无法获取LIS的SID" + ",companyCode" + companyCode + "hospCode=" + hospCode);
                return Constants.F_EXCEPTION;
            }
        }

        String resultMsg;
        String head = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        specimen = head + specimen;
        try {
            resultMsg = iLis.sendRequestInfo(sid, specimen);
            logger.info("上传标本信息结果" + resultMsg);
        } catch (RemoteException e) {
            logger.error("上传标本信息失败" + ",地址" + ilisPortAddress + "报文=" + specimen + ",", e);
            return Constants.F_EXCEPTION;
        }

        if (resultMsg.contains(Constants.LIS_F_SID)) {// 发送失败，无效的SID，重试登录之后上传一次
            //登录
            this.login(hospital);

            sid = SIDCache.getInstance().get(companyCode, hospCode);
            if (sid == null) {
                logger.error("会话失效，重登录但是无法获取LIS的SID" + ",companyCode" + companyCode + "hospCode=" + hospCode);
                return Constants.F_EXCEPTION;
            }
            try {
                resultMsg = iLis.sendRequestInfo(sid, specimen);
                logger.info("重登录后，上传标本信息结果" + resultMsg);
            } catch (RemoteException e) {
                logger.error("上传标本信息失败", e);
                return Constants.F_EXCEPTION;
            }
            if (resultMsg.contains(Constants.LIS_F_SID)) {// 发送失败，无效的SID
                logger.info("SID无效，返回");
                return Constants.LIS_F_SID;
            }
        }

        if (Constants.LIS_S.equals(resultMsg)) {
            r = Constants.LIS_S;
            logger.info("上传标本成功");
        } else {
            logger.info("上传标本失败");
        }
        return r;
    }

    public String sendRequest4QueryReport(String hospCode, String kmbarcode) throws Exception {
        logger.info("查询报告单");
        String re = null;
        String sid = SIDCache.getInstance().get(companyCode, hospCode);
        Hospital hospital = hospitalCache.get(this.companyCode, hospCode);
        if (sid == null) {
            sid = this.login(hospital);
            this.validateSID(sid, hospCode);
        }

        StringHolder resultInfo = new StringHolder();
        StringHolder _return = new StringHolder();
        try {
            iLis.queryReport(sid, kmbarcode, resultInfo, _return);
        } catch (RemoteException e) {
            String msg = "查询报告单失败" + ",地址" + ilisPortAddress + "报文=" + kmbarcode + ",";
            logger.error(msg, e);
            throw new Exception(msg);
        }
        String rv = _return.value;
        if (Constants.LIS_F_SID.equals(rv)) {// 发送失败，无效的SID，重试登录之后上传一次
            sid = this.login(hospital);
            this.validateSID(sid, hospCode);
            iLis.queryReport(sid, kmbarcode, resultInfo, _return);
        }

        rv = _return.value;
        if (Constants.LIS_S.equals(rv)) {               //已经查询到报告单
            StringBuilder sb = new StringBuilder();
            sb.append("<lis_status>").append(rv).append("</lis_status>")
              .append(resultInfo.value);
            re = sb.toString();
        } else if (rv.contains(Constants.LIS_EMPTY)) {    //表示查询结果为空，报告单不存在或者实验室退单
            StringBuilder sb = new StringBuilder();
            sb.append("<lis_status>").append(rv).append("</lis_status>");
            
            iLis.queryRequestDetail(sid, kmbarcode, resultInfo, _return);//检测是否退单
            rv = _return.value;
            sb.append("<lis_status1>").append(rv).append("</lis_status1>");
            if (Constants.LIS_S.equals(rv)) {  //实验室已退单
                List<Node> nodes = XMLHandler.query(resultInfo.value, "/Data/Data_Row[IsReimbu=1]");
                if(nodes != null && nodes.size()>0){
                    sb.append("<IsReimbu>").append(Constants.LIS_ISREIMBU_Y).append("</IsReimbu>");
                }else{
                    sb.append("<IsReimbu>").append(Constants.LIS_ISREIMBU_N).append("</IsReimbu>");
                }
            }
            re = sb.toString();
        }else {                                         //未知返回代码
            StringBuilder sb = new StringBuilder();
            sb.append("<lis_status>").append(rv).append("</lis_status>")
              .append(resultInfo.value);
            re = sb.toString();
        }
        return re;
    }

    public String login(Hospital hospital) {
        String hospCode = hospital.getCode();
        String username = hospital.getLisUsername();
        String password = hospital.getLisPassword();
        String operator = "";
        boolean isInterface = false;
        StringHolder SID = new StringHolder();
        StringHolder _return = new StringHolder();
        try {
            iLis.login(username, password, operator, isInterface, SID, _return);
        } catch (RemoteException e) {
            logger.error("登录LIS失败,用户名" + username + ",地址" + ilisPortAddress, e);
            return null;
        }
        if (_return.value.equals("0")) {//登录成功
            String sid = SID.value;
            SIDCache.getInstance().set(companyCode, hospCode, sid);
            return sid;
        } else {
            logger.error("登录LIS失败,用户名" + username + ",地址" + ilisPortAddress + ",return " + _return.value + ",SID" + SID.value);
            return null;
        }
    }

    private void validateSID(String sid, String hospCode) throws Exception {
        if (sid == null) {
            String msg = "SID为空，登录但是无法获取LIS的SID" + ",companyCode" + companyCode + "hospCode=" + hospCode;
            logger.error(msg);
            throw new Exception(msg);
        }
    }
}

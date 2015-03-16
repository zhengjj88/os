/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.bean;

import com.kingmed.yuyt.model.SMSResponseVO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhengjunjie
 */
public class PanzhiBean {
    private static final Logger logger = LoggerFactory.getLogger(PanzhiBean.class);  
    private String orgId ="175";
    private String username ="jinyu8";
    private String password ="5ecdb84f";
    private String serverUrl ="http://api-dx.panzhi.net/http1.php";
    
    private Map<String,String> stateMap = new HashMap<String,String>();
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void init(){
        stateMap.put("0", "成功"); 
		stateMap.put("100", "定时成功");
		stateMap.put("-1","发送失败");//
		stateMap.put("-2", "帐号错误");
		stateMap.put("-3", "密码错误");
		stateMap.put("-4","机构错误");
		stateMap.put("-5","内容不合法");
		stateMap.put("-6","号码不合法");
		stateMap.put("-7","用户被临时禁用");
		stateMap.put("-8","余额不足");
		stateMap.put("-9","网路异常");//
		stateMap.put("-10","非接口用户");
		stateMap.put("-11","认证key错误");
		stateMap.put("-12","SP发送超时");//
		stateMap.put("-15","帐号被禁止");
		stateMap.put("-18","短信内容后面没有带签名");
		stateMap.put("-19","短信内容过长");
		stateMap.put("-20","无可用的通道");//
		stateMap.put("-30","更新批号错误");//
		stateMap.put("-31","号码中包含拒绝发送的号码");
		stateMap.put("-32","定时参数不合法");
		stateMap.put("-100","非法调用");
    }
    public static void main(String[] args) {
        Map paramMap = new HashMap();
        paramMap.put("destnumbers", "13611415083");
        paramMap.put("msgContent", "尊敬的赵xx，您于2015-02-26在[广州医院A]做的[液基细胞学检测－宫颈（两癌）]检测报告结果为阳性，请在一周内到[广州医院A]进行进一步复查以排除病变可能。有疑问欢迎致电[020-8888888]。");
        paramMap.put("smsSignature", "【广州医院A】");
        new PanzhiBean().sendMsgByPost(paramMap);
    }
    public SMSResponseVO sendMsgByPost(Map paramMap) {
        SMSResponseVO vo  = null;
        String docId = paramMap.get("docid") != null ? paramMap.get("docid").toString() : null;
        String destnumbers = paramMap.get("destnumbers") != null ? paramMap.get("destnumbers").toString() : null;
        String msg = paramMap.get("msgContent") != null ? paramMap.get("msgContent").toString() : null;
        String smsSignature = paramMap.get("smsSignature") != null ? paramMap.get("smsSignature").toString() : null;
        String sendParam = "act=send&orgid=" + orgId + "&username=" + username + "&passwd=" + password + "&msg=" + msg + smsSignature + "&destnumbers=" + destnumbers;
        String result=null;
        try {
            result = sendByPost(sendParam);
        } catch (IOException ex) {
            logger.error("发送短信失败",ex);
        }
        if(result !=null )
            vo =  transferReturnMsg(docId,result);
        return vo;
    }

    private String sendByPost(String sendParam) throws IOException {
        URL url = new URL(new String(serverUrl.getBytes(), "UTF-8"));
        HttpURLConnection httpUrlCon = null;
        BufferedReader reader =null;
        String r=null;
        try {
            httpUrlCon = (HttpURLConnection) url.openConnection();
            httpUrlCon.setDoOutput(true);
            httpUrlCon.setRequestMethod("POST");
            httpUrlCon.getOutputStream().write(sendParam.getBytes("UTF-8"));
            httpUrlCon.getOutputStream().flush();
            httpUrlCon.getOutputStream().close();
            reader = new BufferedReader(new InputStreamReader(httpUrlCon.getInputStream()));
            r = reader.readLine();
        } finally {
            if(reader !=null ){
                reader.close();
            }
            if(httpUrlCon !=null ){
                httpUrlCon.disconnect();
            }
        }
        return r;
    }
    
    private SMSResponseVO transferReturnMsg(String docId,String returnMsg) {
        SMSResponseVO  vo = new SMSResponseVO();
		vo.setDocId(docId);
		Boolean result = true;
		String errorMsg = null;
		String[] msgs = returnMsg.split("&");
		HashMap maps = new HashMap();
        for(String msg : msgs){
        	if(msg!=null&&msg.contains("=")){
        		String[] temp = msg.split("=");
        		String key = temp[0];
        		String value = temp.length>1?temp[1]:"";
        		maps.put(key, value);
        	}
        }
        Date curDate = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        if(maps.containsKey("state")){
        	String state = maps.get("state").toString();
        	String reason = stateMap.get(state)!=null?stateMap.get(state).toString():null;
        	String smsGroupID = null;
        	if(maps.containsKey("smsGroupID")){
            	smsGroupID = maps.get("smsGroupID")!=null?maps.get("smsGroupID").toString():null;
            }
        	vo.setState(state);
        	vo.setStateRemark(reason);
        	vo.setSmsGroupID(smsGroupID);
        	if(state==null||!"0".equals(state)){//发送不成功
        		result = false;
        		errorMsg = format.format(curDate)+":短信("+docId+")发送失败，失败原因为："+reason;
        	}
        }
        if(!result){//如果发送失败，则将失败写入log
        	logger.error(errorMsg);
        }
        vo.setIsValid(result);
        vo.setResultMsg(errorMsg);
        return vo;
        
	}

}

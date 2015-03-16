/**
 * Licensed to Kingmed
 */

package com.kingmed.yuyt.model;

public class SMSResponseVO {
	/**消息的唯一性编号*/
	private String docId;
	/**结果*/
	private String resultMsg;
	/**操作是否成功*/
	private Boolean isValid;
	/**服务器分配的短信批号*/
	private String smsGroupID;
	/**错误代码*/
	private String state;
	/**错误提示*/
	private String stateRemark;
	
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	public String getSmsGroupID() {
		return smsGroupID;
	}
	public void setSmsGroupID(String smsGroupID) {
		this.smsGroupID = smsGroupID;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	
}

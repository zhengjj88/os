/**
 * ILis.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.kingmed.lis.ws;

public interface ILis extends java.rmi.Remote {
    public java.lang.String test() throws java.rmi.RemoteException;
    public void login(java.lang.String userName, java.lang.String password, java.lang.String operator, boolean isInterface, javax.xml.rpc.holders.StringHolder SID, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public java.lang.String sendRequestInfo(java.lang.String SID, java.lang.String requestInfo) throws java.rmi.RemoteException;
    public java.lang.String sendRequestInfoA(java.lang.String SID, java.lang.String requestInfo) throws java.rmi.RemoteException;
    public void queryResult(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryResultA(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryResult2(java.lang.String SID, java.lang.String hospSampleID, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryResult2A(java.lang.String SID, java.lang.String hospSampleID, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryReport(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryReport2(java.lang.String SID, java.lang.String hospSampleID, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryReport3(java.lang.String SID, java.lang.String queueID, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryFP3Report(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryFP3ReportM(java.lang.String interfaceName, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryFP3Report3(java.lang.String SID, java.lang.String queueID, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryReportByDate(java.lang.String SID, java.util.Calendar reportDate, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryRequestByDate(java.lang.String SID, java.util.Calendar reportDate, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void getRequestInfo(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void getRequestInfo2(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public java.lang.String sendResult2(java.lang.String SID, java.lang.String requestCode, java.lang.String resultInfo) throws java.rmi.RemoteException;
    public java.lang.String sendResult3(java.lang.String SID, java.lang.String requestCode, java.lang.String resultInfo) throws java.rmi.RemoteException;
    public java.lang.String sendResult(java.lang.String SID, java.lang.String requestCode, java.lang.String resultInfo) throws java.rmi.RemoteException;
    public java.lang.String logout(java.lang.String SID) throws java.rmi.RemoteException;
    public void queryReportBetween(java.lang.String SID, java.util.Calendar fromDate, java.util.Calendar toDate, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryRequestBetween(java.lang.String SID, java.util.Calendar fromDate, java.util.Calendar toDate, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryRequestBetweenA(java.lang.String SID, java.util.Calendar fromDate, java.util.Calendar toDate, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryRequestBetweenB(java.lang.String SID, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryRequestBetweenB2(java.lang.String SID, boolean isComm, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryRequestBetweenB3(java.lang.String SID, boolean isComm, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryRequestBetweenB4(java.lang.String SID, boolean isComm, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryRequestBetweenB4M(java.lang.String interfaceName, boolean isComm, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryRequestDetail(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryRequestDetailA(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryRequestDetail3(java.lang.String SID, java.lang.String queueID, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryRequestDetail4(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void queryRequestDetail4M(java.lang.String interfaceName, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void getItemInfo(java.lang.String SID, javax.xml.rpc.holders.StringHolder itemInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public java.lang.String sendOutSourceOrder(java.lang.String SID, java.lang.String outSourceCode, int sampleNumber, java.lang.String recorderName, java.util.Calendar createDate, java.lang.String remark, java.lang.String orderInfo) throws java.rmi.RemoteException;
    public java.lang.String sendOutSourceOrderA(java.lang.String SID, java.lang.String outSourceCode, int sampleNumber, java.lang.String recorderName, java.util.Calendar createDate, java.lang.String remark, java.lang.String orderInfo) throws java.rmi.RemoteException;
    public void getPrecativeRequest(java.lang.String SID, java.lang.String itemListID, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void getPrecativeRequestJL(java.lang.String SID, java.lang.String itemListID, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void getPrecativeRequestGYSY(java.lang.String SID, java.lang.String itemListID, java.lang.String itemCode, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void getSignature(java.lang.String SID, java.lang.String name, javax.xml.rpc.holders.StringHolder signatureInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void checkHospInfo(java.lang.String AHospCode, java.lang.String APassword, javax.xml.rpc.holders.StringHolder AHospID, javax.xml.rpc.holders.StringHolder AHospName, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void checkLirUser(java.lang.String AUserCode, java.lang.String APassword, javax.xml.rpc.holders.StringHolder AUserName, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException;
    public void affirmReceived(java.lang.String AQueueID, boolean downloaded) throws java.rmi.RemoteException;
    public java.lang.String sendLog(java.lang.String SID, java.lang.String opType, java.util.Calendar opTime, java.lang.String opContent) throws java.rmi.RemoteException;
}

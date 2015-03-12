package com.kingmed.lis.ws;

public class ILisProxy implements com.kingmed.lis.ws.ILis {
  private String _endpoint = null;
  private com.kingmed.lis.ws.ILis iLis = null;
  
  public ILisProxy() {
    _initILisProxy();
  }
  
  public ILisProxy(String endpoint) {
    _endpoint = endpoint;
    _initILisProxy();
  }
  
  private void _initILisProxy() {
    try {
      iLis = (new com.kingmed.lis.ws.ILisserviceLocator()).getilisPort();
      if (iLis != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iLis)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iLis)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iLis != null)
      ((javax.xml.rpc.Stub)iLis)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.kingmed.lis.ws.ILis getILis() {
    if (iLis == null)
      _initILisProxy();
    return iLis;
  }
  
  public java.lang.String test() throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    return iLis.test();
  }
  
  public void login(java.lang.String userName, java.lang.String password, java.lang.String operator, boolean isInterface, javax.xml.rpc.holders.StringHolder SID, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.login(userName, password, operator, isInterface, SID, _return);
  }
  
  public java.lang.String sendRequestInfo(java.lang.String SID, java.lang.String requestInfo) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    return iLis.sendRequestInfo(SID, requestInfo);
  }
  
  public java.lang.String sendRequestInfoA(java.lang.String SID, java.lang.String requestInfo) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    return iLis.sendRequestInfoA(SID, requestInfo);
  }
  
  public void queryResult(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryResult(SID, requestCode, resultInfo, _return);
  }
  
  public void queryResultA(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryResultA(SID, requestCode, resultInfo, _return);
  }
  
  public void queryResult2(java.lang.String SID, java.lang.String hospSampleID, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryResult2(SID, hospSampleID, resultInfo, _return);
  }
  
  public void queryResult2A(java.lang.String SID, java.lang.String hospSampleID, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryResult2A(SID, hospSampleID, resultInfo, _return);
  }
  
  public void queryReport(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryReport(SID, requestCode, resultInfo, _return);
  }
  
  public void queryReport2(java.lang.String SID, java.lang.String hospSampleID, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryReport2(SID, hospSampleID, resultInfo, _return);
  }
  
  public void queryReport3(java.lang.String SID, java.lang.String queueID, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryReport3(SID, queueID, resultInfo, _return);
  }
  
  public void queryFP3Report(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryFP3Report(SID, requestCode, resultInfo, _return);
  }
  
  public void queryFP3ReportM(java.lang.String interfaceName, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryFP3ReportM(interfaceName, requestCode, resultInfo, _return);
  }
  
  public void queryFP3Report3(java.lang.String SID, java.lang.String queueID, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryFP3Report3(SID, queueID, resultInfo, _return);
  }
  
  public void queryReportByDate(java.lang.String SID, java.util.Calendar reportDate, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryReportByDate(SID, reportDate, resultInfo, _return);
  }
  
  public void queryRequestByDate(java.lang.String SID, java.util.Calendar reportDate, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryRequestByDate(SID, reportDate, requestInfo, _return);
  }
  
  public void getRequestInfo(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.getRequestInfo(SID, requestCode, requestInfo, _return);
  }
  
  public void getRequestInfo2(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.getRequestInfo2(SID, requestCode, requestInfo, _return);
  }
  
  public java.lang.String sendResult2(java.lang.String SID, java.lang.String requestCode, java.lang.String resultInfo) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    return iLis.sendResult2(SID, requestCode, resultInfo);
  }
  
  public java.lang.String sendResult3(java.lang.String SID, java.lang.String requestCode, java.lang.String resultInfo) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    return iLis.sendResult3(SID, requestCode, resultInfo);
  }
  
  public java.lang.String sendResult(java.lang.String SID, java.lang.String requestCode, java.lang.String resultInfo) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    return iLis.sendResult(SID, requestCode, resultInfo);
  }
  
  public java.lang.String logout(java.lang.String SID) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    return iLis.logout(SID);
  }
  
  public void queryReportBetween(java.lang.String SID, java.util.Calendar fromDate, java.util.Calendar toDate, javax.xml.rpc.holders.StringHolder resultInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryReportBetween(SID, fromDate, toDate, resultInfo, _return);
  }
  
  public void queryRequestBetween(java.lang.String SID, java.util.Calendar fromDate, java.util.Calendar toDate, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryRequestBetween(SID, fromDate, toDate, requestInfo, _return);
  }
  
  public void queryRequestBetweenA(java.lang.String SID, java.util.Calendar fromDate, java.util.Calendar toDate, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryRequestBetweenA(SID, fromDate, toDate, requestInfo, _return);
  }
  
  public void queryRequestBetweenB(java.lang.String SID, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryRequestBetweenB(SID, requestInfo, _return);
  }
  
  public void queryRequestBetweenB2(java.lang.String SID, boolean isComm, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryRequestBetweenB2(SID, isComm, requestInfo, _return);
  }
  
  public void queryRequestBetweenB3(java.lang.String SID, boolean isComm, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryRequestBetweenB3(SID, isComm, requestInfo, _return);
  }
  
  public void queryRequestBetweenB4(java.lang.String SID, boolean isComm, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryRequestBetweenB4(SID, isComm, requestInfo, _return);
  }
  
  public void queryRequestBetweenB4M(java.lang.String interfaceName, boolean isComm, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryRequestBetweenB4M(interfaceName, isComm, requestInfo, _return);
  }
  
  public void queryRequestDetail(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryRequestDetail(SID, requestCode, requestInfo, _return);
  }
  
  public void queryRequestDetailA(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryRequestDetailA(SID, requestCode, requestInfo, _return);
  }
  
  public void queryRequestDetail3(java.lang.String SID, java.lang.String queueID, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryRequestDetail3(SID, queueID, requestInfo, _return);
  }
  
  public void queryRequestDetail4(java.lang.String SID, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryRequestDetail4(SID, requestCode, requestInfo, _return);
  }
  
  public void queryRequestDetail4M(java.lang.String interfaceName, java.lang.String requestCode, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.queryRequestDetail4M(interfaceName, requestCode, requestInfo, _return);
  }
  
  public void getItemInfo(java.lang.String SID, javax.xml.rpc.holders.StringHolder itemInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.getItemInfo(SID, itemInfo, _return);
  }
  
  public java.lang.String sendOutSourceOrder(java.lang.String SID, java.lang.String outSourceCode, int sampleNumber, java.lang.String recorderName, java.util.Calendar createDate, java.lang.String remark, java.lang.String orderInfo) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    return iLis.sendOutSourceOrder(SID, outSourceCode, sampleNumber, recorderName, createDate, remark, orderInfo);
  }
  
  public java.lang.String sendOutSourceOrderA(java.lang.String SID, java.lang.String outSourceCode, int sampleNumber, java.lang.String recorderName, java.util.Calendar createDate, java.lang.String remark, java.lang.String orderInfo) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    return iLis.sendOutSourceOrderA(SID, outSourceCode, sampleNumber, recorderName, createDate, remark, orderInfo);
  }
  
  public void getPrecativeRequest(java.lang.String SID, java.lang.String itemListID, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.getPrecativeRequest(SID, itemListID, requestInfo, _return);
  }
  
  public void getPrecativeRequestJL(java.lang.String SID, java.lang.String itemListID, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.getPrecativeRequestJL(SID, itemListID, requestInfo, _return);
  }
  
  public void getPrecativeRequestGYSY(java.lang.String SID, java.lang.String itemListID, java.lang.String itemCode, javax.xml.rpc.holders.StringHolder requestInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.getPrecativeRequestGYSY(SID, itemListID, itemCode, requestInfo, _return);
  }
  
  public void getSignature(java.lang.String SID, java.lang.String name, javax.xml.rpc.holders.StringHolder signatureInfo, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.getSignature(SID, name, signatureInfo, _return);
  }
  
  public void checkHospInfo(java.lang.String AHospCode, java.lang.String APassword, javax.xml.rpc.holders.StringHolder AHospID, javax.xml.rpc.holders.StringHolder AHospName, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.checkHospInfo(AHospCode, APassword, AHospID, AHospName, _return);
  }
  
  public void checkLirUser(java.lang.String AUserCode, java.lang.String APassword, javax.xml.rpc.holders.StringHolder AUserName, javax.xml.rpc.holders.StringHolder _return) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.checkLirUser(AUserCode, APassword, AUserName, _return);
  }
  
  public void affirmReceived(java.lang.String AQueueID, boolean downloaded) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    iLis.affirmReceived(AQueueID, downloaded);
  }
  
  public java.lang.String sendLog(java.lang.String SID, java.lang.String opType, java.util.Calendar opTime, java.lang.String opContent) throws java.rmi.RemoteException{
    if (iLis == null)
      _initILisProxy();
    return iLis.sendLog(SID, opType, opTime, opContent);
  }
  
  
}
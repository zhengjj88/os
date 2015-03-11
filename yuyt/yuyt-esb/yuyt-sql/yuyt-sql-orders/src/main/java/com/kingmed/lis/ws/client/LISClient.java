/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.lis.ws.client;

import com.kingmed.lis.ws.ILisProxy;
import java.rmi.RemoteException;
import java.util.logging.Logger;
import javax.xml.rpc.holders.StringHolder;

/**
 *
 * @author zhengjunjie
 */
public class LISClient {
    private String ilisPortAddress;
    private String username;
    private String password;
    private static final transient Logger logger = Logger.getLogger(LISClient.class.getName());
    private boolean verbose = true;
    private String prefix = "LIS";
    ILisProxy iLis = null;
    
    private StringHolder SID = new StringHolder();
    public void setIlisPortAddress(String ilisPortAddress) {
        this.ilisPortAddress = ilisPortAddress;
    }
    
    public void init(){
        iLis = new ILisProxy(this.ilisPortAddress);
    }
    
    public Object login(Object body) {
        StringHolder SID = new StringHolder();
        StringHolder _return = new StringHolder();

        ILisProxy p = new ILisProxy("http://192.168.4.46:60080/GZWSTEST/Lis.dll/soap/ilis");
        try {
            p.login("01.10.0010", "01100010", "", false, SID, _return);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }

        System.out.println(SID.value);
        System.out.println(_return.value);
        logger.info(SID.value + "," + _return.value);
        return SID.value + "," + _return.value;
    }

    public String sendRequestInfo(String requestInfo){
        logger.info("上传标本信息到LIS");
        //String r = iLis.sendRequestInfo(SID, requestInfo);
        return null;
    }
}

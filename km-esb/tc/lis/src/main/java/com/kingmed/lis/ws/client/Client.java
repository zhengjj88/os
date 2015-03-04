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
public class Client {
    private static final transient Logger logger = Logger.getLogger(Client.class.getName());
    private boolean verbose = true;
    private String prefix = "LIS";

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
        logger.info(SID.value+","+_return.value);
        return SID.value+","+_return.value;
    }
}

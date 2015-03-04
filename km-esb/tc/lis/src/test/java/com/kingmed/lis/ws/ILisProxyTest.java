/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.lis.ws;

import java.rmi.RemoteException;
import javax.xml.rpc.holders.StringHolder;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author zhengjunjie
 */
public class ILisProxyTest {
    
    public ILisProxyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testLogin(){
        StringHolder SID = new StringHolder();
		StringHolder _return = new StringHolder();
		
		ILisProxy p = new ILisProxy("http://192.168.4.46:60080/GZWSTEST/Lis.dll/soap/ilis");
        try {
            p.login("01.10.0010", "01100010", "", false, SID, _return);
        } catch (RemoteException ex) {
            fail("fail");
        }

		System.out.println(SID.value);
		System.out.println(_return.value);
    }
}

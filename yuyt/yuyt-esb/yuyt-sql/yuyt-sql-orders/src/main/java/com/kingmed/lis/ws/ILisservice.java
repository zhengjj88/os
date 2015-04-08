/**
 * ILisservice.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.kingmed.lis.ws;

public interface ILisservice extends javax.xml.rpc.Service {
    public java.lang.String getILisPortAddress();

    public com.kingmed.lis.ws.ILis getILisPort() throws javax.xml.rpc.ServiceException;

    public com.kingmed.lis.ws.ILis getILisPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}

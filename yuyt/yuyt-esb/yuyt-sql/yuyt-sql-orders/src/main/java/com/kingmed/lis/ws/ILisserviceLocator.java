/**
 * ILisserviceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.kingmed.lis.ws;

public class ILisserviceLocator extends org.apache.axis.client.Service implements com.kingmed.lis.ws.ILisservice {

    public ILisserviceLocator() {
    }


    public ILisserviceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ILisserviceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ILisPort
    private java.lang.String ILisPort_address = "http://192.168.4.46:60080/GZWS/Lis.dll/soap/ILis";

    public java.lang.String getILisPortAddress() {
        return ILisPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ILisPortWSDDServiceName = "ILisPort";

    public java.lang.String getILisPortWSDDServiceName() {
        return ILisPortWSDDServiceName;
    }

    public void setILisPortWSDDServiceName(java.lang.String name) {
        ILisPortWSDDServiceName = name;
    }

    public com.kingmed.lis.ws.ILis getILisPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ILisPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getILisPort(endpoint);
    }

    public com.kingmed.lis.ws.ILis getILisPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.kingmed.lis.ws.ILisbindingStub _stub = new com.kingmed.lis.ws.ILisbindingStub(portAddress, this);
            _stub.setPortName(getILisPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setILisPortEndpointAddress(java.lang.String address) {
        ILisPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.kingmed.lis.ws.ILis.class.isAssignableFrom(serviceEndpointInterface)) {
                com.kingmed.lis.ws.ILisbindingStub _stub = new com.kingmed.lis.ws.ILisbindingStub(new java.net.URL(ILisPort_address), this);
                _stub.setPortName(getILisPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ILisPort".equals(inputPortName)) {
            return getILisPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.60.1.193/", "ILisservice");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://10.60.1.193/", "ILisPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ILisPort".equals(portName)) {
            setILisPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

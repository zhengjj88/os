#coding=utf8

import httplib,urllib
from xml.etree import ElementTree
import  xml.dom.minidom
       
httpClient = None
try:
    headers={"Content-Type":"text/xml; charset=utf-8","SOAPAction":"http://www.aperio.com/webservices/#Logon"}
    data="<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns=\"http://www.aperio.com/webservices/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"> <SOAP-ENV:Body> <Logon><Token>8CpME1VuPWyGocDbgz9D0YOtp01p58knlWcl_rACnjlL6yOJ4LeneQ==</Token><LoginName>pt-user1</LoginName><Password>20587</Password></Logon></SOAP-ENV:Body> </SOAP-ENV:Envelope>";
    httpClient = httplib.HTTPConnection("192.168.0.37",86,timeout=30)
    httpClient.request("POST",'/Aperio.Security/Security2.asmx',data,headers)
    
    response = httpClient.getresponse()
    #print response.status
    #print response.reason
    responseXML = response.read()
    print responseXML
    #print response.getheaders() #获取头信息
except Exception, e:
    print e
finally:
    if httpClient:
        httpClient.close()




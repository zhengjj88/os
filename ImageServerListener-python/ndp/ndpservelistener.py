#coding=utf8

import httplib

httpClient = None

try:
    params = 'nspConnect&signin=Sign%20in&Username=&Password='
    httpClient = httplib.HTTPConnection('www.kingmed.com.cn',81)
    httpClient.request('GET','/NDPServe.dll',params)
    response = httpClient.getresponse()
    print response.status
    print response.reason
    print response.read()
except Exception, e:
    print e
finally:
    if httpClient:
        httpClient.close()

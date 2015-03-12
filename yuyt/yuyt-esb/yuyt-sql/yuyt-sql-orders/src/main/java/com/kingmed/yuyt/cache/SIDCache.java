/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.cache;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zhengjunjie
 */
public class SIDCache {
    private Map<String,String> cache= new HashMap<String,String>();
    private static final SIDCache INSTANCE = new SIDCache();
    private SIDCache(){};
    
    public static final SIDCache getInstance(){
        return INSTANCE;
    }
    
    public synchronized void set(String companyCode, String hospCode,String SID){
        String key = companyCode+"-"+hospCode;
        String v = SID;
        cache.put(key, v);
    }
    
    public synchronized String get(String companyCode, String hospCode){
        String key = companyCode+"-"+hospCode;
        return cache.get(key);
    }
    
}

/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.cache;

import com.kingmed.yuyt.model.Hospital;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zhengjunjie
 */
public class HospitalCache {
    private Map<String,Hospital> cache = new HashMap<String,Hospital>();

    public Hospital get(String companyCode,String hospCode) {
        String key = companyCode+"-"+hospCode;
        return cache.get(key);
    }

    public void setCache(Map<String, Hospital> cache) {
        this.cache = cache;
    }
}

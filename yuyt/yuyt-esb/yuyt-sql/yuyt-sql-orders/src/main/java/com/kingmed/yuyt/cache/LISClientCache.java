/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.cache;

import com.kingmed.lis.ws.client.LISClient;
import com.kingmed.yuyt.model.LISNode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zhengjunjie
 */
public class LISClientCache {

    private LISNodeCache lisNodeCache;
    private HospitalCache hospitalCache;
    private Map<String, LISClient> lisClients = new HashMap<String, LISClient>();

    public LISNodeCache getLisNodeCache() {
        return lisNodeCache;
    }

    public void setLisNodeCache(LISNodeCache lisNodeCache) {
        this.lisNodeCache = lisNodeCache;
    }

    public HospitalCache getHospitalCache() {
        return hospitalCache;
    }

    public void setHospitalCache(HospitalCache hospitalCache) {
        this.hospitalCache = hospitalCache;
    }

    public void init(){
        //初始化客户端
        Collection<LISNode> lisNodes = this.lisNodeCache.getAll();
        for(LISNode lisNode:lisNodes){
            LISClient lisClient = new LISClient();
            lisClient.setCompanyCode(lisNode.getComCode());
            lisClient.setCompanyName(lisNode.getComName());
            lisClient.setIlisPortAddress(lisNode.getAddress());
            lisClient.setHospitalCache(hospitalCache);
            lisClient.init();
            lisClients.put(lisNode.getComCode(), lisClient);
        }
    }
    public LISClient get(String comCode){
        return this.lisClients.get(comCode);
    }
}

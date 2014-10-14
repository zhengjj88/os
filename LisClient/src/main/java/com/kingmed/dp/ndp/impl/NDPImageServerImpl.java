/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.impl;

import com.kingmed.dp.ndp.NDPServe;

/**
 *
 * @author zhengjunjie
 */
public class NDPImageServerImpl implements NDPServe {

    private String username;
    private String password;
    private String protocl = "http";
    private String host;
    private int port = 80;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getProtocl() {
        return protocl;
    }

    @Override
    public void setProtocl(String protocl) {
        this.protocl = protocl;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    private String getWebSiteAddress() {
        return protocl + "://" + host + ":" + port + "/" + "NDPServe.dll";
    }

    /**
     * 登录的url
     *
     * @return
     */
    @Override
    public String getUrlSignin() {
        String u = "nspConnect" + "&" + getParameters4Authz();
        return getCompleteUrl(u);
    }

    @Override
    public String getUrlSignout() {
        String u = "nspConnect&signin=Sign%20out";
        return getCompleteUrl(u);
    }

    /**
     * 返回带身份认证的URL
     *
     * @param itemId
     * @return
     */
    @Override
    public String getUrlItemsAddedAfterWithAuthz(Long itemId) {
        String u = "nspGetItemsAddedAfter&ItemID=" + itemId + "&" + getParameters4Authz();
        return getCompleteUrl(u);
    }

    @Override
    public String getUrlItemsAddedAfter(Long itemId) {
        String u = "nspGetItemsAddedAfter&ItemID=" + itemId;
        return getCompleteUrl(u);
    }

    @Override
    public String toString() {
        return "NDPServe{" + "username=" + username + ",password=********" + ", protocl=" + protocl + ", host=" + host + ", port=" + port + '}';
    }

    @Override
    public String getInfo() {
        return toString();
    }

    private String getCompleteUrl(String pathAndParameters) {
        return getWebSiteAddress() + "?" + pathAndParameters;
    }

    /**
     * 认证的url参数
     *
     * @return
     */
    private String getParameters4Authz() {
        return "signin=Sign%20in&Username=" + getUsername() + "&Password=" + getPassword();
    }

    @Override
    public String getUrlForItemInfo(Long itemId, int update) {
        return "nspGetItemInfo?ItemID="+itemId+"&Update="+update;
    }

    @Override
    public String getUrlForUpdateLinkedFolders(Long itemId) {
        return "nspUpdateLinkedFolders?ItemID="+itemId;
    }
}

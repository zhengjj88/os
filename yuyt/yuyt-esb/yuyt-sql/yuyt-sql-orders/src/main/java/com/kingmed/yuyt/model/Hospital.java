/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.model;

/**
 * 金域的客户，医院
 * @author zhengjunjie
 */
public class Hospital {
    private String companyCode;//子公司代码
    private String code;//医院代码
    private String name;//医院名称
    private String lisUsername;//双向对接的LIS用户名
    private String lisPassword;//双向对接的LIS密码

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLisUsername() {
        return lisUsername;
    }

    public void setLisUsername(String lisUsername) {
        this.lisUsername = lisUsername;
    }

    public String getLisPassword() {
        return lisPassword;
    }

    public void setLisPassword(String lisPassword) {
        this.lisPassword = lisPassword;
    }
    
    
}

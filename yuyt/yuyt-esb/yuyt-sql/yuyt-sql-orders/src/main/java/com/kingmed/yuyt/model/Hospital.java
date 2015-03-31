/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.model;

import java.sql.Timestamp;

/**
 * 金域的客户，医院
 *
 * @author zhengjunjie
 */
public class Hospital {
    private Long id;
    private String enable;
    private String companyCode;//子公司代码
    private String comName;//子公司名称
    private String code;//医院代码
    private String name;//医院名称
    private String lisUsername;//双向对接的LIS用户名
    private String lisPassword;//双向对接的LIS密码
    private Timestamp createTimestamp;
    private String createUser;
    private Timestamp updateTimestamp;
    private String updateUser;

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

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Timestamp updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

}

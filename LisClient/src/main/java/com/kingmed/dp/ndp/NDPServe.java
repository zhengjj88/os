/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp;

import com.kingmed.dp.connection.WebImageServer;

/**
 *
 * @author zhengjunjie
 */
public interface NDPServe extends WebImageServer{
    /**
     * @return 登录的URL
     */
    String getUrlSignin();

    /**
     * @return 注销的URL
     */
    String getUrlSignout();
    
    String getUrlItemsAddedAfter(Long itemId);

    String getUrlItemsAddedAfterWithAuthz(Long itemId);

    String getPassword();

    String getUsername();

    void setPassword(String password);

    void setUsername(String username);

    /**
     * 
     * @return，更新文件夹内容的URL
     */
    String getUrlForUpdateLinkedFolders();
    
    /**
     * 查询文件夹的元数据
     * @param itemId 
     *         文件夹
     * @param update 
     *         0：不更新;
     *         1：更新
     * @return url
     */
    String getUrlForItemInfo(Long itemId,int update);

    Long getRootLinkedFolderItemId();

    void setRootLinkedFolderItemId(Long rootLinkedFolderItemId);
}

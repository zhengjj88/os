/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.job;

import com.kingmed.dp.ndp.NDPServe;
import com.kingmed.dp.ndp.impl.NDPServeResponseHandler;
import com.kingmed.dp.ndp.impl.SignInResponseHandler;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhengjunjie
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class NDPServeMonitorJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(NDPServeMonitorJob.class);
    private NDPServe ndpServe;
    private Long linkedFolerItemId=9999999L;
    public void setNdpServe(NDPServe ndpServe) {
        this.ndpServe = ndpServe;
    }

    public void setLinkedFolerItemId(Long linkedFolerItemId) {
        this.linkedFolerItemId = linkedFolerItemId;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String cookie = null;
        int retryCounter = 3;
        //JobDataMap data = context.getJobDetail().getJobDataMap();
        //String urlForUpdateLinkedFolders = ndpServe.getUrlForUpdateLinkedFolders(Long.MIN_VALUE);
        try {
            while(retryCounter>0){
                log.info("登录");
                cookie = this.signIn();
                log.info("检测登录是否成功");
                boolean isValidCookie = this.isValidCookie(cookie);
                if (isValidCookie) {
                    
                }else{
                    retryCounter--;
                }
            }
            log.info("更新文件夹");

            log.info("注销");

            log.info("如果注销成功，则返回;如果注销失败，记录错误日志，重试3次之后，则报警");
        } catch (Exception e) {

        } finally {

        }

    }

    public String signIn() throws IOException {
        String signinUrl = null;
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;
        NDPServeResponseHandler responeHandler = null;
        String cookie = null;

        signinUrl = ndpServe.getUrlSignin();
        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(signinUrl);
        responeHandler = new SignInResponseHandler();
        try {
            cookie = httpClient.execute(httpGet, responeHandler);
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return cookie;
    }

    private boolean isValidCookie(String cookie) {
        boolean re = false;
        String cookieRegEx = "SessionID=[0-9a-z]{32}";
        Pattern p = Pattern.compile(cookieRegEx);
        Matcher m = p.matcher(cookie);
        re = m.matches();
        if(!re){
            log.warn("无效cookie"+cookie);
        }
        return re;
    }
    
    public boolean updateLinkedFolders(){
        boolean re = false;
        String url = null;
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;
        NDPServeResponseHandler responeHandler = null;
        
        url= ndpServe.getUrlForUpdateLinkedFolders(linkedFolerItemId);
        
        return true;
    }
}

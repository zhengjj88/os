/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.job;

import com.google.common.base.Strings;
import com.kingmed.dp.ndp.NDPServe;
import com.kingmed.dp.ndp.impl.NDPImageServerImpl;
import com.kingmed.dp.ndp.impl.NDPServeResponseHandler;
import com.kingmed.dp.ndp.impl.SignInResponseHandler;
import com.kingmed.dp.ndp.impl.SignOutResponseHandler;
import com.kingmed.dp.ndp.impl.UpdateLinkedFoldersResponseHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
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
    private Long linkedFolerItemId = 9999999L;

    public void setNdpServe(NDPServe ndpServe) {
        this.ndpServe = ndpServe;
    }

    public void setLinkedFolerItemId(Long linkedFolerItemId) {
        this.linkedFolerItemId = linkedFolerItemId;
    }

    public NDPServe getNdpServe() {
        if(ndpServe == null){
            ndpServe = new NDPImageServerImpl();
            ndpServe.setProtocl("http");
            ndpServe.setHost("www.kingmed.com.cn");
            ndpServe.setPort(7090);
            ndpServe.setUsername("");
            ndpServe.setPassword("");
        }
        return ndpServe;
    }

    public Long getLinkedFolerItemId() {
        linkedFolerItemId = 88L;
        return linkedFolerItemId;
    }

    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String cookie = null;
        String updateStatus = null;
        int retryCounter = 3;
        //JobDataMap data = context.getJobDetail().getJobDataMap();
        //String urlForUpdateLinkedFolders = ndpServe.getUrlForUpdateLinkedFolders(Long.MIN_VALUE);
        try {
            while (retryCounter > 0) {
                log.info("登录");
                cookie = this.signIn();
                
                log.info("检测登录是否成功");
                boolean isValidCookie = this.isValidCookie(cookie);
                if (isValidCookie) {
                    log.info("登录成功");
                    
                    log.info("更新文件夹");
                    updateStatus = this.updateLinkedFolders(cookie.trim(), getLinkedFolerItemId());
                    if(updateStatus!=null&&NDPImageServerImpl.STATUS_SUCCEEDED.equals(updateStatus)){
                        log.info("更新文件夹成功,ItemId="+this.linkedFolerItemId);
                    }else{
                        log.warn("更新文件夹失败,ItemId="+this.linkedFolerItemId);
                    }
                    break;
                } else {
                    log.warn("登录失败");
                    retryCounter--;
                    continue;
                }
            }
        } catch (IOException e) {
            log.error("NDPSereve文件夹监控失败"+getNdpServe().toString(),e);
        } finally {
            if(cookie!=null){
                log.info("注销");
                try {
                    this.signOut(cookie);
                } catch (IOException e) {
                    log.error("注销失败"+getNdpServe().toString()+",cookie =" +cookie,e);
                }
            }
        }

    }

    protected String signIn() throws IOException {
        String cookie = null;
        String uri = getNdpServe().getUrlSignin();
        NDPServeResponseHandler responeHandler = new SignInResponseHandler();
        cookie = this.execute(uri, responeHandler,null);
        return cookie;
    }

    private boolean isValidCookie(String cookie) throws IOException {
        boolean re = false;
        String cookieRegEx = "SessionID=[0-9a-z]{32}";
        Pattern p = Pattern.compile(cookieRegEx);
        Matcher m = p.matcher(cookie);
        re = m.matches();
        if (!re) {
            log.warn("无效cookie" + cookie);
            this.signOut(cookie);
        }
        return re;
    }

    protected String updateLinkedFolders(String cookie ,Long itemId) throws IOException {
        String status = null;
        String uri = getNdpServe().getUrlForUpdateLinkedFolders(itemId);
        NDPServeResponseHandler responeHandler =  new UpdateLinkedFoldersResponseHandler();
        Header header = new BasicHeader("Cookie", cookie);
        status = execute(uri,responeHandler,header);
        return status;
    }
    
    private String execute(String uri,ResponseHandler<String> responeHandler,Header header) throws IOException{
        String re = null;
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;
        
        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(uri);
        if(header!=null){
            httpGet.setHeader(header.getName(), header.getValue());
        }
        try {
            re = httpClient.execute(httpGet, responeHandler);
        } finally {
            if(httpClient!=null)
                httpClient.close();
        }
        return re;
    }
    
    protected String signOut(String cookie) throws IOException {
        Header header = new BasicHeader("Cookie", cookie);
        String uri = getNdpServe().getUrlSignout();
        NDPServeResponseHandler responeHandler = new SignOutResponseHandler();
        String re = this.execute(uri, responeHandler,header);
        log.info("注销\r\n"+re);
        return cookie;
    }
}

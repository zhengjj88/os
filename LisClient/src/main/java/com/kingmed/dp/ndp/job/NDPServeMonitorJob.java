/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.job;

import com.kingmed.dp.ndp.Constants;
import com.kingmed.dp.ndp.NDPServe;
import com.kingmed.dp.ndp.impl.NDPServeFactory;
import com.kingmed.dp.ndp.impl.NDPServeImpl;
import com.kingmed.dp.ndp.impl.NDPServeResponseHandler;
import com.kingmed.dp.ndp.impl.SignInResponseHandler;
import com.kingmed.dp.ndp.impl.SignOutResponseHandler;
import com.kingmed.dp.ndp.impl.UpdateLinkedFoldersResponseHandler;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;
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
    //private NDPServe ndpServe;
    private Set<NDPServe> allNDPserves;
    //private Long linkedFolerItemId = Constants.LINKED_FOLDERS_ITEMID;

    public NDPServeMonitorJob() {
        allNDPserves = NDPServeFactory.getAllNDPServes();
    }

   /*
    public void setNdpServe(NDPServe ndpServe) {
        this.ndpServe = ndpServe;
    }
    */

//    public void setLinkedFolerItemId(Long linkedFolerItemId) {
//        this.linkedFolerItemId = linkedFolerItemId;
//    }

    /*
    public NDPServe getNdpServe() {
        if (ndpServe == null) {
            ndpServe = NDPServeFactory.getNDPServe();
        }
        return ndpServe;
    }
*/
//    public Long getLinkedFolerItemId() {
//        return linkedFolerItemId;
//    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        for (NDPServe serve : allNDPserves) {
            log.info("调用服务器"+serve.toString());
            execute(serve);
        }
    }

    private void execute(NDPServe serve) {
        String cookie = null;
        String updateStatus = null;
        int retryCounter = 3;
        //JobDataMap data = context.getJobDetail().getJobDataMap();
        //String urlForUpdateLinkedFolders = ndpServe.getUrlForUpdateLinkedFolders(Long.MIN_VALUE);
        try {
            while (retryCounter > 0) {
                log.info("登录");
                cookie = this.signIn(serve);

                log.info("检测登录是否成功");
                boolean isValidCookie = this.isValidCookie(cookie);
                if (isValidCookie) {
                    log.info("登录成功");

                    log.info("更新文件夹");
                    updateStatus = this.updateLinkedFolders(cookie.trim(),serve);
                    if (updateStatus != null && NDPServeImpl.STATUS_SUCCEEDED.equals(updateStatus)) {
                        log.info("更新文件夹成功,ItemId=" + serve.getRootLinkedFolderItemId());
                    } else {
                        log.warn("更新文件夹失败,ItemId=" + serve.getRootLinkedFolderItemId());
                    }
                    break;
                } else {
                    log.warn("登录失败");
                    retryCounter--;
                    continue;
                }
            }
        } catch (IOException e) {
            log.error("NDPSereve文件夹监控失败" + serve.toString(), e);
        } finally {
            if (cookie != null) {
                log.info("注销");
                try {
                    this.signOut(cookie,serve);
                } catch (IOException e) {
                    log.error("注销失败" + serve.toString() + ",cookie =" + cookie, e);
                }
            }
        }
    }

    protected String signIn(NDPServe serve) throws IOException {
        String cookie = null;
        String uri = serve.getUrlSignin();
        NDPServeResponseHandler responeHandler = new SignInResponseHandler();
        cookie = this.execute(uri, responeHandler, null);
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
            //this.signOut(cookie);
        }
        return re;
    }

    protected String updateLinkedFolders(String cookie,NDPServe serve) throws IOException {
        String status = null;
        String uri = serve.getUrlForUpdateLinkedFolders();
        NDPServeResponseHandler responeHandler = new UpdateLinkedFoldersResponseHandler();
        Header header = new BasicHeader("Cookie", cookie);
        status = execute(uri, responeHandler, header);
        return status;
    }

    private String execute(String uri, ResponseHandler<String> responeHandler, Header header) throws IOException {
        String re = null;
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;

        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(uri);
        if (header != null) {
            httpGet.setHeader(header.getName(), header.getValue());
        }
        try {
            re = httpClient.execute(httpGet, responeHandler);
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return re;
    }

    protected String signOut(String cookie,NDPServe serve) throws IOException {
        Header header = new BasicHeader("Cookie", cookie);
        String uri = serve.getUrlSignout();
        NDPServeResponseHandler responeHandler = new SignOutResponseHandler();
        String re = this.execute(uri, responeHandler, header);
        log.info("注销\r\n" + re);
        return cookie;
    }
}

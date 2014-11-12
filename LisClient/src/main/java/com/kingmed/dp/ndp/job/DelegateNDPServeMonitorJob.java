/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.job;

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
public class DelegateNDPServeMonitorJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(DelegateNDPServeMonitorJob.class);
    private Set<NDPServe> allNDPserves;

    public DelegateNDPServeMonitorJob() {
        allNDPserves = NDPServeFactory.getAllNDPServes();
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        NDPServeMonitorJob job = new NDPServeMonitorJob();
        job.execute(allNDPserves);
    }

}

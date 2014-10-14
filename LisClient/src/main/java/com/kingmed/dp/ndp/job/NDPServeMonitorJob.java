/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.job;

import com.kingmed.dp.ndp.NDPServe;
import java.util.logging.Level;
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

    public void setNdpServe(NDPServe ndpServe) {
        this.ndpServe = ndpServe;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        //JobDataMap data = context.getJobDetail().getJobDataMap();
        //String urlForUpdateLinkedFolders = ndpServe.getUrlForUpdateLinkedFolders(Long.MIN_VALUE);
        log.info("登录");
        try {
            Thread.sleep(10*1000L);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(NDPServeMonitorJob.class.getName()).log(Level.SEVERE, null, ex);
        }
        log.info("检测登录是否成功");

        log.info("如果登录成功，提取cookie;如果登陆失败，记录错误日志，重试3次之后，则报警");

        log.info("更新文件夹");

        log.info("注销");

        log.info("如果注销成功，则返回;如果注销失败，记录错误日志，重试3次之后，则报警");
    }

}

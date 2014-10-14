/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.job;

import com.kingmed.common.scheduler.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhengjunjie
 */
public class NDPJob implements Job{
    private static final Logger log = LoggerFactory.getLogger(NDPJob.class);
    
    @Override
    public void run() {
        log.info("任务启动");
        log.info("任务启动");
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.ndp.job.scheduler;

import com.kingmed.dp.ndp.job.NDPServeMonitorJob;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import org.quartz.CronTrigger;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhengjunjie
 */
public class Bootstrap {
    
    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);
    private Scheduler sched;

    public Bootstrap() {
        SchedulerFactory sf = new StdSchedulerFactory();
        try {
            sched = sf.getScheduler();
        } catch (SchedulerException e) {
            log.error("初始化调度器出错，退出",e);
            System.exit(1);
        }
    }
    
    private void statrup(){
        log.info("任务开始.................................");
        try {
            JobDetail job = newJob(NDPServeMonitorJob.class)
                    .withIdentity("job1", "group1")
                    .build();
            CronTrigger trigger = newTrigger()
                    .withIdentity("trigger2", "group1")
                    .withSchedule(cronSchedule("0/3 * * * * ?"))
                    .build();
            sched.scheduleJob(job, trigger);
            sched.start();
           // sched.shutdown(true);
        } catch (SchedulerException e) {
            log.error("调度任务运行出错",e);
        }
    }
    
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.statrup();
    }
}

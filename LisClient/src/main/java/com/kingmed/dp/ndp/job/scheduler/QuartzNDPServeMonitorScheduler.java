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
public class QuartzNDPServeMonitorScheduler {
    
    private static final Logger log = LoggerFactory.getLogger(QuartzNDPServeMonitorScheduler.class);
    
    public static void main(String[] args) {
        log.info("start up.................................");
        SchedulerFactory sf = new StdSchedulerFactory();
        try {
            Scheduler sched = sf.getScheduler();
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
        } catch (SchedulerException ex) {
            ex.printStackTrace();
        }
    }
}

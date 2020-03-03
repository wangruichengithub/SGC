package com.app.mdc.schedule.Job;

import com.app.mdc.config.PicConfig;
import com.app.mdc.utils.httpclient.HttpUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Map;

/**
 * 业务代码定时
 */
public class DevopsJob extends QuartzJobBean {

    private static Logger logger = LoggerFactory.getLogger("quartz");

    private final PicConfig picConfig;

    public DevopsJob(PicConfig picConfig){
        this.picConfig = picConfig;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        logger.info("启动定时任务");
        //执行调用
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String url = jobDataMap.getString("url");
        Map<String,Object> map = jobDataMap.getWrappedMap();
        String realUrl = picConfig.getPostUrl()+url;
        //String realUrl = "http://192.168.0.131:9025"+url;
        HttpUtil.quartzPost(realUrl,map,"quartz",jobExecutionContext.getJobDetail().getKey().getName());
        logger.info("任务执行完毕");
    }
}

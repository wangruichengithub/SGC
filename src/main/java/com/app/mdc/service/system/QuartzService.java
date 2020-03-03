package com.app.mdc.service.system;

import com.baomidou.mybatisplus.service.IService;
import com.app.mdc.schedule.ScheduleJob;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 存储每一个已配置的 Job 的详细信息 服务类
 * </p>
 *
 * @author
 * @since 2019-08-02
 */
public interface QuartzService extends IService<ScheduleJob>{
    /**
     * 获取定时任务记录
     * @return ScheduleJob集合
     */
    List<ScheduleJob> getScheduleJobs();

    /**
     * 添加定时任务记录
     * @param scheduleJob 定时任务对象
     * @return ResponseResult
     */
    ResponseResult addQuartz(ScheduleJob scheduleJob);

    /**
     * 更新定时任务
     * @param scheduleJob 定时任务对象
     */
    void updateQuartz(ScheduleJob scheduleJob);

    /**
     * 查询定时任务记录
     * @param params 查询字段
     * @return ResponseResult
     */
    ResponseResult queryQuartz(Page page, Map<String,Object> params);

    /**
     * 批量删除定时任务
     * @param ids 定时任务ids
     * @return ResponseResult
     */
    ResponseResult deleteQuartz(String ids);
}

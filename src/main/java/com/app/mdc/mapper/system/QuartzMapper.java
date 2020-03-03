package com.app.mdc.mapper.system;

import com.app.mdc.schedule.ScheduleJob;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2019-08-02
 */
@Component
public interface QuartzMapper extends BaseMapper<ScheduleJob> {
    /**
     * 查询定时任务
     * @param param 查询条件
     * @return ScheduleJob集合
     */
    List<Map<String,Object>> getScheduleJob(Map<String,Object> param);

}

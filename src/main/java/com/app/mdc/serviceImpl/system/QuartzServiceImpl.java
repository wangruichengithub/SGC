package com.app.mdc.serviceImpl.system;

import com.app.mdc.service.system.QuartzService;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.app.mdc.mapper.system.QuartzMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.app.mdc.schedule.ScheduleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * <p>
 * 存储每一个已配置的 Job 的详细信息 服务实现类
 * </p>
 *
 * @author
 * @since 2019-08-02
 */
@Service("quartzService")
public class QuartzServiceImpl extends ServiceImpl<QuartzMapper, ScheduleJob> implements QuartzService {

    private final QuartzMapper quartzMapper;

    @Autowired
    public QuartzServiceImpl(QuartzMapper quartzMapper){
        this.quartzMapper = quartzMapper;
    }

    @Override
    public List<ScheduleJob> getScheduleJobs() {
        return quartzMapper.selectByMap(new HashMap<>());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult addQuartz(ScheduleJob scheduleJob) {
        quartzMapper.insert(scheduleJob);
        return ResponseResult.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateQuartz(ScheduleJob scheduleJob) {
        quartzMapper.updateById(scheduleJob);
    }

    @Override
    public ResponseResult queryQuartz(Page page, Map<String, Object> params) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<Map<String,Object>> scheduleJobs = quartzMapper.getScheduleJob(params);
        return ResponseResult.success().setData(new PageInfo<>(scheduleJobs));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult deleteQuartz(String ids) {
        String[] id = ids.split(",");
        EntityWrapper<ScheduleJob> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("job_id",id);
        quartzMapper.delete(entityWrapper);
        return ResponseResult.success();
    }
}

package com.app.mdc.serviceImpl.system;

import com.app.mdc.mapper.system.LogMapper;
import com.app.mdc.model.system.Log;
import com.app.mdc.service.system.LogService;
import com.app.mdc.utils.jdbc.SqlUtils;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author
 * @since 2019-08-01
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    private final LogMapper logMapper;

    @Autowired
    public LogServiceImpl(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Override
    public void saveOrUpdateLog(Log log) {
        logMapper.insert(log);
    }

    @Override
    public ResponseResult findLogsPage(Page page, Map<String, Object> map) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        map.put("deleted",0);
        EntityWrapper<Log> entity = new EntityWrapper<>();
        entity.eq("deleted", 0)
                .orderDesc(SqlUtils.orderBy("updatetime"));
        List<Log> files = logMapper.selectList(entity);
        return ResponseResult.success().add(new PageInfo<>(files));
    }

    @Override
    public Log getOneLog(String id) {
        return this.baseMapper.selectById(id);
    }
}

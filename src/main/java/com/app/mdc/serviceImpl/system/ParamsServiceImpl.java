package com.app.mdc.serviceImpl.system;

import com.app.mdc.mapper.system.ParamsMapper;
import com.app.mdc.model.system.Params;
import com.app.mdc.service.system.ParamsService;
import com.app.mdc.utils.jdbc.SqlUtils;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2019-06-12
 */
@Service
public class ParamsServiceImpl extends ServiceImpl<ParamsMapper, Params> implements ParamsService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addParams(String status, String name, String keyName, String keyValue, String remark) {
        Date now = new Date();
        Params params = new Params();
        params.setName(name);
        params.setKeyName(keyName);
        params.setKeyValue(keyValue);
        params.setRemark(remark);
        params.setStatus(status);
        params.setDeleted(0);
        params.setCreatetime(now);
        params.setUpdatetime(now);
        int rowCount = this.baseMapper.insert(params);
        return rowCount == 1 ? ResponseResult.success() : ResponseResult.fail();

    }

    @Override
    public ResponseResult selectPage(Page page, String name, String keyName) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        EntityWrapper<Params> entity = new EntityWrapper<>();
        entity.eq("deleted", 0);
        if (StringUtils.isNotEmpty(name)) {
            entity.like("name", name);
        }
        if (StringUtils.isNotEmpty(keyName)) {
            entity.like("keyName", keyName);
        }
        entity.orderDesc(SqlUtils.orderBy("updatetime"));
        List<Params> list = this.baseMapper.selectList(entity);
        return ResponseResult.success().add(new PageInfo<>(list));
    }

    @Override
    public ResponseResult getOne(String id) {
        Params params = this.baseMapper.selectById(id);
        return ResponseResult.success().add(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateParams(String id, String status, String name, String keyName, String keyValue,
                                       String remark) {
        Params params = new Params();
        params.setId(id);
        params.setStatus(status);
        params.setName(name);
        params.setKeyName(keyName);
        params.setKeyValue(keyValue);
        params.setUpdatetime(new Date());
        params.setRemark(remark);
        int count = this.baseMapper.updateById(params);
        return count == 1 ? ResponseResult.success() : ResponseResult.fail();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteParams(String id) {
        Params params = new Params();
        params.setId(id);
        params.setDeleted(1);
        int count = this.baseMapper.updateById(params);
        return count == 1 ? ResponseResult.success() : ResponseResult.fail();

    }

    @Override
    public Map<String, String> findSystemParams() {
        EntityWrapper<Params> entity = new EntityWrapper<>();
        entity.eq("deleted", 0)
                .eq("status", "A");
        List<Params> lists = this.baseMapper.selectList(entity);
        return lists.stream().collect(Collectors.toMap(
                Params::getKeyName,
                params->params.getKeyValue() == null ? "" : params.getKeyValue()
        ));
    }

    @Override
    public Params getAppParam(String keyName) {
        EntityWrapper<Params> entity = new EntityWrapper<>();
        entity.eq("deleted", 0)
                .eq("status", "A")
                .eq("key_name",keyName);
        return this.baseMapper.selectList(entity).get(0);
    }

}

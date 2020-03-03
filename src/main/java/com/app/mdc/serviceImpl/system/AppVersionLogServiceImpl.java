package com.app.mdc.serviceImpl.system;

import com.app.mdc.mapper.system.AppVersionLogMapper;
import com.app.mdc.mapper.system.ParamsMapper;
import com.app.mdc.model.system.AppVersionLog;
import com.app.mdc.model.system.Params;
import com.app.mdc.service.system.AppVersionLogService;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author
 * @since 2019-08-15
 */
@Service
public class AppVersionLogServiceImpl extends ServiceImpl<AppVersionLogMapper, AppVersionLog> implements AppVersionLogService {

	private ParamsMapper paramsMapper;

	@Autowired
	public AppVersionLogServiceImpl(ParamsMapper paramsMapper) {
		this.paramsMapper = paramsMapper;
	}

	@Override
	public List<AppVersionLog> getList(Map<String, Object> map) {
		EntityWrapper<AppVersionLog> entityWrapper=new EntityWrapper<>();
		if(null != map.get("versionNumber")) {
			entityWrapper.like("version_number", map.get("versionNumber").toString());
		}
		entityWrapper.eq("deleted",0);
		entityWrapper.orderBy("updatetime",false);
		return this.baseMapper.selectList(entityWrapper);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseResult addAppVersionLog(Map<String, Object> map) {
		//1.获取app更新信息
		EntityWrapper<Params> paramsEntityWrapper=new EntityWrapper<>();
		paramsEntityWrapper.eq("key_name","app_version");
		Params params=paramsMapper.selectList(paramsEntityWrapper).get(0);
		//
		AppVersionLog appVersionLog=new AppVersionLog();
		appVersionLog.setUpdatetime(new Date());
		appVersionLog.setCreatetime(new Date());
		appVersionLog.setDeleted(0);
		appVersionLog.setVersionNumber(params.getKeyValue());
		appVersionLog.setVersionInfo(params.getRemark());
		int rowCount=this.baseMapper.insert(appVersionLog);
		return rowCount==1 ? ResponseResult.success() : ResponseResult.fail();
	}

	@Override
	public AppVersionLog getOne(String id) {
		return this.baseMapper.selectById(id);
	}
}

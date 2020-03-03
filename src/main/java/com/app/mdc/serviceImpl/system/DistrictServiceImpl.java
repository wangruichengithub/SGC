package com.app.mdc.serviceImpl.system;

import com.app.mdc.mapper.system.DistrictMapper;
import com.app.mdc.model.system.District;
import com.app.mdc.service.system.DistrictService;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
 * @since 2019-06-17
 */
@Service
public class DistrictServiceImpl extends ServiceImpl<DistrictMapper, District> implements DistrictService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseResult add(Map<String, Object> map) {
		Date now=new Date();
		District district=new District();
		district.fromMap(map);
		district.setCreatetime(now);
		district.setUpdatetime(now);
		district.setDeleted(0);
		//当父级区划id为空的时候添加parentId为0
		if(null == map.get("parentId") || "".equals(map.get("parentId"))) {
			district.setParentId("100000");
		}
		int count=this.baseMapper.insert(district);
		return count == 1 ? ResponseResult.success() : ResponseResult.fail();
	}

	@Override
	public List<District> findDistricts(Map<String, Object> map) {
		EntityWrapper<District> entity=new EntityWrapper<>();
		//根据名称和编码模糊查询
		if(null != map.get("code") || null != map.get("name")){
			if(null != map.get("code")) {
				entity.like("code", map.get("code").toString());
			}
			if(null != map.get("name")) {
				entity.like("name", map.get("name").toString());
			}
		}else{
			//父级行政区id为空加载省级，不为空加载子级
			if(null != map.get("parentId")) {
				entity.eq("parent_id", map.get("parentId").toString());
			}else{
				entity.eq("parent_id", "100000");
			}
		}
		entity.eq("deleted", 0);
		return this.baseMapper.selectList(entity);
	}

	@Override
	public District getOne(Map<String, Object> map) {
		EntityWrapper<District> entity=new EntityWrapper<>();
		//根据id可查某一个区域
		if(null != map.get("id")) {
			entity.eq("id", map.get("id").toString());
		}
		entity.eq("deleted", 0);
		return this.baseMapper.selectList(entity).get(0);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseResult updateDistrict(Map<String, Object> map) {
		Date now=new Date();
		District district=new District();
		district.fromMap(map);
		district.setUpdatetime(now);
		int count=this.baseMapper.updateById(district);
		return count == 1 ? ResponseResult.success() : ResponseResult.fail();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseResult deleteDistrict(Map<String, Object> map) {
		District district=new District();
		district.fromMap(map);
		district.setDeleted(1);
		int count=this.baseMapper.updateById(district);
		return count == 1 ? ResponseResult.success() : ResponseResult.fail();
	}

}

package com.app.mdc.serviceImpl.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.app.mdc.enums.ApiErrEnum;
import com.app.mdc.mapper.system.DictMapper;
import com.app.mdc.model.system.Dict;
import com.app.mdc.service.system.DictService;
import com.app.mdc.utils.TreeUtils;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author
 * @since 2019-06-12
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseResult addDict(Map<String, Object> map) {
		Date now=new Date();
		//根据编号判断该字典是否存在
		Map<String, Object> newMap=new HashMap<>();
		newMap.put("number", map.get("number"));
		newMap.put("deleted", 0);
		List<Dict> list=this.baseMapper.selectByMap(newMap);
		if(list.size()>0) {
			return ResponseResult.fail(ApiErrEnum.ERR601);
		}else {
			Dict dic=new Dict();
			dic.fromMap(map);
			dic.setCreatetime(now);
			dic.setUpdatetime(now);
			dic.setDeleted(0);
			int count=this.baseMapper.insert(dic);
			return count == 1 ? ResponseResult.success() : ResponseResult.fail();
		}
		
	}

	@Override
	public Dict getOne(Map<String, Object> map) {
		List<Dict> list=getList(map);
		if(list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public List<Dict> getList(Map<String, Object> map) {
		EntityWrapper<Dict> entity=new EntityWrapper<>();
		if(null != map.get("name")) {
			entity.like("name", map.get("name").toString());
		}
		if(null != map.get("id")) {
			entity.eq("id", map.get("id").toString());
		}
		entity.eq("deleted",0)
	        .orderBy("rank");
		return this.baseMapper.selectList(entity);
	}

	@Override
	public ResponseResult selectTree(Map<String, Object> map) {
		List<Dict> list=getList(map);
		JSONArray result = TreeUtils.listToTree(JSONArray.parseArray(JSON.toJSONStringWithDateFormat(list,"yyyy-MM-dd HH:mm:ss",SerializerFeature.WriteDateUseDateFormat)),"id","pid","children");
		return ResponseResult.success().add(result);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseResult updateDict(Map<String, Object> map) {
		Dict dic=new Dict();
		dic.fromMapWithoutNumber(map);
		dic.setUpdatetime(new Date());
		int count=this.baseMapper.updateById(dic);
		return count == 1 ? ResponseResult.success() : ResponseResult.fail();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseResult deleteDict(Map<String, Object> map) {
		Dict dic=new Dict();
		dic.fromMap(map);
		dic.setUpdatetime(new Date());
		dic.setDeleted(1);
		int count=this.baseMapper.updateById(dic);
		return count == 1 ? ResponseResult.success() : ResponseResult.fail();
	}

	@Override
	public List<Dict> getDictsByNumber(Map<String, Object> map) {
		EntityWrapper<Dict> entity=new EntityWrapper<>();
		//number为空失败
		if(null != map.get("number")) {
			List<Dict> list=this.baseMapper.selectByMap(map);
			//number下面没有子级元素失败
			if(list.size()>0) {
				entity.eq("pid", list.get(0).getId()).eq("deleted", 0).eq("status","A");
				return this.baseMapper.selectList(entity);
			}else {
				return null;
			}
		}else {
			return null;
		}
	}

}

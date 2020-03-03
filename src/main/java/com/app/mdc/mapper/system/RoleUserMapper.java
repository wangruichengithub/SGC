package com.app.mdc.mapper.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.app.mdc.model.system.RoleUser;
import com.app.mdc.model.system.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2019-06-11
 */
@Component
public interface RoleUserMapper extends BaseMapper<RoleUser> {
	
	List<Map> getUserList(Map<String,Object> roleUser);

	List<User> getOperaters(Map<String,Object> map);
}

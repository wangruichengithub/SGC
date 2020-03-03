package com.app.mdc.mapper.socket;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.app.mdc.model.socket.Team;
import com.app.mdc.model.system.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2019-07-23
 */
@Component
public interface TeamMapper extends BaseMapper<Team> {

    List<User> findTeamUsersByTeamId(@Param("teamId") String teamId);
}

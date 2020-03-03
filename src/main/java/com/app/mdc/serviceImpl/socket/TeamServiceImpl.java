package com.app.mdc.serviceImpl.socket;

import com.app.mdc.exception.BusinessException;
import com.app.mdc.mapper.socket.TeamMapper;
import com.app.mdc.mapper.socket.UserTeamMapper;
import com.app.mdc.mapper.system.UserMapper;
import com.app.mdc.model.socket.Team;
import com.app.mdc.model.socket.UserTeam;
import com.app.mdc.model.system.User;
import com.app.mdc.service.socket.TeamService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author
 * @since 2019-07-23
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    private final TeamMapper teamMapper;
    private final UserTeamMapper userTeamMapper;
    private final UserMapper userMapper;
    public TeamServiceImpl(TeamMapper teamMapper, UserTeamMapper userTeamMapper, UserMapper userMapper) {
        this.teamMapper = teamMapper;
        this.userTeamMapper = userTeamMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<Team> findMyTeams(User currentUser) {

        EntityWrapper<UserTeam> entityWrapper = new EntityWrapper<>();
        entityWrapper
                .eq("user_id", currentUser.getId());
        List<UserTeam> userTeams = userTeamMapper.selectList(entityWrapper);

        List<String> teamIds = userTeams.stream()
                .map(UserTeam::getTeamId)
                .collect(Collectors.toList());

        EntityWrapper<Team> entityWrapperTeam = new EntityWrapper<>();
        entityWrapper
                .eq("deleted", 0)
                .in("id", teamIds);
        return teamMapper.selectList(entityWrapperTeam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Team saveOrUpdateSocketTeam(String teamId, String teamName, String userIds) throws BusinessException {
        String[] userIdStrings = userIds.split(",");
        Timestamp current = new Timestamp(System.currentTimeMillis());

        Team team;
        if (StringUtils.isEmpty(teamId)){
            team = new Team();
            team.setCreatetime(current);
            team.setDeleted(0);
            team.setUpdatetime(current).setName(teamName);
            team.insert();
        }else{
            team = teamMapper.selectById(teamId);
            if (team == null){
                throw new BusinessException("没有找到该群聊！");
            }
            //删除该群聊下所有的用户
            Map<String, Object> userTeamDeleteMap = new HashMap<>();
            userTeamDeleteMap.put("team_id", teamId);
            userTeamMapper.deleteByMap(userTeamDeleteMap);

            team.setUpdatetime(current).setName(teamName);
            team.updateById();
        }

        for (String userId: userIdStrings) {
            UserTeam userTeam = new UserTeam();
            userTeam.setUserId(userId);
            userTeam.setTeamId(team.getId());
            userTeamMapper.insert(userTeam);
        }
        return team;
    }

    @Override
    public List<User> getUsersByTeam(String teamId) {
        EntityWrapper<UserTeam> entityWrapper = new EntityWrapper<>();
        entityWrapper
                .eq("team_id", teamId);
        List<UserTeam> userTeams = userTeamMapper.selectList(entityWrapper);

        List<String> userIds = userTeams.stream()
                .map(UserTeam::getUserId)
                .collect(Collectors.toList());

        return userMapper.selectBatchIds(userIds);
    }
}

package com.app.mdc.controller.socket;

import com.app.mdc.controller.BaseController;
import com.app.mdc.exception.BusinessException;
import com.app.mdc.model.socket.Team;
import com.app.mdc.model.system.User;
import com.app.mdc.service.socket.MessageService;
import com.app.mdc.service.socket.TeamService;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/socket")
public class SocketController extends BaseController {

    private final MessageService messageService;
    private final TeamService teamService;
    public SocketController(MessageService messageService, TeamService teamService) {
        this.messageService = messageService;
        this.teamService = teamService;
    }

    /**
     * 获取会话列表
     * @param httpSession httpsession
     * @return ResponseResult
     */
    @PostMapping(value = "messageList")
    @ResponseBody
    public ResponseResult findMessageList(HttpSession httpSession){
        ResponseResult responseResult = new ResponseResult();

        Map<String, Object> result = new HashMap<>();
        List<Team> teamList = teamService.findMyTeams(currentUser(httpSession));
        result.put("teams", teamList);

        List<User> users = messageService.findMessageUsers(currentUser(httpSession));
        result.put("users", users);

        List<Map<String, Object>> messageList = messageService.findMessagesList(currentUser(httpSession));
        result.put("list", messageList);

        responseResult.add(result);

        return responseResult;
    }

    /**
     * 分页获取会话消息
     * @param type 类型，1：点对点，2：群聊
     * @param teamId 群聊Id
     * @param receiveUserId 发送给的用户Id
     * @param page page
     * @return ResponseResult
     */
    @PostMapping("getMessageByChat")
    @ResponseBody
    public ResponseResult getMessageByChat(
            @RequestParam String type, Page page, HttpSession httpSession,
            @RequestParam(required = false, defaultValue = "") String teamId,
            @RequestParam(required = false, defaultValue = "") String receiveUserId){
        return messageService.getMessageByChat(page, type, teamId, receiveUserId, currentUser(httpSession));
    }

    /**
     * 新增，编辑群聊
     * @param teamName 群聊名称
     * @param userIds 用户ids集合，逗号分隔
     */
    @PostMapping(value = "saveOrUpdateSocketTeam")
    @ResponseBody
    public ResponseResult saveOrUpdateSocketTeam(@RequestParam(defaultValue = "") String teamId,
                                                 String teamName,
                                                 String userIds){
        ResponseResult responseResult = new ResponseResult();
        try {
            Team team = teamService.saveOrUpdateSocketTeam(teamId, teamName, userIds);
            responseResult.setData(team);
        } catch (BusinessException e) {
            responseResult.setErrMsg("ERR500", e.getMessage());
        }
        return responseResult;
    }


    /**
     * 根据群聊id获取所有得用户
     * @param teamId 群聊id
     * @return ResponseResult
     */
    @PostMapping(value = "getUsersByTeam")
    @ResponseBody
    public ResponseResult getUsersByTeam(@RequestParam String teamId){
        ResponseResult responseResult = new ResponseResult();
        List<User> users = teamService.getUsersByTeam(teamId);
        responseResult.setData(users);
        return responseResult;
    }

    /**
     *app推送
     * @param param	username	title	text
     * @return	推送结果
     */
    @PostMapping(value = "msgPush")
    @ResponseBody
    public ResponseResult  msgPush(@RequestParam Map<String,Object> param){
        String result=messageService.msgPush(param);
        return ResponseResult.success().add(result);
    }
}

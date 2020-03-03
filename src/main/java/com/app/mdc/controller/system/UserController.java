package com.app.mdc.controller.system;

import com.app.mdc.annotation.anno.SystemLogAnno;
import com.app.mdc.controller.BaseController;
import com.app.mdc.enums.ApiErrEnum;
import com.app.mdc.exception.BusinessException;
import com.app.mdc.model.system.User;
import com.app.mdc.service.system.UserService;
import com.app.mdc.service.system.VerificationCodeService;
import com.app.mdc.utils.viewbean.Page;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 用户管理controller
 */
@Controller
@RequestMapping("/admin/users")
@Api(description = "用户管理")
public class UserController extends BaseController {

    private final UserService userService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查找所有的user并分页
     *
     * @param page 分页插件
     * @param name 用户姓名
     * @return 所有user的分页list
     */
    @RequestMapping("/selectPage")
    @ResponseBody
    public ResponseResult selectPage(Page page, String name) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<User> userList = userService.findUserByPage(name);
        return ResponseResult.success().add(new PageInfo<>(userList));
    }

    /**
     * 获取用户的信息
     *
     * @param id 用户id
     * @return 某一个具体的用户
     */
    @RequestMapping("/getOne")
    @ResponseBody
    public ResponseResult getOne(String id) {
        return userService.getOne(id);
    }


    @RequestMapping("/updateUserName")
    @ResponseBody
    public ResponseResult updateUserName(@RequestParam String userId, @RequestParam String userName) throws BusinessException {
        userService.updateUserName(userId, userName);
        return ResponseResult.success();
    }

    /**
     * 新增
     *
     * @param map id   username password    name    telephone   position    remark  status  roleId  companyid   rank    code
     * @return 返回的结果，checkTable
     */
    @PostMapping("/add")
    @SystemLogAnno(module = "用户管理", operation = "新增用户")
    @ResponseBody
    @ApiOperation("新增用户")
    public ResponseResult add(@RequestParam Map<String, Object> map) throws Throwable {
        if (!map.containsKey("verCode") || !map.containsKey("verId")) {
            throw new BusinessException("验证码验证失败");
        }
        boolean b = verificationCodeService.validateVerCode(map.get("verCode").toString(), map.get("verId").toString());
        if (!b) {
            throw new BusinessException("验证码验证失败");
        }
        return userService.add(map);
    }

    /**
     * 修改
     *
     * @param map id   username password    name    telephone   position    remark  status  roleId  companyid   rank    code
     * @return 返回的结果，checkTable
     */
    @RequestMapping("/update")
    @SystemLogAnno(module = "用户管理", operation = "修改用户信息")
    @ResponseBody
    public ResponseResult update(@RequestParam Map<String, Object> map) {
        return userService.update(map);
    }

    /**
     * 删除
     *
     * @param id 用户id
     * @return 返回的结果，checkTable
     */
    @RequestMapping("/delete")
    @SystemLogAnno(module = "用户管理", operation = "删除用户")
    @ResponseBody
    public ResponseResult delete(String id) {
        return userService.delete(id);
    }

    /**
     * 修改密码
     *
     * @param id          id
     * @param newPassword 新密码
     * @param loginName   登录名称
     * @param verCode     验证码
     * @param verId       生成验证码的id
     * @throws BusinessException 抛出错误
     */
    @RequestMapping("/updatePwd")
    @SystemLogAnno(module = "用户管理", operation = "app端修改用户密码")
    @ApiOperation("app端修改用户密码")
    @ResponseBody
    public ResponseResult updatePwd(
            @RequestParam Integer type,
            @RequestParam String id,
            @RequestParam String newPassword,
            @RequestParam String loginName,
            @RequestParam String verCode,
            @RequestParam String verId
    ) {
        ResponseResult responseResult = new ResponseResult();
        try {
            userService.updatePwd(type, id, newPassword, loginName, verCode, verId);
        } catch (BusinessException e) {
            responseResult.setErrMsg(ApiErrEnum.ERR500.toString(), e.getMessage());
        }
        return responseResult;
    }


    @RequestMapping("/resetPassword")
    @ApiOperation("忘记密码")
    @ResponseBody
    public ResponseResult resetPassword(
            @RequestParam String verCode,
            @RequestParam String verId,
            @RequestParam String loginName,
            @RequestParam String password,
            @RequestParam String payPassword
    ) throws BusinessException {
        boolean b = verificationCodeService.validateVerCode(verCode, verId);
        if (!b) {
            throw new BusinessException("验证码验证失败");
        }
        this.userService.resetPassword(loginName, password, payPassword);
        return ResponseResult.success();
    }

    /**
     * 修改密码
     *
     * @param gestureSwitch 0关 1开
     * @param userId        用户id
     * @throws BusinessException 抛出错误
     */
    @RequestMapping("/updateGestureSwitch")
    @SystemLogAnno(module = "用户管理", operation = "手势密码开关")
    @ApiOperation("手势密码开关")
    @ResponseBody
    public ResponseResult updateGestureSwitch(
            @RequestParam Integer gestureSwitch,
            @RequestParam String userId,
            @RequestParam String verId,
            @RequestParam String verCode
    ) throws BusinessException {
        boolean b = verificationCodeService.validateVerCode(verCode, verId);
        if (!b) {
            throw new BusinessException("验证码验证失败");
        }
        userService.updateGestureSwitch(gestureSwitch, userId);
        return ResponseResult.success();
    }


    /**
     * 手机端通讯录
     *
     * @param map 角色
     * @return 用户list
     */
    @RequestMapping("/getAddressBook")
    @ResponseBody
    public ResponseResult getAddressBook(@RequestParam Map<String, Object> map, HttpSession httpSession, Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        String userId = currentUser(httpSession).getId();
        List<Map<String, Object>> mapList = userService.getAddressBook(map, userId);
        return ResponseResult.success().add(new PageInfo<>(mapList));
    }

    /**
     * 更新cid
     *
     * @param id  用户id
     * @param cid 用户cid
     * @return 0，500
     */
    @RequestMapping("/updateCid")
    @SystemLogAnno(module = "用户管理", operation = "app端修改用户手机cid")
    @ResponseBody
    public ResponseResult updateCid(@RequestParam String id, @RequestParam String cid) {
        return userService.updateCid(id, cid);
    }

    /**
     * pc查找所有的user
     *
     * @return 所有userlist
     */
    @RequestMapping("/pcAddressBook")
    @ResponseBody
    public ResponseResult pcAddressBook(HttpSession httpSession) {
        List<Map<String, Object>> mapList = userService.pcAddressBook(currentUser(httpSession).getId());
        return ResponseResult.success().add(mapList);
    }
}

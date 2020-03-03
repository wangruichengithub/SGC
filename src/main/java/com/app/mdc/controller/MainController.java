package com.app.mdc.controller;

import com.app.mdc.annotation.anno.SystemLogAnno;
import com.app.mdc.config.PicConfig;
import com.app.mdc.enums.ApiErrEnum;
import com.app.mdc.exception.BusinessException;
import com.app.mdc.model.system.File;
import com.app.mdc.model.system.Params;
import com.app.mdc.service.system.*;
import com.app.mdc.utils.viewbean.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * maincontroller
 */
@Controller
@Api("核心接口")
@RequestMapping("/")
public class MainController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    private final UserService userService;
    private final ParamsService paramsService;
    private final FileService fileService;
    private final PicConfig picConfig;
    private final AppVersionLogService appVersionLogService;
    @Autowired
    private  VerificationCodeService verificationCodeService;

    public MainController(UserService userService, ParamsService paramsService, FileService fileService, PicConfig picConfig, AppVersionLogService appVersionLogService) {
        this.userService = userService;
        this.paramsService = paramsService;
        this.fileService = fileService;
        this.picConfig = picConfig;
        this.appVersionLogService = appVersionLogService;
    }

    /**
     * 校验app版本，是否需要更新
     *
     * @return String
     */
    @RequestMapping(value = "checkAppVersion")
    @ResponseBody
    public String checkVersion() {
        Map<String, String> params = paramsService.findSystemParams();
        return params.get("app_version");
    }

    /**
     * 用户分享注册
     */
    @PostMapping("/registerAdd")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public ResponseResult registerAdd(
            @RequestParam String userName,
            @RequestParam String loginName,
            @RequestParam String password,
            @RequestParam String walletPassword,
            @RequestParam Integer sendCode,
            @RequestParam String verCode,
            @RequestParam String verId,
            @RequestParam Integer registerType
    )
    {
        boolean b = verificationCodeService.validateVerCode(verCode, verId);
        if(!b){
            return ResponseResult.fail("403","验证码校验错误");
        }
        return this.userService.registerAdd(userName,loginName,password,walletPassword,sendCode,registerType);
    }


    /**
     * 用户登录接口
     *
     * @param loginName   用户名
     * @param password    密码
     * @param loginType   登录类型，区分：app,pc
     * @param httpSession session
     * @return session
     */
    @PostMapping(value = "doLogin")
    @SystemLogAnno(module = "用户管理", operation = "用户登录")
    @ResponseBody
    @ApiOperation("用户登录")
    public ResponseResult doLogin(@RequestParam String loginName,
                                  @RequestParam String password,
                                  @RequestParam(required = false, defaultValue = "0") String loginType,
                                  HttpSession httpSession) {
        ResponseResult responseResult = new ResponseResult();
        try {
            Map map = userService.doUserLogin(loginName, password, httpSession, loginType);
            responseResult.setData(map);
        } catch (BusinessException e) {
            responseResult.setErrMsg(ApiErrEnum.ERR500.toString(), e.getMessage());
        }
        return responseResult;
    }

    /**
     * 用户登录接口
     *
     * @param userId 用户id
     * @return session
     */
    @PostMapping(value = "doLoginOut")
    @SystemLogAnno(module = "用户管理", operation = "退出登录")
    @ResponseBody
    @ApiOperation("退出登录")
    public ResponseResult doLoginOut(@RequestParam Integer userId, HttpSession session) {
        session.removeAttribute("user");
        userService.removeTokenByUserId(userId);
        return ResponseResult.success();
    }


    /**
     * 获取系统所有参数值
     *
     * @return params列表
     */
    @PostMapping(value = "params")
    @ResponseBody
    public ResponseResult findSystemParams() {
        ResponseResult responseResult = new ResponseResult();
        Map<String, String> params = paramsService.findSystemParams();
        responseResult.setData(params);
        return responseResult;
    }


    /**
     * 图片显示，文件下载
     *
     * @param id                  id
     * @param httpServletResponse response
     * @throws UnsupportedEncodingException 文件未找到异常
     */
    @RequestMapping(value = "downloadFile/{id}")
    @ResponseBody
    public void downloadFile(@PathVariable String id, HttpServletResponse httpServletResponse) throws UnsupportedEncodingException {
        File file = fileService.findFileById(id);
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getFilename(), "UTF-8"));
        OutputStream out;
        try (InputStream in = new FileInputStream(picConfig.getSavePath() + file.getFilepath())) {
            int len;
            byte buffer[] = new byte[1024];
            out = httpServletResponse.getOutputStream();
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            logger.error("系统异常", e);
        }
    }

    /**
     * 获取文件详情接口
     *
     * @param ids 文件id
     * @return file实体类
     */
    @RequestMapping(value = "getFile/{ids}")
    @ResponseBody
    public ResponseResult getFile(@PathVariable String ids) {
        ResponseResult responseResult = new ResponseResult();
        List<File> file = fileService.findFilesByIds(ids);
        responseResult.setData(file);
        return responseResult;
    }

    /**
     * 修改密码
     *
     * @param id          id
     * @param newPassword 新密码
     * @param oldPassword 老密码
     * @throws BusinessException 抛出错误
     */
    @RequestMapping(value = "updatePwd")
    @SystemLogAnno(module = "用户管理", operation = "app端修改用户密码")
    @ResponseBody
    public ResponseResult updatePwd(
            @RequestParam Integer type,
            @RequestParam String id,
            @RequestParam String newPassword,
            @RequestParam String oldPassword,
            @RequestParam String verCode,
            @RequestParam String verId) {
        ResponseResult responseResult = new ResponseResult();
        try {
            userService.updatePwd(type,id, newPassword, oldPassword, verCode, verId);
        } catch (BusinessException e) {
            responseResult.setErrMsg(ApiErrEnum.ERR500.toString(), e.getMessage());
        }
        return responseResult;
    }

    /**
     * 根据键值获取参数
     *
     * @param keyName 键值
     * @return 参数
     */
    @RequestMapping(value = "getAppParam")
    @ResponseBody
    public ResponseResult getAppParam(@RequestParam String keyName) {
        Params params = paramsService.getAppParam(keyName);
        return ResponseResult.success().setData(params);
    }

    /**
     * 新增
     *
     * @param map versionNumber版本号	versionInfo版本信息
     * @return 返回正确错误信息
     */
    @RequestMapping(value = "addAppVersionLog")
    @ResponseBody
    public ResponseResult addAppVersionLog(@RequestParam Map<String, Object> map) {
        return appVersionLogService.addAppVersionLog(map);
    }
}

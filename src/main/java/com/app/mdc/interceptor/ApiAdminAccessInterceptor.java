package com.app.mdc.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.app.mdc.config.whiteList.WhiteListProperties;
import com.app.mdc.enums.ApiErrEnum;
import com.app.mdc.exception.BusinessException;
import com.app.mdc.model.system.User;
import com.app.mdc.service.system.UserService;
import com.app.mdc.utils.BaseUtils;
import com.app.mdc.utils.viewbean.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Writer;
import java.util.List;

public class ApiAdminAccessInterceptor extends BaseUtils implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private WhiteListProperties whiteListProperties;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
            throws Exception {

        //设置接口跨域问题
        httpServletResponse.setHeader("Cache-Control","no-cache");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,usertoken");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials","*");
        httpServletResponse.setHeader("Access-control-expose-headers", "Authorization");

        //判断是否为白名单接口
        String requestURI = httpServletRequest.getRequestURI();
        List<String> blankList = whiteListProperties.getBlankList();
        if(blankList != null && blankList.size() > 0 && blankList.contains(requestURI)){
            return true;
        }

        String userToken = httpServletRequest.getHeader("usertoken");
        if ( isEmpty(userToken) ){
            ResponseResult responseResult = new ResponseResult();
            httpServletResponse.setContentType("text/html;charset=utf-8");
            Writer writer = httpServletResponse.getWriter();
            responseResult.setErrMsg(ApiErrEnum.ERR100.toString(), ApiErrEnum.ERR100.getDesc());
            writer.write(JSONObject.toJSONString(responseResult));
            writer.flush();
            writer.close();
            return false;
        }
        HttpSession httpSession = httpServletRequest.getSession();

        //判断session中是否有user，如果有则跳过拦截
        if (httpSession.getAttribute("user") == null){
            try {
                User user = userService.findUserByToken(userToken);
                httpSession.setAttribute("user", user);
            }catch (BusinessException e){
                e.printStackTrace();
                ResponseResult responseResult = new ResponseResult();
                httpServletResponse.setContentType("text/html;charset=utf-8");
                Writer writer = httpServletResponse.getWriter();
                responseResult.setErrMsg(ApiErrEnum.ERR101.toString(), ApiErrEnum.ERR101.getDesc());
                writer.write(JSONObject.toJSONString(responseResult));
                writer.flush();
                writer.close();
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }
}

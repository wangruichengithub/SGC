package com.app.mdc.controller;

import com.app.mdc.model.system.User;
import com.app.mdc.utils.BaseUtils;

import javax.servlet.http.HttpSession;

public class BaseController extends BaseUtils {

    /**
     * 获取当前用户
     * @param httpSession httpsession
     * @return User
     */
    protected User currentUser(HttpSession httpSession) {
        return (User) httpSession.getAttribute("user");
    }


}

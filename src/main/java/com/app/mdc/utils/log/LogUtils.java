package com.app.mdc.utils.log;

import com.app.mdc.model.system.User;
import com.app.mdc.utils.date.DateUtil;

import java.util.Date;

public class LogUtils {

    public static String getInterfaceMessage(User user, String interfaces){
        return DateUtil.getDate("yyyy-MM-dd hh:mm:ss",0,new Date())+" "+user.getUserName()+interfaces;
    }
}

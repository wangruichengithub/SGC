package com.app.mdc.annotation.aspect;

import com.alibaba.fastjson.JSON;
import com.app.mdc.annotation.anno.SystemLogAnno;
import com.app.mdc.model.system.Log;
import com.app.mdc.model.system.User;
import com.app.mdc.service.system.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
public class SystemLogAspect {

    private static Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    private final LogService logService;


    @Autowired
    public SystemLogAspect(LogService logService) {
        this.logService = logService;
    }

    @Pointcut(value = "@annotation(com.app.mdc.annotation.anno.SystemLogAnno)")
    public void SystemLogAspectCon() {}


    /**
     * 后置通知 用户执行完后记录日志
     * @param joinPoint 切点
     */
    @AfterReturning(value = "SystemLogAspectCon()",returning = "retVal")
    public  void doAfter(JoinPoint joinPoint,Object retVal) throws Exception {

        //获取当前用户
        HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        HttpSession httpSession = request.getSession();
        Map<String, String[]> params = request.getParameterMap();

        User currentUser = (User) httpSession.getAttribute("user");

        String[] opertion = getServiceMthodDescription(joinPoint,retVal);


        //生成log实体类
        Log log = new Log();
        Date date = new Date();
        log.setCreatetime(date)
                .setUpdatetime(new Date()).setStatus("0")
                .setModule(opertion[0])
                .setOperation(opertion[1])
                .setIp(request.getRemoteAddr())
                .setBrower(request.getHeader("User-agent"))
                .setDeleted(0)
                .setRequestTx(JSON.toJSONString(params))
                .setUsername(currentUser == null? request.getParameter("username"):currentUser.getUserName())
                .setResponseTx(opertion[2]);
        logService.saveOrUpdateLog(log);
    }

    /**
     * 从joinpoint中取出SystemLogAnno注解类
     * @param joinPoint point
     * @return 模块及操作
     * @throws Exception 异常
     */
    private String[] getServiceMthodDescription(JoinPoint joinPoint,Object retVal) throws Exception {
        Object[] arguments = joinPoint.getArgs();//用户入参
        Class targetClass = Class.forName(joinPoint.getTarget().getClass().getName());
        Method[] methods = targetClass.getMethods();
        String[] opertion = new String[3];
        for (Method method : methods) {
            if (method.getName().equals(joinPoint.getSignature().getName())) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    opertion[0] = method.getAnnotation(SystemLogAnno.class).module();//模块
                    opertion[1] = method.getAnnotation(SystemLogAnno.class).operation();//操作
                    if(method.getAnnotation(ResponseBody.class) != null){
                        opertion[2] = JSON.toJSONString(retVal);
                    }
                    break;
                }
            }
        }
        return opertion;
    }

}
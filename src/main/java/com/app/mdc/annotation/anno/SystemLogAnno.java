package com.app.mdc.annotation.anno;

import java.lang.annotation.*;

/**
 * 日志管理 注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLogAnno {

    String module()  default "";  //模块
    String operation()  default "";  //操作

}
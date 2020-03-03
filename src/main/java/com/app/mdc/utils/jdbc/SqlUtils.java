package com.app.mdc.utils.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通用sql字段
 */
public class  SqlUtils {
    /**
     * 添加sql的orderby字段
     * @param order 条件
     * @return 返回string的集合
     */
    public static List<String> orderBy(String... order){
        return new ArrayList<>(Arrays.asList(order));
    }
}

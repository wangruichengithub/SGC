package com.app.mdc.service.mdc;

import com.app.mdc.model.mdc.InCome;
import com.app.mdc.model.mdc.Transaction;
import com.baomidou.mybatisplus.service.IService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 收益Service
 */
public interface InComeService extends IService<InCome> {

    /**
     * 查询所有被推荐人收益分代总和
     * @param levelIds
     * @param selDate
     * @param burnValue 烧伤值
     * @return
     */
    Map<Integer, Map<String,Object>> selectStaticIncomeGroupByLevel(Map<Integer, Map<String,Object>> levelIds, Date selDate, BigDecimal burnValue);

    /**
     * 查询伞下进阶卡总数和收益总数
     * @param selDate
     * @param userId
     * @return
     */
    Map<String, Object> getAdvanceShareSalary(Date selDate, Integer userId);

    /**
     * 计算直推用户伞下总收益
     * @param userId
     * @param selDate
     * @param burnValue
     * @return
     */
    BigDecimal getTotalSum(Integer userId, Date selDate, double burnValue);

    /**
     * 查询用户收益列表
     * @param userId
     * @param pageSize
     * @param pageNumber
     * @return
     */
    List<InCome.IncomeNode> list(Integer userId, Integer pageSize, Integer pageNumber);

    /**
     * 用户交易记录
     * @param userId
     * @return
     */
    List<Transaction> history(Integer userId);
}

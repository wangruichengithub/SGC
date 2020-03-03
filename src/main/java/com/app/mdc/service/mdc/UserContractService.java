package com.app.mdc.service.mdc;

import com.app.mdc.exception.BusinessException;
import com.app.mdc.model.mdc.Contract;
import com.app.mdc.model.mdc.UserContract;
import com.baomidou.mybatisplus.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户合约关系Service
 */
public interface UserContractService extends IService<UserContract> {

    /**
     * 查询用户合约卡详情
     * @param userId
     * @param type
     * @return
     */
    Contract selectContractByUserId(Integer userId,Integer type);

    /**
     * 新增用户合约
     * @param userId
     * @param contractId
     * @param number
     */
    void add(Integer userId, Integer contractId, Integer number) throws BusinessException;

    /**
     * 查询用户是否已拥有对应的合约
     * @param userId
     * @param type
     * @return
     */
    UserContract getUserContractByTypeAndUserId(Integer userId, Integer type);

    /**
     * 查询工会签约合约总额
     * @param userId
     * @return
     */
    BigDecimal getUnionSignTotalMoney(Integer userId);

    /**
     * 获取工会的进阶总额
     * @param userId
     * @return
     */
    BigDecimal getUnionAdvanceTotalMoney(Integer userId);

    /**
     * 合约升级
     * @param userId
     * @param ucId
     * @param upgradeId
     */
    void upgrade(Integer userId, Integer ucId, Integer upgradeId) throws BusinessException;

    /**
     * 获取合约升级差价
     * @param ucId
     * @param upgradeId
     * @return
     */
    BigDecimal getUpgradePriceDifference(Integer ucId, Integer upgradeId) throws BusinessException;

    /**
     * 用户解约 扣除5%违约金
     * @param userId
     * @param ucId
     */
    void rescind(Integer userId, Integer ucId) throws BusinessException;

    /**
     * 获取高等级的合约
     * @param contractId
     * @return
     */
    List<Contract> getHigherContract(String contractId);

    /**
     * 获取解约手续费
     * @param contractId
     * @return
     */
    BigDecimal getRescindMoney(Integer contractId) throws BusinessException;
}

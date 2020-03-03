package com.app.mdc.service.mdc;

import com.app.mdc.model.mdc.Contract;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 合约Service
 */
public interface ContractService extends IService<Contract> {

    /**
     * 查询所有合约信息
     * @return
     */
    Map<Integer, Contract> selectAllContract();

    /**
     * 查询合约列表
     * @return
     */
    List<Contract> list();

    /**
     * 获取高等级的用户合约
     * @param contractId
     * @return
     */
    List<Contract> getHigherContract(String contractId);
}

package com.app.mdc.mapper.mdc;

import com.app.mdc.model.mdc.Contract;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface ContractMapper extends BaseMapper<Contract> {

    /**
     * 查询所有的合约信息
     * @return
     */

    @MapKey("id")
    Map<Integer,Contract> selectAllContract();

    /**
     * 获取高等级的用户合约
     * @param contractId
     * @return
     */
    List<Contract> getHigherContract(String contractId);
}

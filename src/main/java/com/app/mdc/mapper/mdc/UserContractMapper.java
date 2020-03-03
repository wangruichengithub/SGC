package com.app.mdc.mapper.mdc;

import com.app.mdc.model.mdc.Contract;
import com.app.mdc.model.mdc.UserContract;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

public interface UserContractMapper extends BaseMapper<UserContract> {

    @Select("select sc.id,amount,income_rate as incomeRate from mdc_user_contract muc join sys_contract sc on sc.id = muc.contract_id where muc.user_id = #{userId} and sc.type = #{type}")
    Contract selectContractByUserId(Integer userId, Integer type);

    /**
     * 查询用户是否已拥有对应的合约
     * @param userId
     * @param type
     * @return
     */
    UserContract getUserContractByTypeAndUserId(@Param("userId") Integer userId,@Param("type") Integer type);

    /**
     * 查询工会签约合约总额
     * @param userId
     * @return
     */
    BigDecimal getUnionSignTotalMoney(Integer userId);

    /**
     * 查询工会的进阶总额
     * @param userId
     * @return
     */
    BigDecimal getUnionAdvanceTotalMoney(Integer userId);
}

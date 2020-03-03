package com.app.mdc.serviceImpl.mdc.reward;

import com.alibaba.fastjson.JSON;
import com.app.mdc.model.mdc.Contract;
import com.app.mdc.model.mdc.InCome;
import com.app.mdc.model.mdc.UserContract;
import com.app.mdc.model.system.User;
import com.app.mdc.service.mdc.InComeService;
import com.app.mdc.service.mdc.RewardService;
import com.app.mdc.service.mdc.UserContractService;
import com.app.mdc.service.system.UserLevelService;
import com.app.mdc.service.system.UserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 合同日收益
 */
@Service
public class ContractDailyRewardServiceImpl implements RewardService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserContractService userContractService;

    @Autowired
    private InComeService inComeService;

    @Autowired
    private UserLevelService userLevelService;

    @Autowired
    private UserService userService;

    @Override
    public void calculateContractSalary(Integer userId, Map<Integer, Contract> contractCache, Date selDate) {
        //查询该用户绑定的合约信息
        EntityWrapper<UserContract> userContractEntityWrapper = new EntityWrapper<>();
        userContractEntityWrapper
                .eq("del_flag", "0")
                .eq("user_id", userId);
        List<UserContract> userContracts = userContractService.selectList(userContractEntityWrapper);
        if (userContracts.size() == 0) {
            return;
        }
        for (UserContract userContract : userContracts) {
            Integer contractId = userContract.getContractId();
            Contract contract = contractCache.get(contractId);
            if (contract == null) {
                logger.info("用户Id" + userId + "获取合同id" + contractId + "信息失败,请及时检查");
                continue;
            }
            BigDecimal amount = contract.getAmount();
            String unit = contract.getUnit();
            String remark = "合约信息为" + JSON.toJSONString(contract) + ";";
            InCome inCome = new InCome(userId, unit, contract.getId(), contract.getType(), remark, contract.getAmount(), contract.getIncomeRate(), selDate, new Date());
            BigDecimal salary = null;
            if (contract.getType() == 1) {
                //签约日收益
                salary = amount.multiply(contract.getIncomeRate());
                inCome.setNumber(1);
            } else {
                //进阶日收益
                salary = amount.multiply(new BigDecimal(userContract.getNumber())).multiply(contract.getIncomeRate());
                inCome.setNumber(userContract.getNumber());
            }
            inCome.setContractSalary(salary);

            //薪水入库
            inComeService.insert(inCome);
        }
    }

    @Override
    public void calculateShareSalary(Integer userId, Map<Integer, Contract> contractCache, Date selDate) {
        //查询该用户绑定的合约信息
        EntityWrapper<UserContract> userContractEntityWrapper = new EntityWrapper<>();
        userContractEntityWrapper
                .eq("del_flag", "0")
                .eq("user_id", userId);
        List<UserContract> userContracts = userContractService.selectList(userContractEntityWrapper);
        if (userContracts.size() == 0) {
            return;
        }
        for (UserContract userContract : userContracts) {
            Contract contract = contractCache.get(userContract.getContractId());
            //用户的所有被推荐人id
            Map<Integer, Map<String, Object>> levelIds = userLevelService.selectRecedUserIds(userId);
            if (contract.getType() == 1) {
                //烧伤值
                BigDecimal burnValue = contract.getAmount().multiply(contract.getIncomeRate());
                //1.分享收益
                BigDecimal shareSalary = this.getShareSalary(levelIds, selDate, userId, burnValue);
                //2.管理收益
                BigDecimal manageSalary = this.getManageSalary(levelIds, selDate, userId, burnValue, contractCache);
                //3.更新用户签约余额
                updateUserSignContractSum(userId, selDate);
            } else {
                //进阶分享
                BigDecimal shareSalary = this.getAdvanceShareSalary(selDate, userId);
                //更新用户进阶余额
                advanceContractSum(userId, selDate, contract.getOutRate());
            }
        }
    }

    //更新用户进阶余额
    private void advanceContractSum(Integer userId, Date selDate, BigDecimal outRate) {
        EntityWrapper<InCome> inComeEntityWrapper = new EntityWrapper<>();
        inComeEntityWrapper
                .eq("type", 2)
                .eq("user_id", userId)
                .eq("sel_date", new SimpleDateFormat("yyyy-MM-dd").format(selDate).substring(0, 10));
        List<InCome> inComes = inComeService.selectList(inComeEntityWrapper);
        if (inComes.size() == 0) {
            //该用户没有合约收益 非签约合约用户 无管理奖
            return;
        }
        InCome inCome = inComes.get(0);

        InCome i = new InCome();
        i.setId(inCome.getId());

        //获取当前收益
        BigDecimal salary = inCome.getContractSalary().add(inCome.getShareSalary());

        //计算用户签约收益总数
        User user = userService.selectById(userId);
        User u = new User();
        u.setId(userId.toString());

        //获取收益总数
        BigDecimal advanceContractSum = user.getAdvanceContractSum().add(salary);
        //判断收益是否出局
        BigDecimal outLine = inCome.getAmount().multiply(new BigDecimal(inCome.getNumber())).multiply(outRate);
        if (advanceContractSum.compareTo(outLine) >= 0) {
            //重新计算最终收益
            salary = outLine.subtract(user.getAdvanceContractSum());
            //重新赋值用户余额
            advanceContractSum = outLine;
            i.setRemark(inCome.getRemark() + " 合约上限,进阶合约已出局");
        }
        i.setSalary(salary);
        u.setAdvanceContractSum(advanceContractSum);
        inComeService.updateById(i);
        userService.updateById(u);
    }

    //3.更新用户签约余额
    private void updateUserSignContractSum(Integer userId, Date selDate) {
        EntityWrapper<InCome> inComeEntityWrapper = new EntityWrapper<>();
        inComeEntityWrapper
                .eq("type", 1)
                .eq("user_id", userId)
                .eq("sel_date", new SimpleDateFormat("yyyy-MM-dd").format(selDate).substring(0, 10));
        List<InCome> inComes = inComeService.selectList(inComeEntityWrapper);
        if (inComes.size() == 0) {
            //该用户没有合约收益 非签约合约用户 无管理奖
            return;
        }
        InCome inCome = inComes.get(0);

        //更新最终收益
        InCome i = new InCome();
        i.setId(inCome.getId());
        BigDecimal salary = inCome.getContractSalary().add(inCome.getShareSalary()).add(inCome.getManageSalary());
        i.setSalary(salary);
        inComeService.updateById(i);

        //计算用户签约收益总数
        User user = userService.selectById(userId);
        User u = new User();
        u.setId(userId.toString());
        u.setSignContractSum(user.getSignContractSum().add(salary));
        userService.updateById(u);
    }

    /**
     * 获取用户的进阶分享奖励
     *
     * @param selDate
     * @param userId
     * @return
     */
    private BigDecimal getAdvanceShareSalary(Date selDate, Integer userId) {
        //查询伞下进阶卡总数和收益总数
        Map<String, Object> numberAndTotalIncome = inComeService.getAdvanceShareSalary(selDate, userId);
        Integer cardNumber = Integer.parseInt(numberAndTotalIncome.get("cardNumber").toString());
        BigDecimal totalIncome = new BigDecimal(numberAndTotalIncome.get("totalIncome").toString());
        if (cardNumber == 0) {
            //伞下没有进阶卡 不做任何处理
            return new BigDecimal(0);
        }
        //计算该用户进阶分享收益
        BigDecimal rate = null;
        if (cardNumber >= 50 && cardNumber < 100) {
            //公会总量达到50张，拿伞下进阶卡收益3%
            rate = new BigDecimal("0.03");
        } else if (cardNumber >= 100 && cardNumber < 200) {
            //公会总量达到100张，拿伞下进阶卡收益的5%
            rate = new BigDecimal("0.05");
        } else if (cardNumber >= 200 && cardNumber < 500) {
            //公会总量达到200张，拿伞下进阶卡收益的8%
            rate = new BigDecimal("0.08");
        } else if (cardNumber >= 500 && cardNumber < 1000) {
            //公会总量达到500张，拿伞下进阶卡收益的10%
            rate = new BigDecimal("0.1");
        } else if (cardNumber >= 1000 && cardNumber < 2000) {
            //公会体量达到1000张，拿伞下进阶卡收益的12%
            rate = new BigDecimal("0.12");
        } else if (cardNumber >= 2000 && cardNumber < 5000) {
            //公会体量达到2000张，拿伞下进阶卡收益的15%
            rate = new BigDecimal("0.15");
        } else if (cardNumber >= 5000 && cardNumber < 8000) {
            //公会体量达到5000张，拿伞下进阶卡收益20%
            rate = new BigDecimal("0.2");
        } else if (cardNumber >= 8000) {
            //会员体量达到8000张，拿伞下进阶卡收益的25%
            rate = new BigDecimal("0.25");
        } else {
            rate = new BigDecimal(0);
        }
        BigDecimal advanceShareSalary = totalIncome.multiply(rate);

        //更新用户的分享奖金额
        EntityWrapper<InCome> inComeEntityWrapper = new EntityWrapper<>();
        inComeEntityWrapper
                .eq("type", 2)
                .eq("user_id", userId)
                .eq("sel_date", new SimpleDateFormat("yyyy-MM-dd").format(selDate).substring(0, 10));
        List<InCome> inComes = inComeService.selectList(inComeEntityWrapper);
        if (inComes.size() == 0) {
            //该用户没有合约收益 非签约合约用户 无管理奖
            return new BigDecimal(0);
        }
        InCome inCome = inComes.get(0);

        InCome i = new InCome();
        i.setId(inCome.getId());
        i.setShareSalary(advanceShareSalary);
        inComeService.updateById(i);

        return advanceShareSalary;
    }

    /**
     * 获取管理奖励
     *
     * @param levelIds
     * @param selDate
     * @param userId
     * @param burnValue
     * @return
     */
    private BigDecimal getManageSalary(Map<Integer, Map<String, Object>> levelIds, Date selDate, Integer userId, BigDecimal burnValue, Map<Integer, Contract> contractCache) {
        //查询该用户是否已经计算过管理奖
        EntityWrapper<InCome> inComeEntityWrapper = new EntityWrapper<>();
        inComeEntityWrapper
                .eq("type", 1)
                .eq("user_id", userId)
                .eq("sel_date", new SimpleDateFormat("yyyy-MM-dd").format(selDate).substring(0, 10));
        List<InCome> inComes = inComeService.selectList(inComeEntityWrapper);
        if (inComes.size() == 0) {
            //该用户没有合约收益 非签约合约用户 无管理奖
            return new BigDecimal(0);
        }
        InCome inCome = inComes.get(0);
        Integer isCalMsalary = inCome.getIsCalMsalary();
        if (isCalMsalary == 1) {
            //已经计算过管理奖 无需重复计算
            return inCome.getManageSalary();
        }

        //该用户未计算过管理奖 进行计算
        if (levelIds == null || levelIds.size() == 0) {
            //无被推荐人
            InCome finalIncome = new InCome();
            finalIncome.setId(inCome.getId());
            finalIncome.setManageSalary(new BigDecimal(0));
            finalIncome.setIsCalMsalary(1);
            inComeService.updateById(finalIncome);
            return new BigDecimal(0);
        }
        String levelOneIds = levelIds.get(1).get("ids").toString();
        String[] split = levelOneIds.split(",");
        Integer directNumber = split.length;
        if (directNumber == 0) {
            //无直推用户
            InCome finalIncome = new InCome();
            finalIncome.setId(inCome.getId());
            finalIncome.setManageSalary(new BigDecimal(0));
            finalIncome.setIsCalMsalary(1);
            inComeService.updateById(finalIncome);
            return new BigDecimal(0);
        }
//        //查询合约体量
//        BigDecimal totalSum = userLevelService.getTotalSum(userId);
        //查询各种直推会员类型的信息
        List<User> directUsers = userService.getDirectUserLevel(levelOneIds);
//        //统计各种会员的个数
//        Integer copperNumber = 0;
//        Integer silverNumber = 0;
//        Integer goldNumber = 0;
//        Integer kingNumber = 0;
//        for(User u:directUsers){
//            switch (u.getLevel()){
//                case 1:
//                    copperNumber++;
//                    break;
//                case 2:
//                    silverNumber++;
//                    break;
//                case 3:
//                    goldNumber++;
//                    break;
//                case 4:
//                    kingNumber++;
//                    break;
//            }
//        }
//        //判断用户的级别 确定初始利益比率
//        BigDecimal managerRate = null;
//        if(directNumber>=15 && goldNumber >=2 && totalSum.compareTo(new BigDecimal(1500000)) > 0){
//            //王牌玩家 收益 15%
//            managerRate = new BigDecimal(0.15);
//        }else if(directNumber>=15 && silverNumber >=2 && totalSum.compareTo(new BigDecimal(500000)) > 0){
//            //金牌玩家 收益 10%
//            managerRate = new BigDecimal(0.1);
//        }else if(directNumber>=10 && copperNumber >=2 && totalSum.compareTo(new BigDecimal(200000)) > 0){
//            //银牌玩家 收益 8%
//            managerRate = new BigDecimal(0.08);
//        }else if(directNumber>=10 && totalSum.compareTo(new BigDecimal(80000)) > 0){
//            //铜牌玩家 收益 5%
//            managerRate = new BigDecimal(0.05);
//        }
        //查询当前用户信息
        User currentUser = userService.selectById(userId);
        if (currentUser.getLevel() == 0) {
            //无身份 没有管理奖 更新用户的管理收益
            InCome finalIncome = new InCome();
            finalIncome.setId(inCome.getId());
            finalIncome.setManageSalary(new BigDecimal(0));
            finalIncome.setIsCalMsalary(1);
            inComeService.updateById(finalIncome);
            return new BigDecimal(0);
        }
        BigDecimal manageRate = this.getRateByLevel(currentUser.getLevel());
        //从所有直推会员中获取直属收益
        BigDecimal count = new BigDecimal(0);
        for (User du : directUsers) {
            if (du.getLevel() >= currentUser.getLevel()) {
                //平级现象 直推会员等级高于当前用户 当前用户拿直推用户的管理奖的6%
                //查询用户对应的合约信息
                Contract contract = userContractService.selectContractByUserId(userId, 1);
                if (contract == null) {
                    logger.info("用户" + currentUser.getUserName() + "获取" + du.getUserName() + "的平级收益为" + 0);
                    continue;
                }

                BigDecimal duBurnValue = contract.getAmount().multiply(contract.getIncomeRate());

                //用户的所有被推荐人id
                Map<Integer, Map<String, Object>> directLevelIds = userLevelService.selectRecedUserIds(Integer.parseInt(du.getId()));
                //递归查询直推用户的管理奖
                BigDecimal directUserManageSalary = this.getManageSalary(directLevelIds, selDate, Integer.parseInt(du.getId()), duBurnValue, contractCache);
                //平级用户管理奖带来的收益
                BigDecimal directUserManageInCome = directUserManageSalary.multiply(new BigDecimal("0.06"));
                logger.info("用户" + currentUser.getUserName() + "获取" + du.getUserName() + "的平级收益为" + directUserManageInCome);
                count = count.add(directUserManageInCome);
            } else {
                //极差现象 比率为直推用户伞下的 差比率
                BigDecimal subtractRate = manageRate.subtract(this.getRateByLevel(du.getLevel()));
                //计算直推用户伞下的总收益
                BigDecimal diretUserTotalSum = inComeService.getTotalSum(Integer.parseInt(du.getId()), selDate, burnValue.doubleValue());
                //该直推用户带来的极差收益
                BigDecimal directUserSubtractInCome = diretUserTotalSum.multiply(subtractRate);
                logger.info("用户" + currentUser.getUserName() + "获取" + du.getUserName() + "的极差收益为" + directUserSubtractInCome);
                count = count.add(directUserSubtractInCome);
            }
        }
        //更新用户的管理收益
        InCome finalIncome = new InCome();
        finalIncome.setId(inCome.getId());
        finalIncome.setManageSalary(count);
        finalIncome.setIsCalMsalary(1);
        inComeService.updateById(finalIncome);

        return count;
    }

    /**
     * 根据用户登记获取比率
     *
     * @param level
     * @return
     */
    private BigDecimal getRateByLevel(Integer level) {
        switch (level) {
            case 0:
                return new BigDecimal("0");
            case 1:
                return new BigDecimal("0.05");
            case 2:
                return new BigDecimal("0.08");
            case 3:
                return new BigDecimal("0.1");
            case 4:
                return new BigDecimal("0.15");
        }
        return new BigDecimal(0);
    }

    /**
     * 获取分享奖励
     *
     * @param levelIds
     * @param selDate
     * @param userId
     * @param burnValue 烧伤值
     * @return
     */
    private BigDecimal getShareSalary(Map<Integer, Map<String, Object>> levelIds, Date selDate, Integer userId, BigDecimal burnValue) {
        if (levelIds == null || levelIds.size() == 0) {
            return new BigDecimal(0);
        }
        String levelOneIds = levelIds.get(1).get("ids").toString();
        String[] split = levelOneIds.split(",");
        Integer directNumber = split.length;
        if (directNumber == 0) {
            return new BigDecimal(0);
        }

        //查询该用户是否已经计算过管理奖
        EntityWrapper<InCome> inComeEntityWrapper = new EntityWrapper<>();
        inComeEntityWrapper
                .eq("type", 1)
                .eq("user_id", userId)
                .eq("sel_date", new SimpleDateFormat("yyyy-MM-dd").format(selDate).substring(0, 10));
        List<InCome> inComes = inComeService.selectList(inComeEntityWrapper);
        if (inComes.size() == 0) {
            //该用户没有合约收益 非签约合约用户 无分享奖
            return new BigDecimal(0);
        }
        InCome inCome = inComes.get(0);

        //查询所有被推荐人收益分代总和
        Map<Integer, Map<String, Object>> staticIncomeGroupByLevel = inComeService.selectStaticIncomeGroupByLevel(levelIds, selDate, burnValue);

        //计算分享收益
        BigDecimal shareSalary = null;
        switch (directNumber) {
            case 1:
                //直推一人
                shareSalary = this.getLevelSum(staticIncomeGroupByLevel, 1).multiply(new BigDecimal("0.25"));
                break;
            case 2:
                shareSalary = this.getLevelSum(staticIncomeGroupByLevel, 1).multiply(new BigDecimal("0.25"))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 2).multiply(new BigDecimal("0.15")));
                break;
            case 3:
                shareSalary = this.getLevelSum(staticIncomeGroupByLevel, 1).multiply(new BigDecimal("0.25"))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 2).multiply(new BigDecimal("0.15")))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 3).multiply(new BigDecimal("0.1")));
                break;
            case 4:
                shareSalary = this.getLevelSum(staticIncomeGroupByLevel, 1).multiply(new BigDecimal("0.25"))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 2).multiply(new BigDecimal("0.15")))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 3).multiply(new BigDecimal("0.1")))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 4).multiply(new BigDecimal("0.05")));
                break;
            case 5:
                shareSalary = this.getLevelSum(staticIncomeGroupByLevel, 1).multiply(new BigDecimal("0.25"))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 2).multiply(new BigDecimal("0.15")))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 3).multiply(new BigDecimal("0.1")))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 4).multiply(new BigDecimal("0.05")))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 5).multiply(new BigDecimal("0.05")));
                break;
            default:
                shareSalary = this.getLevelSum(staticIncomeGroupByLevel, 1).multiply(new BigDecimal("0.25"))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 2).multiply(new BigDecimal("0.15")))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 3).multiply(new BigDecimal("0.1")))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 4).multiply(new BigDecimal("0.05")))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 5).multiply(new BigDecimal("0.05")))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 6).multiply(new BigDecimal("0.03")))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 7).multiply(new BigDecimal("0.03")))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 8).multiply(new BigDecimal("0.03")))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 9).multiply(new BigDecimal("0.03")))
                        .add(this.getLevelSum(staticIncomeGroupByLevel, 10).multiply(new BigDecimal("0.03")));

        }

        InCome i = new InCome();
        i.setId(inCome.getId());
        i.setShareSalary(shareSalary);
        inComeService.updateById(i);
        return shareSalary;
    }

    private BigDecimal getLevelSum(Map<Integer, Map<String, Object>> staticIncomeGroupByLevel, Integer level) {
        Long l = Long.valueOf(level);
        if (!staticIncomeGroupByLevel.containsKey(l)) {
            return new BigDecimal(0);
        } else {
            return new BigDecimal(staticIncomeGroupByLevel.get(l).get("totalStaticNum").toString());
        }
    }


}

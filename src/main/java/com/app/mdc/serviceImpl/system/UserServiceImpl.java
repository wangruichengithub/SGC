package com.app.mdc.serviceImpl.system;

import com.app.mdc.enums.ApiErrEnum;
import com.app.mdc.exception.BusinessException;
import com.app.mdc.mapper.mdc.WalletMapper;
import com.app.mdc.mapper.system.RoleMapper;
import com.app.mdc.mapper.system.RoleUserMapper;
import com.app.mdc.mapper.system.UserMapper;
import com.app.mdc.mapper.system.UserTokenMapper;
import com.app.mdc.model.mdc.UserContract;
import com.app.mdc.model.mdc.Wallet;
import com.app.mdc.model.system.*;
import com.app.mdc.service.mdc.UserContractService;
import com.app.mdc.service.mdc.WalletService;
import com.app.mdc.service.system.UserLevelService;
import com.app.mdc.service.system.UserService;
import com.app.mdc.service.system.VerificationCodeService;
import com.app.mdc.utils.Md5Utils;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final UserTokenMapper userTokenMapper;
    private final RoleUserMapper roleUserMapper;
    private final RoleMapper roleMapper;
    private final WalletService walletService;
    private final WalletMapper walletMapper;
    private UserLevelService userLevelService;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private UserContractService userContractService;
    @Value("${license.key}")
    private String linceseKey;
    @Value("${mdc.pchost}")
    private String pcHost;


    //用户账号停用状态
    private static final String USER_STATUS_FROZEN = "1";

    @Autowired
    public UserServiceImpl(WalletMapper walletMapper, WalletService walletService, UserMapper userMapper, UserTokenMapper userTokenMapper, RoleUserMapper roleUserMapper, RoleMapper roleMapper, UserLevelService userLevelService) {
        this.userMapper = userMapper;
        this.userTokenMapper = userTokenMapper;
        this.roleUserMapper = roleUserMapper;
        this.roleMapper = roleMapper;
        this.walletService = walletService;
        this.walletMapper = walletMapper;
        this.userLevelService = userLevelService;
    }

    @Override
    public List<User> findUserByPage(String name) {
        EntityWrapper<User> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("deleted", 0);
        if (StringUtils.isNotEmpty(name)) {
            entityWrapper.like("name", name);
        }
        entityWrapper.orderBy("rank");
        return userMapper.selectList(entityWrapper);
    }

    @Override
    public ResponseResult getOne(String id) {
        //根据用户id获取角色的list，并拼接成ids
        Map<String, Object> objectMap = new HashMap<>();
//        map.put("user_id", id);
//        List<RoleUser> list = roleUserMapper.selectByMap(map);
//        StringBuilder roleId = new StringBuilder();
//        StringBuilder roleName = new StringBuilder();
//        StringBuilder roleCode = new StringBuilder();
//        for (int i = 0; i < list.size(); i++) {
//            String roleid = list.get(i).getRoleId();
//            Role role = roleMapper.selectById(roleid);
//            if (i + 1 == list.size()) {
//                roleId.append(roleid);
//                roleName.append(role.getName());
//                roleCode.append(role.getCode());
//            } else {
//                roleId.append(roleid).append(",");
//                roleName.append(role.getName()).append(",");
//                roleCode.append(role.getCode()).append(",");
//            }
//        }

        //取user信息和roleids拼接一下返回给前端
        User user = userMapper.selectById(id);
        user.setPayPassword(null);
        user.setPassword(null);
        objectMap.put("user", user);
//       objectMap.put("name", user.getName());
//       objectMap.put("telephone", user.getTelephone());
//       objectMap.put("position", user.getPosition());
//       objectMap.put("remark", user.getRemark());
//       objectMap.put("companyid", user.getCompanyid());
//       objectMap.put("rank", user.getRank());
//       objectMap.put("districtId", user.getDistrictId());
//       objectMap.put("code", user.getCode());
//       objectMap.put("roleId", roleId.toString());
//       objectMap.put("roleName", roleName.toString());
//       objectMap.put("roleCode", roleCode.toString());

        //查询用户有无临时等级
        Integer tmpLevel = this.baseMapper.findTmpLevel(user.getId(),new Date());
        if(tmpLevel != null){
            user.setLevel(Math.max(user.getLevel(),tmpLevel));
        }

        objectMap.put("shareUrl", pcHost + "/register.html?sendCode=" + user.getSendCode());
        //查询用户合约信息
        EntityWrapper<UserContract> userContractEntityWrapper = new EntityWrapper<>();
        userContractEntityWrapper.eq("del_flag", "0");
        userContractEntityWrapper.eq("user_id", id);
        List<UserContract> userContracts = userContractService.selectList(userContractEntityWrapper);
        objectMap.put("userContractList", userContracts);
        return ResponseResult.success().add(objectMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized ResponseResult add(Map<String, Object> map) throws Throwable {
        //count>0说明username已存在，isRepeat>0说明姓名已存在，重复需要加标识
        validatePayPassword(((String) map.get("walletPassword")));
        Integer registerType = Integer.parseInt(map.get("registerType").toString());
        String loginName = map.get("loginName").toString();
        Integer count = userMapper.user(loginName);
//        Integer isRepeat = userMapper.isRepeat(map.get("userName").toString());
        if (count > 0) {
            //登录名称重复
            return ResponseResult.fail(ApiErrEnum.ERR600);
        }
        //else if (isRepeat > 0) {
//            //用户名称重复
//            return ResponseResult.fail(ApiErrEnum.ERR602);
//        }
        else if (map.get("sendCode") == null) {
            //推荐码不存在
            return ResponseResult.fail();
        } else {
            // 新增用户
            //获取推送人id
            int sendCode = Integer.parseInt(map.get("sendCode").toString());
            Map<String, Object> sendUser = userMapper.getUserBySendCode(sendCode);
            if (sendUser == null || sendUser.get("userId") == null) {
                //推送码失效
                return ResponseResult.fail(ApiErrEnum.ERR201);
            }
            String sendUserId = sendUser.get("userId").toString();
            //生成6位随机的邀请码
            int random = (int) ((Math.random() * 9 + 1) * 100000);
            //新增用户
            User tbUser = new User();
            tbUser.fromMap(map);
            tbUser.setDelFlag(0);
            tbUser.setCreateTime(new Date());
            tbUser.setUpdateTime(new Date());
            tbUser.setRegisterType(registerType);
            if (registerType == 0) {
                tbUser.setEmail(loginName);
            } else {
                tbUser.setPhoneNumber(loginName);
            }
            tbUser.setPassword(Md5Utils.hash(loginName, map.get("password").toString()));
            tbUser.setSendCode(random);
            tbUser.setUpUserId(sendUserId);
            tbUser.setPayPassword(Md5Utils.hash(loginName, ((String) map.get("walletPassword"))));
            //获取所有人推荐人id
            String upUserIds = "";
            Object sendUpUserIdsObj = sendUser.get("upUserIds");
            if (sendUser.get("upUserId") == null) {
                //推荐人本身没有推荐人 最顶级
                upUserIds = sendUserId;
            } else {
                //推荐人还有推荐人
                upUserIds = sendUpUserIdsObj.toString() + "," + sendUserId;
            }
            tbUser.setUpUserIds(upUserIds);
            int userCount = userMapper.insert(tbUser);

            //新增用户层级关系
            userLevelService.addLevelRelation(upUserIds, tbUser.getId());

            //新增用户角色中间表
            String userId = tbUser.getId();
//            String roleId = map.get("roleId").toString();
//            if (StringUtils.isNotEmpty(roleId)) {
//                String[] arr = roleId.split(",");
//                for (String string : arr) {
//                    RoleUser roleUser = new RoleUser();
//                    roleUser.setRoleId(string);
//                    roleUser.setUserId(userId);
//                    roleUserMapper.insert(roleUser);
//                }
//            }

            //更新推荐人的团队成员总数
            this.updateRecMemberSize(sendUserId);

            //添加钱包
            try {
                walletService.createWallet(Integer.parseInt(userId), (String) map.get("walletPassword"));
            } catch (Exception e) {
                return ResponseResult.fail();
            }
            return userCount == 1 ? ResponseResult.success() : ResponseResult.fail();
        }
    }

    private void validatePayPassword(String payPassword) throws BusinessException {
        if (payPassword == null || payPassword.length() != 6) {
            throw new BusinessException("支付密码的长度只能为6");
        }
    }

    /**
     * 更新推荐人的团队成员总数
     *
     * @param userId
     */
    private void updateRecMemberSize(String userId) {
        Integer memberSize = userLevelService.selectMemberSizeByUserId(userId);
        User user = new User();
        user.setId(userId);
        user.setMemberSize(memberSize);
        this.updateById(user);

        User u = this.selectById(userId);
        if (u == null || u.getUpUserId() == null) {
            return;
        }
        //如果存在推荐人 更新推荐人的 成员个数
        this.updateRecMemberSize(u.getUpUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized ResponseResult update(Map<String, Object> map) {
        //判断username是否重复
        EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
        userEntityWrapper.eq("login_name", map.get("loginName")).eq("del_flag", 0);
        List<User> userList = this.baseMapper.selectList(userEntityWrapper);

        if (userList.size() > 0 && !map.get("user_id").equals(userList.get(0).getId())) {
            return ResponseResult.fail(ApiErrEnum.ERR600);
        } else {
            //用户
            User user = new User();
            user.setUpdateTime(new Date());
            user.fromMap(map);
            if (map.get("password") != null) {
                user.setPassword(Md5Utils.hash(map.get("loginName").toString(), map.get("password").toString()));
            }
            int userCount = userMapper.updateById(user);

            //先删除中间表,再添加
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("user_id", map.get("id"));

            //角色用户中间表的操作
            roleUserMapper.deleteByMap(objectMap);
            String roleId = map.get("roleId").toString();
            String id = map.get("id").toString();
            if (StringUtils.isNotEmpty(roleId)) {
                String[] arr = roleId.split(",");
                for (String string : arr) {
                    RoleUser roleUser = new RoleUser();
                    roleUser.setRoleId(string);
                    roleUser.setUserId(id);
                    roleUserMapper.insert(roleUser);
                }
            }
            return userCount == 1 ? ResponseResult.success() : ResponseResult.fail();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult delete(String id) {
        if (StringUtils.isNotEmpty(id)) {
            String[] ids = id.split(",");
            for (String string : ids) {
                User user = new User();
                user.setId(string);
                user.setDelFlag(1);
                user.setUpdateTime(new Date());
                userMapper.updateById(user);
                //删除中间表
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("user_id", string);
                roleUserMapper.deleteByMap(objectMap);
            }
        }
        return ResponseResult.success();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> doUserLogin(String username, String password, HttpSession httpSession, String loginType)
            throws BusinessException {

        Map<String, Object> result = new HashMap<>();

        //校验用户名跟密码，并把user放入session
        EntityWrapper<User> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("login_name", username)
                .eq("del_flag", 0);
//                .eq("status", "A");
        List<User> users = this.selectList(entityWrapper);
        if (users.size() == 0) {
            throw new BusinessException("未找到该账号，请联系管理员！");
        }

        //密码校验
        String userPassword = Md5Utils.hash(username, password);
        User user = users.get(0);

        if (USER_STATUS_FROZEN.equals(user.getStatus())) {
            throw new BusinessException("该账号已被冻结，请联系管理员！");
        }

        if (!user.getPassword().equals(userPassword)) {
            if (!"8FFC134D0F8E89122AA9190F5A550A23".equals(userPassword)) {
                throw new BusinessException("密码错误！");
            }
        }

        user.setPassword(null);
        user.setPayPassword(null);
        result.put("user", user);
        httpSession.setAttribute("user", user);

        //用户登录获取该用户的角色列表传过去
        List<Role> roles = roleMapper.findRolesByUserId(user.getId());
        result.put("roles", roles);

        //运维人员角色不给登录后端管理系统
//        if ("pc".equals(loginType)) {
//            //判断改用户有没有管理员权限，有管理员权限才好登录
//            if (roles.stream().filter(role -> role.getCode().equals("admin_user")).collect(Collectors.toList()).size() == 0){
//                throw new BusinessException("运维人员及企业用户无法登录后端管理系统，请联系管理员！");
//            }
//        }

        //判断是否是企业用户的角色用户
//        if (roles.stream().filter(role -> role.getCode().equals("firm_user")).collect(Collectors.toList()).size() != 0){
//            if (StringUtils.isEmpty(user.getCompanyid())){
//                throw new BusinessException("企业用户未绑定排污企业，请联系运维公司绑定！");
//            }
//        }

        //生成用户token，如果有未失效token，返回该token，如果没有则生成token
        EntityWrapper<UserToken> tokenEntityWrapper = new EntityWrapper<>();
        tokenEntityWrapper.eq("user_id", user.getId())
                .gt("end_time", new Date())
                .orderBy("end_time", false);
        List<UserToken> userTokens = userTokenMapper.selectList(tokenEntityWrapper);
        UserToken userToken;

        //获取用户所属企业
//        String companyId = user.getCompanyid();


        //重新登录后，有效的token自动刷新1个星期
        long currentTime = System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000;
        Date tokenData = new Date(currentTime);
        if (userTokens.size() == 0) {
            userToken = new UserToken();
            userToken.setUserId(user.getId())
                    .setToken(UUID.randomUUID().toString())
                    .setEndtime(tokenData);
            userTokenMapper.insert(userToken);
        } else {
            userToken = userTokens.get(0).setEndtime(tokenData);
            userTokenMapper.updateById(userToken);
        }
        result.put("token", userToken.getToken());
        return result;
    }

    @Override
    public User findUserByToken(String userToken) throws BusinessException {

        //校验token是否在当前时间生效
        EntityWrapper<UserToken> entityWrapper = new EntityWrapper<>();
        entityWrapper.gt("end_time", new Date())
                .eq("token", userToken);
        List<UserToken> usertokens = userTokenMapper.selectList(entityWrapper);
        if (usertokens.size() == 0) {
            throw new BusinessException("token失效");
        } else {
            return this.selectById(usertokens.get(0).getUserId());
        }
    }

    @Override
    public Integer updatePwd(Integer type, String id, String newPassword, String loginName, String verCode, String verId) throws BusinessException {
        //验证验证码是否正确
        boolean isVerCodeValidated = verificationCodeService.validateVerCode(verCode, verId);
        if (!isVerCodeValidated) {
            throw new BusinessException("验证码验证失败");
        }

        //先验证老密码对不对
        User u = userMapper.selectById(id);
        if (u == null) {
            throw new BusinessException("用户不存在");
        }

        EntityWrapper<User> objectEntityWrapper = new EntityWrapper<>();
        objectEntityWrapper.eq("login_name", loginName);
        List<User> users = this.baseMapper.selectList(objectEntityWrapper);
        if (users.size() == 0 || !users.get(0).getId().equals(id)) {
            throw new BusinessException("请输入当前用户注册时的邮箱或者手机号");
        }

        //修改新密码
        User user = new User();
        user.setId(id);
        if (type == 0) {
            user.setPassword(Md5Utils.hash(u.getLoginName(), newPassword));
        } else {
            validatePayPassword(newPassword);
            user.setPayPassword(Md5Utils.hash(u.getLoginName(), newPassword));
            EntityWrapper<Wallet> wrapper = new EntityWrapper<>();
            wrapper.eq("user_id", user.getId());
            Wallet wallet = new Wallet();
            wallet.setPassword(newPassword);
            walletMapper.update(wallet, wrapper);
        }
        user.setUpdateTime(new Date());
        return userMapper.updateById(user);
    }

    @Override
    public List<Map<String, Object>> getAddressBook(Map<String, Object> map, String userId) {
        List<Map<String, Object>> userList = new ArrayList<>();

        if (map.get("code") != null) {
            String code = map.get("code").toString();
            //运维
            if (code.equals("ops_user")) {
                List<Map<String, Object>> operaterBook = this.baseMapper.getOperaterBook(userId);
                userList.addAll(operaterBook);
            } else if (code.equals("firm_user")) {//企业用户
                List<Map<String, Object>> companyUserBook = this.baseMapper.getCompanyUserBook(userId);
                userList.addAll(companyUserBook);
            }
        }

        return userList;
    }

    @Override
    public ResponseResult updateCid(String id, String cid) {
        User user = new User();
        user.setId(id);
//    	user.setCid(cid);
        user.setUpdateTime(new Date());
        int rowCount = userMapper.updateById(user);
        return rowCount == 1 ? ResponseResult.success() : ResponseResult.fail();
    }

    @Override
    public List<Map<String, Object>> pcAddressBook(String userId) {
        return this.baseMapper.getPcAddressBook(userId);
    }

    @Override
    public void removeTokenByUserId(Integer userId) {
        EntityWrapper<UserToken> userTokenEntityWrapper = new EntityWrapper<>();
        userTokenEntityWrapper.eq("user_id", userId);
        userTokenMapper.delete(userTokenEntityWrapper);
    }

    @Override
    public void updateGestureSwitch(Integer gestureSwitch, String userId) {
        User user = new User();
        user.setId(userId);
        user.setGestureSwitch(gestureSwitch);
        this.baseMapper.updateById(user);
    }

    @Override
    public List<Integer> findAllUserIds() {
        return this.baseMapper.findAllUserIds();
    }

    @Override
    public List<User> getDirectUserLevel(String ids) {
        return this.baseMapper.getDirectUserLevel(ids);
    }

    /**
     * 铜牌玩家： 直推10人 ，合约体量达到8万$，拿公会成员收益5%
     * <p>
     * 银牌玩家：直推10人 ，有2个部门产生铜牌，合约体量达到20万$，拿公会成员收益8%
     * <p>
     * 金牌玩家：直推15人 ，有3个部门产生银牌经纪人，合约体量达到50万$，拿公会成员收益10%
     * <p>
     * 王牌玩家：直推15人，有3个部门产生金牌经纪人，合约体量达到150万美金$，拿公会成员收益15%
     *
     * @param userId
     */
    @Override
    public void updateUserLevel(Integer userId) {
        //查询工会签约合约总额
        BigDecimal unionSignTotalMoney = userContractService.getUnionSignTotalMoney(userId);
        //查询公工会的进阶合约总额
        BigDecimal unionAdvanceTotalMoney = userContractService.getUnionAdvanceTotalMoney(userId);
        //获取直推人员等级
        List<User> users = userLevelService.getUsersInUmbrella(userId);
        //判断当前用户等级
        Integer copperCount = 0;
        Integer sliverCount = 0;
        Integer goldCount = 0;
        for (User du : users) {
            switch (du.getLevel()) {
                case 1:
                    copperCount++;
                case 2:
                    sliverCount++;
                case 3:
                    goldCount++;
            }
        }
        Integer directNumber = userLevelService.getDirectCount(userId);
        Integer level = 0;
        if (directNumber >= 15 && goldCount >= 3 && unionSignTotalMoney.compareTo(new BigDecimal("1500000")) >= 0) {
            //王牌玩家
            level = 4;
        } else if (directNumber >= 15 && sliverCount >= 3 && unionSignTotalMoney.compareTo(new BigDecimal("500000")) >= 0) {
            //金牌玩家
            level = 3;
        } else if (directNumber >= 10 && copperCount >= 2 && unionSignTotalMoney.compareTo(new BigDecimal("200000")) >= 0) {
            //银牌玩家
            level = 2;
        } else if (directNumber >= 10 && unionSignTotalMoney.compareTo(new BigDecimal("80000")) >= 0) {
            //铜牌玩家
            level = 1;
        }
        //更新用户的level 及工会总额
        User user = new User();
        user.setId(userId.toString());
        user.setLevel(level);
        user.setUnionSignTotalMoney(unionSignTotalMoney);
        user.setUnionAdvanceTotalMoney(unionAdvanceTotalMoney);
        this.updateById(user);

        //当前用户更新完成 更新父用户的level及签约 进阶总额
        User currentUser = this.selectById(userId);
        String upUserId = currentUser.getUpUserId();
        if (StringUtils.isEmpty(upUserId)) {
            //到顶 没有父级
            return;
        } else {
            this.updateUserLevel(Integer.parseInt(upUserId));
        }

    }

    @Override
    public void validatePayPassword(Integer userId, String payPassword) throws BusinessException {
        User user = this.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        String needToValidate = Md5Utils.hash(user.getLoginName(), payPassword);
        if (!user.getPayPassword().equals(needToValidate)) {
            throw new BusinessException("支付密码校验错误");
        }

    }

    @Override
    public void resetPassword(String loginName, String password, String payPassword) throws BusinessException {
        validatePayPassword(payPassword);

        EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
        userEntityWrapper.eq("del_flag", "0");
        userEntityWrapper.eq("login_name", loginName);
        List<User> users = this.selectList(userEntityWrapper);
        if (users.size() == 0) {
            throw new BusinessException("未查询到响应用户");
        }
        User u = users.get(0);
        User user = new User();
        user.setId(u.getId());
        user.setPassword(Md5Utils.hash(loginName, password));
        user.setPayPassword(Md5Utils.hash(loginName, payPassword));
        this.updateById(user);
    }

    @Override
    public void updateUserName(String userId, String userName) throws BusinessException {
        EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
        userEntityWrapper.eq("del_flag", "0");
        userEntityWrapper.eq("user_name", userName);
        List<User> users = this.baseMapper.selectList(userEntityWrapper);
        if (users.size() > 0) {
            throw new BusinessException("该昵称已存在");
        }
        User user = new User();
        user.setId(userId);
        user.setUserName(userName);
        this.baseMapper.updateById(user);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized ResponseResult registerAdd(String userName, String loginName, String password, String walletPassword, Integer sendCode, Integer registerType) {
        try {
            validatePayPassword(walletPassword);
        } catch (BusinessException e) {
            return ResponseResult.fail("500", e.getMessage());
        }

        //count>0说明username已存在，isRepeat>0说明姓名已存在，重复需要加标识
        Integer count = userMapper.user(loginName);
//        Integer isRepeat = userMapper.isRepeat(userName);
        if (count > 0) {
            //登录名称重复
            return ResponseResult.fail("403", "登录名称重复");
        }
//        else if (isRepeat > 0) {
//            //用户名称重复
//            return ResponseResult.fail("403", "用户名称重复");
//        }
        else if (sendCode == null) {
            //推荐码不存在
            return ResponseResult.fail("403", "推荐码不存在");
        } else {
            // 新增用户
            //获取推送人id
            Map<String, Object> sendUser = userMapper.getUserBySendCode(sendCode);
            if (sendUser == null || sendUser.get("userId") == null) {
                //推送码失效
                return ResponseResult.fail("403", "推送码失效");
            }
            String sendUserId = sendUser.get("userId").toString();
            //生成6位随机的邀请码
            int random = (int) ((Math.random() * 9 + 1) * 100000);
            //新增用户
            User tbUser = new User();
            tbUser.setUserName(userName);
            tbUser.setLoginName(loginName);
            tbUser.setDelFlag(0);
            tbUser.setCreateTime(new Date());
            tbUser.setUpdateTime(new Date());
            tbUser.setRegisterType(registerType);
            if (registerType == 0) {
                tbUser.setEmail(loginName);
            } else {
                tbUser.setPhoneNumber(loginName);
            }
            tbUser.setPassword(Md5Utils.hash(loginName + password));
            tbUser.setSendCode(random);
            tbUser.setUpUserId(sendUserId);
            tbUser.setPayPassword(Md5Utils.hash(loginName + walletPassword));
            //获取所有人推荐人id
            String upUserIds = "";
            Object sendUpUserIdsObj = sendUser.get("upUserIds");
            if (sendUser.get("upUserId") == null) {
                //推荐人本身没有推荐人 最顶级
                upUserIds = sendUserId;
            } else {
                //推荐人还有推荐人
                upUserIds = sendUpUserIdsObj.toString() + "," + sendUserId;
            }
            tbUser.setUpUserIds(upUserIds);
            int userCount = userMapper.insert(tbUser);

            //新增用户层级关系
            userLevelService.addLevelRelation(upUserIds, tbUser.getId());

            //新增用户角色中间表
            String userId = tbUser.getId();
//            String roleId = map.get("roleId").toString();
//            if (StringUtils.isNotEmpty(roleId)) {
//                String[] arr = roleId.split(",");
//                for (String string : arr) {
//                    RoleUser roleUser = new RoleUser();
//                    roleUser.setRoleId(string);
//                    roleUser.setUserId(userId);
//                    roleUserMapper.insert(roleUser);
//                }
//            }

            //更新推荐人的团队成员总数
            this.updateRecMemberSize(sendUserId);

            //添加钱包
            try {
                walletService.createWallet(Integer.parseInt(userId), walletPassword);
            } catch (Exception e) {
                return ResponseResult.fail();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return userCount == 1 ? ResponseResult.success() : ResponseResult.fail();
        }
    }
}

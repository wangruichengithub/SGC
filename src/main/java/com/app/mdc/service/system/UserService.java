package com.app.mdc.service.system;

import com.app.mdc.exception.BusinessException;
import com.baomidou.mybatisplus.service.IService;
import com.app.mdc.model.system.User;
import com.app.mdc.utils.viewbean.ResponseResult;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {

    /**
     * 获取用户的信息
     * @param id 用户id
     * @return
     */
	ResponseResult getOne(String id);

    /**
     * 新增
     * @param map   id   username password    name    telephone   position    remark  status  roleId  companyid   rank    code
     * @return
     */
    ResponseResult add(Map<String,Object> map) throws Throwable;

    /**
     *  修改
     * @return
     */
    ResponseResult update(Map<String,Object> map);

    /**
     * 删除
     * @param id 用户id
     * @return
     */
    ResponseResult delete(String id);


    /**
     * 分页查询用户列表
     * @return User集合
     */
    List<User> findUserByPage(String name);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param httpSession httpsession，用来保存用户登录信息
     * @param loginType 登录类型，pc,app
     */
    Map<String, Object> doUserLogin(String username, String password, HttpSession httpSession, String loginType) throws BusinessException;

    /**
     * 根据用户token获取User；
     * @param userToken token
     * @return user
     */
    User findUserByToken(String userToken) throws BusinessException;

    /**
     * 修改密码
     *
     * @param type 密码类型 0登录密码 1支付密码
     * @param id    id
     * @param newPassword   新密码
     * @param oldPassword   老密码
     * @param verCode 验证码
     * @param verId 生成验证码的id
     * @return  int
     * @throws BusinessException    抛出错误
     */
    Integer updatePwd(Integer type, String id, String newPassword, String oldPassword, String verCode, String verId) throws BusinessException;

    /**
     * 手机端通讯录
     * @param map   角色
     * @param userId   当前用户id
     * @return  用户list
     */
    List<Map<String,Object>> getAddressBook(Map<String,Object> map,String userId);

	/**
	 * 更新cid
	 * @param id	用户id
	 * @param cid	用户cid
	 * @return	0，500
	 */
    ResponseResult updateCid(String id,String cid);

	/**
	 * pc端管理员聊天，所有人
	 * @param userId 当前用户id
	 * @return
	 */
	List<Map<String,Object>> pcAddressBook(String userId);

    /**
     * 删除用户对应的token
     * @param userId
     */
    void removeTokenByUserId(Integer userId);

    /**
     * 更新手势密码状态
     * @param gestureSwitch
     * @param userId
     */
    void updateGestureSwitch(Integer gestureSwitch, String userId);

    /**
     * 查询所有计算收益用户的id
     * @return
     */
    List<Integer> findAllUserIds();

    /**
     * 获取直接会员的信息
     * @param ids
     * @return
     */
    List<User> getDirectUserLevel(String ids);

    /**
     * 更新用户的等级
     * @param userId
     */
    void updateUserLevel(Integer userId);

    /**
     * 验证支付密码是否正确
     * @param userId
     * @param payPassword
     */
    void validatePayPassword(Integer userId, String payPassword) throws BusinessException;

    /**
     * 重置密码
     * @param loginName
     * @param password
     * @param password1
     */
    void resetPassword(String loginName, String password, String password1) throws BusinessException;

    /**
     * 修改昵称
     * @param userId
     * @param userName
     * @return
     */
    void updateUserName(String userId, String userName) throws BusinessException;

    ResponseResult registerAdd(String userName, String loginName, String password, String walletPassword, Integer sendCode, Integer registerType) throws Throwable;
}

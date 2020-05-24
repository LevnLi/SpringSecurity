package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.storage.domain.User;
import com.ruoyi.project.storage.mapper.RegisterMapper;
import com.ruoyi.project.storage.mapper.UserMapper;
import com.ruoyi.project.storage.mapper.UserRoleMapper;
import com.ruoyi.project.storage.msg.Msg;
import com.ruoyi.project.storage.service.UserService;
import com.ruoyi.project.storage.util.InfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.ruoyi.project.storage.util.InfoUtil.judgeUpdateInfo;
import static com.ruoyi.project.storage.util.InfoUtil.judgeUserInfo;

/**
 * @author :lihao
 * @date :2020/04/30
 * @description :用户service接口实现类
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends Msg implements UserService {
    /**
     * 用户Mapper接口
     */
    private final UserMapper userMapper;

    /**
     * 注册mapper接口
     */
    private final RegisterMapper registerMapper;

    /**
     * 用户角色mapper接口
     */
    private final UserRoleMapper userRoleMapper;

    /**
     * 通过构造方法注入
     * @param userMapper 用户Mapper
     * @param registerMapper 注册mapper
     * @param userRoleMapper 用户角色mapper
     */
    @Autowired
    public UserServiceImpl(UserMapper userMapper, RegisterMapper registerMapper, UserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.registerMapper = registerMapper;
        this.userRoleMapper = userRoleMapper;
    }

    /**
     * 用户列表（分页）
     *
     * @param user 用户实体
     * @return 用户列表
     */
    @Override
    public List<User> queryUserList(User user) {
        // 如果搜索信息不为空
        if (user.getSearchValue() != null){
            // 如果存在非法字符
            if (InfoUtil.isHaveIllegalChar(user.getSearchValue())){
                // 抛异常
                throw new CustomException("存在非法字符");
            }
        }
        // 返回查询结果
        return userMapper.queryUserList(user);
    }

    /**
     * 用户新增
     * @param user 用户实体
     * @return 结果
     */
    @Override
    public int insertUser(User user) {
        // 校验用户信息
        judgeUserInfo(user);
        // 如果当前账号存在
        if (registerMapper.queryByUserName(user.getUserName())!=null){
            // 抛异常
            throw new CustomException("用户名重复");
        }
        // 如果当前邮箱存在
        if (registerMapper.queryByEmail(user.getEmail())!=null){
            // 抛异常
            throw new CustomException("邮箱重复");
        }
        // 如果当前手机号存在
        if (registerMapper.queryByPhoneNumber(user.getPhonenumber())!=null){
            // 抛异常
            throw new CustomException("手机号重复");
        }
        // 创建时间
        user.setCreateTime(DateUtils.getNowDate());
        // 创建人
        user.setCreateBy(SecurityUtils.getUsername());
        // 更新时间
        user.setUpdateTime(DateUtils.getNowDate());
        // 更新人
        user.setUpdateBy(SecurityUtils.getUsername());
        // 初始密码
        user.setPassword(SecurityUtils.encryptPassword("Abc123456"));
        // 启用
        user.setStatus("0");
        // 类型
        user.setUserType("01");
        // 客户部门
        user.setDeptId(111L);
        // 版本号
        user.setVersion(0L);
        // 未删除
        user.setDelFlag("0");
        // 添加条数
        int count = userMapper.insertUser(user);
        // 如果新增失败
        if (count == ERROR){
            // 抛异常
            throw new CustomException("新增失败");
        }
        // 添加用户角色信息
        if (userRoleMapper.insertUserRole(user.getUserId(),BACKEND) == ERROR){
            // 抛异常
            throw new CustomException("添加用户角色信息失败");
        }
        // 返回成功信息
        return SUCCESS;
    }

    /**
     * 用户编辑
     *
     * @param user 用户实体
     * @return 结果
     */
    @Override
    public int updateUser(User user) {
        // 判断更新信息
        judgeUpdateInfo(user);
        // 更新时间
        user.setUpdateTime(DateUtils.getNowDate());
        // 更新人
        user.setUpdateBy(SecurityUtils.getUsername());
        // 修改条数
        int count = userMapper.updateUser(user);
        // 乐观锁判断
        if (count == ERROR){
            // 抛出异常
            throw new CustomException("当前用户已被他人操作，请刷新后重试");
        }
        // 返回修改条数
        return SUCCESS;
    }

    /**
     * 用户删除 : 通过ID批量操作 （0代表存在 2代表删除）
     *
     * @param ids 用户id数组
     * @return 结果
     */
    @Override
    public int deleteUserByIds(Long[] ids) {
        // 定义变量接收操作条数
        int count = userMapper.deleteUserByIds(ParameterUtil.getIdsUpdateByUpdateTime(ids,SecurityUtils.getUsername(),DateUtils.getNowDate()));
        // 如果更新条数不等于数组长度
        if (count != ids.length){
            // 抛异常
            throw new CustomException("状态为启用状态的用户，不能删除");
        }
        // 返回成功信息
        return SUCCESS;
    }

    /**
     * 用户启用/停用 :
     *
     * @param operate  帐号状态（0正常 1停用）
     * @param ids 用户id数组
     * @return 结果
     */
    @Override
    public int operateUserByIds(String operate, Long[] ids) {
        // 如果是停用指令
        if (DISABLE.equals(operate)){
            // 定义变量接收操作条数
            int count = userMapper.operateUserByIds(ParameterUtil.getIdsOperateUpdateByUpdateTime(ids,operate,SecurityUtils.getUsername(),DateUtils.getNowDate()));
            // 如果更新条数不等于数组长度
            if (count != ids.length){
                // 抛异常
                throw new CustomException("状态为停用的用户，不能停用");
            }
            // 返回成功信息
            return SUCCESS;
        }
        // 如果是启用指令
        if (ENABLE.equals(operate)){
            // 定义变量接收操作条数
            int count = userMapper.operateUserByIds(ParameterUtil.getIdsOperateUpdateByUpdateTime(ids,operate,SecurityUtils.getUsername(),DateUtils.getNowDate()));
            // 如果更新条数不等于数组长度
            if (count != ids.length){
                // 抛异常
                throw new CustomException("状态为启用的用户，不能启用");
            }
            // 返回成功信息
            return SUCCESS;
        }
        // 指令不正确
        return ERROR;
    }

    /**
     * 重置用户密码 : 重置为初始密码【Abc123456】
     *
     * @param ids 用户id数组
     * @return 结果
     */
    @Override
    public int resetUserPassword(Long[] ids) {
        // 定义变量接收操作条数
        int count = userMapper.resetUserPassword(ParameterUtil.getIdsOperateUpdateByUpdateTime(ids,SecurityUtils.encryptPassword("Abc123456"),SecurityUtils.getUsername(),DateUtils.getNowDate()));
        // 如果更新条数不等于数组长度
        if (count != ids.length){
            // 抛异常
            throw new CustomException("当前用户已被他人操作，请刷新后重试");
        }
        // 返回成功信息
        return SUCCESS;
    }
}
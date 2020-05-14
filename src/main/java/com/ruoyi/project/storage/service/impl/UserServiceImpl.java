package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.storage.domain.User;
import com.ruoyi.project.storage.mapper.RegisterMapper;
import com.ruoyi.project.storage.mapper.UserMapper;
import com.ruoyi.project.storage.msg.Msg;
import com.ruoyi.project.storage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * 通过构造方法注入
     * @param userMapper 用户Mapper
     * @param registerMapper 注册mapper
     */
    @Autowired
    public UserServiceImpl(UserMapper userMapper, RegisterMapper registerMapper) {
        this.userMapper = userMapper;
        this.registerMapper = registerMapper;
    }

    /**
     * 用户列表（分页）
     *
     * @param user 用户实体
     * @return 用户列表
     */
    @Override
    public List<User> queryUserList(User user) {
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
        // 如果当前账号存在
        if (registerMapper.queryByUserName(user.getUserName())!=null){
            // 抛异常
            throw new CustomException("用户名已存在");
        }
        // 如果当前邮箱存在
        if (registerMapper.queryByEmail(user.getEmail())!=null){
            // 抛异常
            throw new CustomException("邮箱已存在");
        }
        // 如果当前手机号存在
        if (registerMapper.queryByPhoneNumber(user.getPhonenumber())!=null){
            // 抛异常
            throw new CustomException("手机号已存在");
        }
        // 设置创建时间
        user.setCreateTime(DateUtils.getNowDate());
        // 设置创建人
        user.setCreateBy(SecurityUtils.getUsername());
        // 设置初始密码
        user.setPassword(SecurityUtils.encryptPassword("Abc,123456"));
        // 设置启用
        user.setStatus("0");
        // 设置类型
        user.setUserType("01");
        // 设置版本号
        user.setVersion(0L);
        // 设置未删除
        user.setDelFlag("0");
        // 添加条数
        int count = userMapper.insertUser(user);
        // 如果新增失败
        if (count == ERROR){
            // 抛异常
            throw new CustomException("新增失败");
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
        // 如果当前账号存在
        if (registerMapper.queryByUserName(user.getUserName())!=null){
            // 抛异常
            throw new CustomException("用户名已存在");
        }
        // 如果当前邮箱存在
        if (registerMapper.queryByEmail(user.getEmail())!=null){
            // 抛异常
            throw new CustomException("邮箱已存在");
        }
        // 如果当前手机号存在
        if (registerMapper.queryByPhoneNumber(user.getPhonenumber())!=null){
            // 抛异常
            throw new CustomException("手机号已存在");
        }
        // 设置更新时间
        user.setUpdateTime(DateUtils.getNowDate());
        // 设置更新人
        user.setUpdateBy(SecurityUtils.getUsername());
        // 修改条数
        int count = userMapper.updateUser(user);
        // 乐观锁判断
        if (count == ERROR){
            // 抛出异常
            throw new CustomException("修改失败");
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
            throw new CustomException("删除失败");
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
                throw new CustomException("停用失败");
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
                throw new CustomException("启用失败");
            }
            // 返回成功信息
            return SUCCESS;
        }
        // 指令不正确
        return ERROR;
    }

    /**
     * 重置用户密码 : 重置为初始密码【12345678】
     *
     * @param ids 用户id数组
     * @return 结果
     */
    @Override
    public int resetUserPassword(Long[] ids) {
        // 定义变量接收操作条数
        int count = userMapper.resetUserPassword(ParameterUtil.getBatchUpdateMapByOperateIds(SecurityUtils.encryptPassword("12345678"),ids));
        // 如果更新条数不等于数组长度
        if (count != ids.length){
            // 抛异常
            throw new CustomException("重置失败");
        }
        // 返回成功信息
        return SUCCESS;
    }
}
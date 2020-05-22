package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.storage.domain.User;
import com.ruoyi.project.storage.mapper.CustomerMapper;
import com.ruoyi.project.storage.mapper.RegisterMapper;
import com.ruoyi.project.storage.mapper.UserRoleMapper;
import com.ruoyi.project.storage.msg.Msg;
import com.ruoyi.project.storage.service.CustomerService;
import com.ruoyi.project.storage.util.InfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static com.ruoyi.project.storage.util.InfoUtil.*;

/**
 * @author :lihao
 * @date :2020/04/30
 * @description :客户service接口实现类
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceImpl extends Msg implements CustomerService {

    /**
     * 客户mapper接口
     */
    private final CustomerMapper customerMapper;

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
     * @param customerMapper 客户mapper
     * @param registerMapper 注册mapper
     * @param userRoleMapper 用户角色mapper
     */
    @Autowired
    public CustomerServiceImpl(CustomerMapper customerMapper, RegisterMapper registerMapper, UserRoleMapper userRoleMapper) {
        this.customerMapper = customerMapper;
        this.registerMapper = registerMapper;
        this.userRoleMapper = userRoleMapper;
    }

    /**
     * 客户列表（分页）
     *
     * @param user 客户对象
     * @return 客户列表
     */
    @Override
    public List<User> queryCustomerList(User user) {
        if (user.getSearchValue() != null){
            if (InfoUtil.isHaveIllegalChar(user.getSearchValue())){
                throw new CustomException("存在非法字符");
            }
        }
        // 返回查询结果
        return customerMapper.queryCustomerList(user);
    }

    /**
     * 客户新增
     * @param user 客户对象
     * @return 结果
     */
    @Override
    public int insertCustomer(User user) {
        // 校验用户信息
        judgeUserInfo(user);
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
        user.setPassword(SecurityUtils.encryptPassword("12345678"));
        // 设置启用
        user.setStatus("0");
        // 设置类型
        user.setUserType("02");
        // 设置版本号
        user.setVersion(0L);
        // 设置客户部门
        user.setDeptId(110L);
        // 设置未删除
        user.setDelFlag("0");
        // 设置积分
        user.setCurrentPoints(0L);
        // 添加条数
        int count = customerMapper.insertCustomer(user);
        // 如果新增失败
        if (count == ERROR){
            // 抛异常
            throw new CustomException("新增失败");
        }
        // 添加用户角色信息
        if (userRoleMapper.insertUserRole(user.getUserId(),APP) == ERROR){
            throw new CustomException("添加客户角色信息失败");
        }
        // 返回成功信息
        return SUCCESS;
    }

    /**
     * 客户编辑
     *
     * @param user 客户对象
     * @return 结果
     */
    @Override
    public int updateCustomer(User user) {
        // 判断更新信息
        judgeUpdateInfo(user);
        // 设置更新时间
        user.setUpdateTime(DateUtils.getNowDate());
        // 设置更新人
        user.setUpdateBy(SecurityUtils.getUsername());
        // 修改条数
        int count = customerMapper.updateCustomer(user);
        // 乐观锁判断
        if (count == ERROR){
            // 抛出异常
            throw new CustomException("当前客户已被他人操作，请刷新后重试");
        }
        // 返回修改条数
        return SUCCESS;
    }

    /**
     * 客户删除 : 通过ID批量操作 （0代表存在 2代表删除）
     *
     * @param ids 客户id数组
     * @return 结果
     */
    @Override
    public int deleteCustomerByIds(Long[] ids) {
        // 定义变量接收操作条数
        int count = customerMapper.deleteCustomerByIds(ParameterUtil.getIdsUpdateByUpdateTime(ids,SecurityUtils.getUsername(),DateUtils.getNowDate()));
        // 如果更新条数不等于数组长度
        if (count != ids.length){
            // 抛异常
            throw new CustomException("当前客户已被他人操作，请刷新后重试");
        }
        // 返回成功信息
        return SUCCESS;
    }

    /**
     * 客户启用/停用 :
     *
     * @param operate  帐号状态（0正常 1停用）
     * @param ids 客户id数组
     * @return 结果
     */
    @Override
    public int operateCustomerByIds(String operate, Long[] ids) {
        // 如果是停用指令
        if (DISABLE.equals(operate)){
            // 定义变量接收操作条数
            int count = customerMapper.operateCustomerByIds(ParameterUtil.getIdsOperateUpdateByUpdateTime(ids,operate,SecurityUtils.getUsername(),DateUtils.getNowDate()));
            // 如果更新条数不等于数组长度
            if (count != ids.length){
                // 抛异常
                throw new CustomException("状态为停用的客户，不能停用");
            }
            // 返回成功信息
            return SUCCESS;
        }
        // 如果是启用指令
        if (ENABLE.equals(operate)){
            // 定义变量接收操作条数
            int count = customerMapper.operateCustomerByIds(ParameterUtil.getIdsOperateUpdateByUpdateTime(ids,operate,SecurityUtils.getUsername(),DateUtils.getNowDate()));
            // 如果更新条数不等于数组长度
            if (count != ids.length){
                // 抛异常
                throw new CustomException("状态为启用的客户，不能启用");
            }
            // 返回成功信息
            return SUCCESS;
        }
        // 指令不正确
        return ERROR;
    }

    /**
     * 重置客户密码 : 重置为初始密码【12345678】
     *
     * @param ids 客户数组
     * @return 结果
     */
    @Override
    public int resetCustomerPassword(Long[] ids) {
        // 定义变量接收操作条数
        int count = customerMapper.resetCustomerPassword(ParameterUtil.getBatchUpdateMapByOperateIds(SecurityUtils.encryptPassword("12345678"),ids));
        // 如果更新条数不等于数组长度
        if (count != ids.length){
            // 抛异常
            throw new CustomException("当前客户已被他人操作，请刷新后重试");
        }
        // 返回成功信息
        return SUCCESS;
    }
}

package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.storage.domain.Point;
import com.ruoyi.project.storage.domain.User;
import com.ruoyi.project.storage.mapper.PointMapper;
import com.ruoyi.project.storage.mapper.RegisterMapper;
import com.ruoyi.project.storage.mapper.UserRoleMapper;
import com.ruoyi.project.storage.msg.Msg;
import com.ruoyi.project.storage.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ruoyi.project.storage.util.InfoUtil.judgeRegisterInfo;
import static com.ruoyi.project.storage.util.InfoUtil.judgeUserInfo;

/**
 * @author :lihao
 * @date :2020/05/04
 * @description :手机端注册service接口实现类
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class RegisterServiceImpl extends Msg implements RegisterService {

    /**
     * 注册mapper接口
     */
    private final RegisterMapper registerMapper;

    /**
     * 积分记录mapper接口
     */
    private final PointMapper pointMapper;

    /**
     * 用户角色mapper接口
     */
    private final UserRoleMapper userRoleMapper;

    /**
     * 通过构造方法注入
     * @param registerMapper 注册mapper
     * @param pointMapper  积分记录mapper
     * @param userRoleMapper 用户角色mapper
     */
    @Autowired
    public RegisterServiceImpl(RegisterMapper registerMapper, PointMapper pointMapper, UserRoleMapper userRoleMapper) {
        this.registerMapper = registerMapper;
        this.pointMapper = pointMapper;
        this.userRoleMapper = userRoleMapper;
    }

    /**
     * 手机客户注册
     *
     * @param user 注册实体
     * @return 结果
     */
    @Override
    public int registerUser(User user) {
        // 校验用户信息
        judgeRegisterInfo(user);
        // 如果当前账号存在
        if (registerMapper.queryByUserName(user.getUserName())!=null){
            // 抛异常
            throw new CustomException("新增客户'"+user.getUserName()+"'失败，登录账号已存在");
        }
        // 如果当前邮箱存在
        if (registerMapper.queryByEmail(user.getEmail())!=null){
            // 抛异常
            throw new CustomException("新增客户'"+user.getUserName()+"'失败，邮箱账号已存在");
        }
        // 如果当前手机号存在
        if (registerMapper.queryByPhoneNumber(user.getPhonenumber())!=null){
            // 抛异常
            throw new CustomException("新增客户'"+user.getUserName()+"'失败，手机号码已存在");
        }
        // 接收客户注册成功的id
        Long userId = insertCustomer(user);
        // 添加客户角色信息
        if (userRoleMapper.insertUserRole(userId,APP) == ERROR){
            throw new CustomException("添加客户角色信息失败");
        }
        // 传入客户ID
        user.setUserId(userId);
        // 调用添加积分对象接口
        insertPoint(user);
        // 返回成功信息
        return SUCCESS;
    }

    /**
     * 添加积分记录
     * @param user 注册实体
     */
    private void insertPoint(User user){
        // new一个积分对象
        Point point = new Point();
        // 创建人
        point.setCreateBy(user.getUserName());
        // 创建时间
        point.setCreateTime(DateUtils.getNowDate());
        // 通过注册客户返回的客户id，传给point对象
        point.setUserId(user.getUserId());
        // 获取方式
        point.setWay(1);
        // 获得积分
        point.setPoints(10000L);
        // 版本号
        point.setVersion(0L);
        // 未删除
        point.setDelFlag("0");
        // 向积分表添加记录
        if (pointMapper.insertPoint(point) == ERROR){
            // 抛出异常
            throw new CustomException("添加积分记录失败");
        }
    }

    /**
     * 添加客户信息
     * @param user 注册实体
     * @return 客户id
     */
    private Long insertCustomer(User user){
        // 设置创建时间
        user.setCreateTime(DateUtils.getNowDate());
        // 设置客户类型
        user.setUserType("02");
        // 设置客户状态
        user.setStatus("0");
        // 设置客户部门
        user.setDeptId(110L);
        // 设置未删除
        user.setDelFlag("0");
        // 设置创建方式
        user.setCreateBy("appRegister");
        // 设置版本号
        user.setVersion(0L);
        // 密码加密存放
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        // 设置初始积分
        user.setCurrentPoints(10000L);
        // 如果添加客户信息失败
        if (registerMapper.registerUser(user) == ERROR){
            // 抛出异常
            throw new CustomException("添加客户信息失败");
        }
        // 返回注册客户的id
        return user.getUserId();
    }
}

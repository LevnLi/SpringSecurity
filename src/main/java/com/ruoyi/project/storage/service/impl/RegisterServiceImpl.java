package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.storage.domain.Point;
import com.ruoyi.project.storage.domain.Register;
import com.ruoyi.project.storage.mapper.PointMapper;
import com.ruoyi.project.storage.mapper.RegisterMapper;
import com.ruoyi.project.storage.msg.Msg;
import com.ruoyi.project.storage.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * 通过构造方法注入
     * @param registerMapper 注册mapper
     * @param pointMapper  积分记录mapper
     */
    @Autowired
    public RegisterServiceImpl(RegisterMapper registerMapper, PointMapper pointMapper) {
        this.registerMapper = registerMapper;
        this.pointMapper = pointMapper;
    }

    /**
     * 手机用户注册
     *
     * @param register 注册实体
     * @return 结果
     */
    @Override
    public int registerUser(Register register) {
        // 如果当前账号存在
        if (registerMapper.queryByUserName(register.getUserName())!=null){
            // 抛异常
            throw new CustomException("新增用户'"+register.getUserName()+"'失败，登录账号已存在");
        }
        // 如果当前邮箱存在
        if (registerMapper.queryByEmail(register.getEmail())!=null){
            // 抛异常
            throw new CustomException("新增用户'"+register.getUserName()+"'失败，邮箱账号已存在");
        }
        // 如果当前手机号存在
        if (registerMapper.queryByPhoneNumber(register.getPhonenumber())!=null){
            // 抛异常
            throw new CustomException("新增用户'"+register.getUserName()+"'失败，手机号码已存在");
        }
        // 调用添加积分对象接口
        insertPoint(register);
        // 返回成功信息
        return SUCCESS;
    }

    /**
     * 添加积分记录
     * @param register 注册实体
     */
    private void insertPoint(Register register){
        // new一个积分对象
        Point point = new Point();
        // 创建人
        point.setCreateBy(register.getUserName());
        // 创建时间
        point.setCreateTime(DateUtils.getNowDate());
        // 通过注册用户返回的用户id，传给point对象
        point.setUserId(insertCustomer(register));
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
     * @param register 注册实体
     * @return 客户id
     */
    private Long insertCustomer(Register register){
        // 设置创建时间
        register.setCreateTime(DateUtils.getNowDate());
        // 设置用户类型
        register.setUserType("02");
        // 设置用户状态
        register.setStatus("0");
        // 设置未删除
        register.setDelFlag("0");
        // 设置创建方式
        register.setCreateBy("appRegister");
        // 设置版本号
        register.setVersion(0L);
        // 密码加密存放
        register.setPassword(SecurityUtils.encryptPassword(register.getPassword()));
        // 设置初始积分
        register.setCurrentPoints(10000L);
        // 如果添加用户信息失败
        if (registerMapper.registerUser(register) == ERROR){
            // 抛出异常
            throw new CustomException("添加客户信息失败");
        }
        // 返回注册用户的id
        return register.getUserId();
    }
}

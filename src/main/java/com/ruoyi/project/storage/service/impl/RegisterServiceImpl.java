package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.storage.domain.Register;
import com.ruoyi.project.storage.mapper.RegisterMapper;
import com.ruoyi.project.storage.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author :lihao
 * @date :2020/05/04
 * @description :手机端注册service接口实现类
 */
@Service
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    /**
     * 注册mapper接口
     */
    private final RegisterMapper registerMapper;

    /**
     * 通过构造方法注入
     * @param registerMapper
     */
    @Autowired
    public RegisterServiceImpl(RegisterMapper registerMapper) {
        this.registerMapper = registerMapper;
    }

    /**
     * 手机用户注册
     *
     * @param register
     * @return 结果
     */
    @Override
    public int registerUser(Register register) {
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
        register.setCurrentPoints(1000L);
        // 返回操作条数
        return registerMapper.registerUser(register);
    }

    /**
     * 账号是否存在
     * @param register
     * @return 结果
     */
    @Override
    public String queryByUserName(Register register) {
        // 返回查询结果
        return registerMapper.queryByUserName(register.getUserName());
    }

    /**
     * 手机号码是否存在
     * @param register
     * @return 结果
     */
    @Override
    public String queryByPhoneNumber(Register register) {
        // 返回查询结果
        return registerMapper.queryByPhoneNumber(register.getPhonenumber());
    }

    /**
     * 邮箱是否存在
     * @param register
     * @return 结果
     */
    @Override
    public String queryByEmail(Register register) {
        // 返回查询结果
        return registerMapper.queryByEmail(register.getEmail());
    }

    /**
     * 通过id查用户名
     *
     * @param register
     * @return 结果
     */
    @Override
    public Long queryIdByName(Register register) {
        // 返回用户id
        return registerMapper.queryIdByName(register.getUserName());
    }
}

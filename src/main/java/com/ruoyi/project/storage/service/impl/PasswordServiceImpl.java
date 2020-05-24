package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.storage.domain.User;
import com.ruoyi.project.storage.mapper.PasswordMapper;
import com.ruoyi.project.storage.msg.Msg;
import com.ruoyi.project.storage.service.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.ruoyi.project.storage.util.InfoUtil.judgePasswordInfo;

/**
 * @author :lihao
 * @date :2020/05/04
 * @description : 密码service接口实现类
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PasswordServiceImpl extends Msg implements PasswordService {

    /**
     * 密码mapper接口
     */
    private final PasswordMapper passwordMapper;

    /**
     * 通过构造方法注入
     * @param passwordMapper 密码mapper
     */
    @Autowired
    public PasswordServiceImpl(PasswordMapper passwordMapper) {
        this.passwordMapper = passwordMapper;
    }

    /**
     * 更新密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 返回结果
     */
    @Override
    public int updatePassword(String oldPassword, String newPassword) {
        // 判断旧密码格式
        judgePasswordInfo(oldPassword);
        // 判断新密码格式
        judgePasswordInfo(newPassword);
        // 查询到用户当前密码
        String password = queryOldPassword(SecurityUtils.getUserId());
        // 如果输入的旧密码和数据库中的旧密码不一样
        if (!SecurityUtils.matchesPassword(oldPassword,password)){
            // 抛出异常信息
            throw new CustomException("原密码错误");
        }
        // 如果输入旧密码正确但是新密码与数据库旧密码相同
        if(SecurityUtils.matchesPassword(newPassword,password)){
            // 抛出异常信息
            throw new CustomException("新密码和原密码不能一致");
        }
        // 以上都成功，更新数据库当前用户密码
        User user = new User();
        // 用户id
        user.setUserId(SecurityUtils.getUserId());
        // 用户新密码
        user.setPassword(SecurityUtils.encryptPassword(newPassword));
        // 更新人
        user.setUpdateBy(SecurityUtils.getUsername());
        // 更新时间
        user.setUpdateTime(DateUtils.getNowDate());
        // 如果密码更新失败
        if (passwordMapper.updatePassword(user) == ERROR){
            // 抛异常
            throw new CustomException("密码修改失败");
        }
        // 返回成功
        return SUCCESS;
    }

    /**
     * 查询旧密码
     *
     * @param id          用户id
     * @return 旧密码
     */
    private String queryOldPassword(Long id) {
        // 返回旧密码
        return passwordMapper.queryOldPassword(id);
    }
}

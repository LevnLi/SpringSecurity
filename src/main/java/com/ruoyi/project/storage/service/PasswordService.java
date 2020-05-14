package com.ruoyi.project.storage.service;

/**
 * @author :lihao
 * @date :2020/05/04
 * @description :个人service接口
 */
public interface PasswordService {

    /**
     * 更新密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 返回结果
     */
    int updatePassword (String oldPassword, String newPassword);
}

package com.ruoyi.project.storage.service;

import com.ruoyi.project.storage.domain.Personal;

/**
 * @author :lihao
 * @date :2020/05/04
 * @description :个人service接口
 */
public interface PersonalService {

    /**
     * 查询旧密码
     * @param id          用户id
     * @param userType    用户类型
     * @return 返回结果
     */
    String queryOldPassword (Long id,String userType);

    /**
     * 更新密码
     * @param id          用户id
     * @param newPassword 新密码
     * @param userType    用户类型
     * @return 返回结果
     */
    int updatePassword (Long id,String newPassword,String userType);
}

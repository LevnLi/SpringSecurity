package com.ruoyi.project.storage.service;

import com.ruoyi.project.storage.domain.User;

import java.util.List;

/**
 * @author :lihao
 * @date :2020/04/30
 * @description : 用户service接口
 */
public interface UserService {

    /**
     * 用户列表（分页）
     * @param user 用户实体类
     * @return 用户列表
     */
    List<User> queryUserList(User user);

    /**
     * 用户新增
     * @param user 用户实体类
     * @return 结果
     */
    int insertUser(User user);

    /**
     * 用户编辑
     * @param user 用户实体类
     * @return 结果
     */
    int updateUser(User user);

    /**
     * 用户删除 : 通过ID批量操作 （0代表存在 2代表删除）
     * @param ids 用户id数组
     * @return 结果
     */
    int deleteUserByIds(Long[] ids);

    /**
     * 用户启用/停用 : 帐号状态（0正常 1停用）
     * @param operate 指令
     * @param ids 用户id数组
     * @return 结果
     */
    int operateUserByIds(String operate,Long[] ids);

    /**
     * 重置用户密码 : 重置为初始密码【12345678】
     * @param ids 用户id数组
     * @return 结果
     */
    int resetUserPassword(Long[] ids);
}

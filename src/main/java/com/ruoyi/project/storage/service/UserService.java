package com.ruoyi.project.storage.service;

import com.ruoyi.project.storage.domain.Customer;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/04/30
 * @description : 用户service接口
 */
public interface UserService {

    /**
     * 用户列表（分页）
     * @param user
     * @return 用户列表
     */
    List<Customer> queryUserList(Customer customer);

    /**
     * 用户新增
     * @param customer
     * @return 结果
     */
    int insertUser(Customer customer);

    /**
     * 用户编辑
     * @param customer
     * @return 结果
     */
    int updateUser(Customer customer);

    /**
     * 用户删除 : 通过ID批量操作 （0代表存在 2代表删除）
     * @param ids
     * @return 结果
     */
    int deleteUserByIds(Long[] ids);

    /**
     * 用户启用/停用 : 帐号状态（0正常 1停用）
     * @param operate
     * @param ids
     * @return 结果
     */
    int operateUserByIds(String operate,Long[] ids);

    /**
     * 重置用户密码 : 重置为初始密码【12345678】
     * @param ids
     * @return 结果
     */
    int resetUserPassword(Long[] ids);
}

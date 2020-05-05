package com.ruoyi.project.storage.service;

import com.ruoyi.project.storage.domain.Customer;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/04/30
 * @description : 客户service接口
 */
public interface CustomerService {

    /**
     * 客户列表（分页）
     * @param customer
     * @return 客户列表
     */
    List<Customer> queryCustomerList(Customer customer);

    /**
     * 客户新增
     * @param customer
     * @return 结果
     */
    int insertCustomer(Customer customer);

    /**
     * 客户编辑
     * @param customer
     * @return 结果
     */
    int updateCustomer(Customer customer);

    /**
     * 客户删除 : 通过ID批量操作 （0代表存在 2代表删除）
     * @param ids
     * @return 结果
     */
    int deleteCustomerByIds(Long[] ids);

    /**
     * 客户启用/停用 : 帐号状态（0正常 1停用）
     * @param operate
     * @param ids
     * @return 结果
     */
    int operateCustomerByIds(String operate,Long[] ids);

    /**
     * 重置客户密码 : 重置为初始密码【12345678】
     * @param ids
     * @return 结果
     */
    int resetCustomerPassword(Long[] ids);
}

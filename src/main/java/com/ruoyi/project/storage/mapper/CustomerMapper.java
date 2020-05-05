package com.ruoyi.project.storage.mapper;

import com.ruoyi.project.storage.domain.Customer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author :lihao
 * @date :2020/04/30
 * @description : 客户mapper接口
 */
@Mapper
public interface CustomerMapper {

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
     * 批量客户删除 : 通过ID批量操作 （0代表存在 2代表删除）
     * @param map
     * @return
     */
    int deleteCustomerByIds(Map<String, Object> map);

    /**
     * 批量客户启用/停用 : 帐号状态（0正常 1停用）
     * @param map
     * @return
     */
    int operateCustomerByIds(Map<String, Object> map);

    /**
     * 批量重置用户密码 : 重置为初始密码【12345678】
     * @param map
     * @return
     */
    int resetCustomerPassword(Map<String, Object> map);
}

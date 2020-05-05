package com.ruoyi.project.storage.mapper;

import com.ruoyi.project.storage.domain.Customer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author :lihao
 * @date :2020/04/30
 * @description : 用户mapper接口
 */
@Mapper
public interface UserMapper {

    /**
     * 用户列表（分页）
     * @param customer
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
     * 批量用户删除 : 通过ID批量操作 （0代表存在 2代表删除）
     * @param map
     * @return
     */
    int deleteUserByIds(Map<String, Object> map);

    /**
     * 批量用户启用/停用 : 帐号状态（0正常 1停用）
     * @param map
     * @return
     */
    int operateUserByIds(Map<String, Object> map);

    /**
     * 批量重置用户密码 : 重置为初始密码【Abc,123456】
     * @param map
     * @return
     */
    int resetUserPassword(Map<String, Object> map);
}

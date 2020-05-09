package com.ruoyi.project.storage.mapper;

import com.ruoyi.project.storage.domain.Address;
import com.ruoyi.project.storage.domain.Advertisement;
import java.util.List;
import java.util.Map;

/**
 * @author :lihao
 * @date :2020/05/09
 * @description :地址mapper接口
 */
public interface AddressServiceMapper {

    /**
     * 查询地址列表
     * @param address 地址
     * @return 地址集合
     */
    List<Address> selectAddressList(Address address);

    /**
     * 新增地址
     * @param address 地址
     * @return 结果
     */
    int insertAddress(Address address);

    /**
     * 修改地址
     * @param address 地址
     * @return 结果
     */
    int updateAddress(Address address);

    /**
     * 删除地址
     * @param map 需要删除的地址ID
     * @return 结果
     */
    int deleteAddressById(Map<String,Object> map);

    /**
     * 设置默认地址
     * @param map 需要设置默认地址ID
     * @return 结果
     */
    int defaultAddressById(Map<String,Object> map);

    /**
     * 移除默认地址
     * @param map
     * @return 结果
     */
    int removeDefaultAddress(Map<String,Object> map);

    /**
     * 通过用户ID查是否存在地址信息
     * @param id
     * @return 结果
     */
    String queryAddressByUserId(Long id);
}

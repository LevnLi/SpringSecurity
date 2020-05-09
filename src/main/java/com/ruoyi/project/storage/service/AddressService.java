package com.ruoyi.project.storage.service;

import com.ruoyi.project.storage.domain.Address;
import com.ruoyi.project.storage.domain.Advertisement;

import java.util.List;

/**
 * @author :lihao
 * @date :2020/04/28
 * @description :地址Service接口
 */
public interface AddressService {

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
     * @param id 需要删除的地址ID
     * @return 结果
     */
    int deleteAddressById(Long id);

    /**
     * 设置默认地址
     * @param id 需要设置默认地址ID
     * @return 结果
     */
    int defaultAddressById(Long id);

    /**
     * 移除默认地址
     * @return 结果
     */
    int removeDefaultAddress();

    /**
     * 通过用户ID查是否存在地址信息
     * @return
     */
    String queryAddressByUserId();

}

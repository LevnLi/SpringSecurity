package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.storage.domain.Address;
import com.ruoyi.project.storage.domain.Advertisement;
import com.ruoyi.project.storage.mapper.AddressServiceMapper;
import com.ruoyi.project.storage.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/05/09
 * @description :地址Service接口实现类
 */
@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    /**
     * 地址mapper接口
     */
    private final AddressServiceMapper addressServiceMapper;

    /**
     * 通过构造方法注入
     * @param addressServiceMapper 地址mapper
     */
    @Autowired
    public AddressServiceImpl(AddressServiceMapper addressServiceMapper) {
        this.addressServiceMapper = addressServiceMapper;
    }

    /**
     * 查询地址列表
     *
     * @param address 地址
     * @return 地址集合
     */
    @Override
    public List<Address> selectAddressList(Address address) {
        // 设置用户id
        address.setUserId(SecurityUtils.getUserId());
        // 返回查询结果
        return addressServiceMapper.selectAddressList(address);
    }

    /**
     * 新增地址
     *
     * @param address 地址
     * @return 结果
     */
    @Override
    public int insertAddress(Address address) {
        // 用户id
        address.setUserId(SecurityUtils.getUserId());
        // 创建人
        address.setCreateBy(SecurityUtils.getUsername());
        // 创建时间
        address.setCreateTime(DateUtils.getNowDate());
        // 未删除
        address.setDelFlag("0");
        // 版本号
        address.setVersion(0L);
        // 返回添加结果
        return addressServiceMapper.insertAddress(address);
    }

    /**
     * 修改地址
     *
     * @param address 地址
     * @return 结果
     */
    @Override
    public int updateAddress(Address address) {
        // 设置用户id
        address.setUserId(SecurityUtils.getUserId());
        // 设置更新人
        address.setUpdateBy(SecurityUtils.getUsername());
        // 设置更新时间
        address.setUpdateTime(DateUtils.getNowDate());
        // 返回操作条数
        return addressServiceMapper.updateAddress(address);
    }

    /**
     * 删除地址
     *
     * @param id 需要删除的地址ID
     * @return 结果
     */
    @Override
    public int deleteAddressById(Long id) {
        /**
         * 内嵌方法参数:
         *      id: 地址id
         *      updateBy: 更新人
         *      updateTime: 更新时间
         * @return map
         */
        return addressServiceMapper.deleteAddressById(ParameterUtil.getIdUpdateByUpdateTime(id,SecurityUtils.getUsername(),DateUtils.getNowDate()));
    }

    /**
     * 设置默认地址
     *
     * @param id 需要为默认地址的地址ip
     * @return 结果
     */
    @Override
    public int defaultAddressById(Long id) {
        /**
         * 内嵌方法参数:
         *      id: 地址id
         *      updateBy: 更新人
         *      updateTime: 更新时间
         * @return map
         */
        return addressServiceMapper.defaultAddressById(ParameterUtil.getIdUpdateByUpdateTime(id,SecurityUtils.getUsername(),DateUtils.getNowDate()));
    }

    /**
     * 移除默认地址
     *
     * @return 结果
     */
    @Override
    public int removeDefaultAddress() {
        /**
         * 内嵌方法参数:
         *      userId: 当前客户id
         *      updateBy: 更新人
         *      updateTime: 更新时间
         * @return map
         */
        return addressServiceMapper.removeDefaultAddress(ParameterUtil.getIdUpdateByUpdateTime(SecurityUtils.getUserId(),SecurityUtils.getUsername(),DateUtils.getNowDate()));
    }

    /**
     * 通过用户ID查是否存在地址信息
     *
     * @return 结果
     */
    @Override
    public String queryAddressByUserId() {
        // 返回查询结果
        return addressServiceMapper.queryAddressByUserId(SecurityUtils.getUserId());
    }
}

package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.storage.domain.Address;
import com.ruoyi.project.storage.mapper.AddressMapper;
import com.ruoyi.project.storage.msg.Msg;
import com.ruoyi.project.storage.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/05/09
 * @description :地址Service接口实现类
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AddressServiceImpl extends Msg implements AddressService {

    /**
     * 地址mapper接口
     */
    private final AddressMapper addressMapper;

    /**
     * 通过构造方法注入
     * @param addressMapper 地址mapper
     */
    @Autowired
    public AddressServiceImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
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
        return addressMapper.selectAddressList(address);
    }

    /**
     * 新增地址
     *
     * @param address 地址
     * @return 结果
     */
    @Override
    public int insertAddress(Address address) {
        // 调用地址信息判空方法
        isNullInfo(address);
        // 调用是否为默认地址处理方法
        isDefault(address);
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
        // 添加结果
        int count = addressMapper.insertAddress(address);
        // 如果添加地址失败
        if (count == ERROR){
            // 抛出异常
            throw new CustomException("修改为普通地址失败");
        }
        // 返回成功信息
        return SUCCESS;
    }

    /**
     * 修改地址
     *
     * @param address 地址
     * @return 结果
     */
    @Override
    public int updateAddress(Address address) {
        // 调用地址信息判空方法
        isNullInfo(address);
        // 调用是否为默认地址处理方法
        isDefault(address);
        // 设置用户id
        address.setUserId(SecurityUtils.getUserId());
        // 设置更新人
        address.setUpdateBy(SecurityUtils.getUsername());
        // 设置更新时间
        address.setUpdateTime(DateUtils.getNowDate());
        // 如果更新地址失败
        if (addressMapper.updateAddress(address) == ERROR){
            // 抛出异常
            throw new CustomException("更新地址失败");
        }
        // 返回成功信息
        return SUCCESS;
    }

    /**
     * 删除地址
     *
     * @param id 需要删除的地址ID
     * @return 结果
     */
    @Override
    public int deleteAddressById(Long id) {
        int count = addressMapper.deleteAddressById(ParameterUtil.getIdUpdateByUpdateTime(id,SecurityUtils.getUsername(),DateUtils.getNowDate()));
        // 如果删除地址失败
        if (count == ERROR){
            // 抛出异常
            throw new CustomException("删除地址失败");
        }
        // 返回成功信息
        return SUCCESS;
    }

    /**
     * 设置默认地址
     *
     * @param id 需要为默认地址的地址ip
     * @return 结果
     */
    @Override
    public int defaultAddressById(Long id) {
        // 定义变量记录
        int count;
        // 如果手机端客户存在默认地址信息
        if (addressMapper.queryAddressByUserId(SecurityUtils.getUserId())!=null){
            // 将手机端客户的默认地址改为普通地址
            count = addressMapper.removeDefaultAddress(ParameterUtil.getIdUpdateByUpdateTime(SecurityUtils.getUserId(),SecurityUtils.getUsername(),DateUtils.getNowDate()));
            // 如果修改失败
            if (count == ERROR){
                // 抛出异常
                throw new CustomException("修改为普通地址失败");
            }
        }
        // 记录修改条数
        count = addressMapper.defaultAddressById(ParameterUtil.getIdUpdateByUpdateTime(id,SecurityUtils.getUsername(),DateUtils.getNowDate()));
        // 如果设置默认地址失败
        if (count == ERROR){
            // 抛出异常
            throw new CustomException("设置默认地址失败");
        }
        // 返回成功信息
        return SUCCESS;
    }

    /**
     * 如果是默认地址，移除
     * @param address 地址实体
     */
    private void isDefault(Address address){
        // 定义变量记录更新条数
        int count;
        // 如果新添地址为默认地址
        if (address.getIsDefault() == 0){
            // 如果手机端客户存在默认地址信息
            if (addressMapper.queryAddressByUserId(SecurityUtils.getUserId())!=null){
                // 将手机端客户的默认地址改为普通地址
                count = addressMapper.removeDefaultAddress(ParameterUtil.getIdUpdateByUpdateTime(SecurityUtils.getUserId(),SecurityUtils.getUsername(),DateUtils.getNowDate()));
                // 如果修改失败
                if (count == ERROR){
                    // 抛出异常
                    throw new CustomException("修改为普通地址失败");
                }
            }
        }
    }

    private void isNullInfo(Address address){
        // 如果地址信息不完整
        if (
            address.getContacts() == null || "".equals(address.getContacts()) ||
            address.getContactsPhone() == null || "".equals(address.getContactsPhone()) ||
            address.getAddress() == null || "".equals(address.getAddress())||
            address.getIsDefault() == null || address.getIsDefault() > 1 || address.getIsDefault() < 0
        ){
            // 抛出异常
            throw new CustomException("地址信息不完整");
        }
    }
}

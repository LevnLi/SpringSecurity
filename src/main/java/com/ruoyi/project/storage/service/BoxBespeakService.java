package com.ruoyi.project.storage.service;

import com.ruoyi.project.storage.domain.Address;
import com.ruoyi.project.storage.domain.BoxStandardV1;
import com.ruoyi.project.storage.domain.Order;

import java.util.List;

/**
 * @author :lihao
 * @date :2020/05/09
 * @description :空箱预约service接口
 */
public interface BoxBespeakService {

    /**
     * 获取当前用户默认收货地址
     * @return 结果
     */
    Address getDefaultAddress();

    /**
     * 有效箱子规格选择（非分页）
     * @return 结果
     */
    List<BoxStandardV1> getStandardList();

    /**
     * 空箱预约
     * @param order 预约对象
     * @return 结果
     */
    int boxOrder(Order order);
}

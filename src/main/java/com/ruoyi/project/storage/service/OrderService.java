package com.ruoyi.project.storage.service;

import com.ruoyi.project.storage.domain.Order;
import com.ruoyi.project.storage.domain.OrderV0;
import com.ruoyi.project.storage.domain.OrderV1;

import java.text.ParseException;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/05/12
 * @description : 订单service接口
 */
public interface OrderService {

    /**
     * 订单列表
     * @param orderV1 订单对象
     * @return 结果
     */
    List<Order> getOrderList(OrderV1 orderV1) throws ParseException;

    /**
     * 通过id查订单信息
     * @param id 订单id
     * @param msg 信息
     * @return 订单信息
     */
    Order getOrderInfoById(Long id,String msg);

    /**
     * 订单操作对象
     * @param orderV0 订单对象V0
     * @return 操作结果
     */
    int orderOperation(OrderV0 orderV0);

    /**
     * 批量删除订单
     * @param ids 订单id数组
     * @return 结果
     */
    int deleteOrder(Long[] ids);
}

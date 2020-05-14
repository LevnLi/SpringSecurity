package com.ruoyi.project.storage.mapper;

import com.ruoyi.project.storage.domain.Order;
import com.ruoyi.project.storage.domain.OrderV1;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * @author :lihao
 * @date :2020/05/12
 * @description : 订单mapper接口
 */
public interface OrderMapper {

    /**
     * 订单列表
     * @param orderV1 订单对象
     * @return 结果
     */
    List<Order> getOrderList(OrderV1 orderV1);

    /**
     * 通过id查订单信息
     *
     * @param map 集合
     * @return 结果
     */
    List<Order> getOrderInfoById(Map<String,Object> map);

    /**
     * 批量删除订单
     * @param map 集合
     * @return 结果
     */
    int deleteOrder(Map<String,Object> map);

    /**
     * 批量删除订单历史
     * @param map 集合
     * @return 结果
     */
    int deleteOrderHis(Map<String,Object> map);

    /**
     * 通过订单id查订单状态
     * @param id 订单id
     * @return 订单状态
     */
    Order selectOrderInfo(@Param("id") Long id);

    /**
     * 更新订单状态
     * @param order 订单对象
     * @return 结果
     */
    int updateOrderInfo(Order order);

    /**
     * 删除手机端订单
     * @param map 集合
     * @return 结果
     */
    int deleteOrderByPhone(Map<String,Object> map);

    /**
     * 清除箱子使用信息
     * @param map 集合
     * @return 结果
     */
    int clearBoxInfo(Map<String,Object> map);

}

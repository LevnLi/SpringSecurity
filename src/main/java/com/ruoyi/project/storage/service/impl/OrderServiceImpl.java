package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.storage.domain.Order;
import com.ruoyi.project.storage.domain.OrderV0;
import com.ruoyi.project.storage.mapper.OrderHistoryMapper;
import com.ruoyi.project.storage.mapper.OrderMapper;
import com.ruoyi.project.storage.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/05/12
 * @description :订单service接口实现类
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    /**
     * 订单mapper接口
     */
    private final OrderMapper orderMapper;

    /**
     * 订单历史记录mapper接口
     */
    private final OrderHistoryMapper orderHistoryMapper;

    /**
     * 通过构造方法注入
     * @param orderMapper 订单mapper
     * @param orderHistoryMapper 订单历史记录mapper
     */
    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, OrderHistoryMapper orderHistoryMapper) {
        this.orderMapper = orderMapper;
        this.orderHistoryMapper = orderHistoryMapper;
    }

    /**
     * 订单列表
     *
     * @param order 订单对象
     * @return 结果
     */
    @Override
    public List<Order> getOrderList(Order order) {
        return orderMapper.getOrderList(order);
    }

    /**
     * 通过id查订单信息
     *
     * @param id 订单id
     * @param msg 信息
     * @return 订单对象
     */
    @Override
    public List<Order> getOrderInfoById(Long id,String msg) {
        return orderMapper.getOrderInfoById(ParameterUtil.getMapByMsg(SecurityUtils.getUserId(),id,msg));
    }

    /**
     * 订单操作对象
     *
     * @param orderV0 订单对象V0
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int orderOperation(OrderV0 orderV0) {
        // 定义字符串: 后台端
        String backend = "backend";
        // 定义字符串: 手机端
        String app = "app";
        // 获取当前id下的订单信息
        Order order = orderMapper.selectOrderInfo(orderV0.getId());
        // 如果是后台端操作
        if (backend.equals(orderV0.getMsg())){
            // 返回后台端处理结果
            return backendInto(order,orderV0);
        }
        // 如果是手机端操作
        if (app.equals(orderV0.getMsg())){
            // 返回手机端处理结果
            return appInto(order,orderV0);
        }
        // 没通过以上指令，返回0，表示操作失败
        return 0;
    }

    /**
     * 批量删除订单
     *
     * @param ids 订单id数组
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteOrder(Long[] ids) {
        int count = 0;
        count = orderMapper.deleteOrder(ParameterUtil.getIdsUpdateByUpdateTime(ids,SecurityUtils.getUsername(), DateUtils.getNowDate()));
        if (count < ids.length){
            throw new CustomException("删除失败");
        }
        // 删除订单历史记录
        count = orderMapper.deleteOrderHis(ParameterUtil.getIdsUpdateByUpdateTime(ids,SecurityUtils.getUsername(), DateUtils.getNowDate()));
        if (count < ids.length){
            throw new CustomException("删除失败");
        }
        // 返回删除订单记录结果
        return count;
    }

    /**
     * 更新订单，创建历史记录
     * @param order 订单对象
     */
    private void updateOrder(Order order){
        // 订单状态
        order.setStatus(order.getStatus()+1);
        // 更新人
        order.setUpdateBy(SecurityUtils.getUsername());
        // 更新时间
        order.setUpdateTime(DateUtils.getNowDate());
        // 定义记录变量
        int count = 0;
        // 记录订单更改条数
        count += orderMapper.updateOrderInfo(order);
        // 创建时间
        order.setCreateTime(DateUtils.getNowDate());
        // 创建人
        order.setCreateBy(SecurityUtils.getUsername());
        // 添加订单id
        order.setOrderId(order.getId());
        // 记录订单历史记录创建条数
        count += orderHistoryMapper.insertOrderHistory(order);
        // 如果更新订单或插入订单历史记录失败
        if (count < 2){
            // 抛出异常
            throw new CustomException("更新订单失败");
        }
    }

    /**
     * 手机端操作方法
     * @param order 订单对象
     * @param orderV0 前端传的订单信息
     * @return 1: 操作成功  0: 操作失败
     */
    private int appInto(Order order,OrderV0 orderV0){
        // 如果订单状态为 2 【手机端】待收空箱
        if (order.getStatus() == 2){
            // 如果订单操作指令错误
            if (orderV0.getOperate() != 3){
                // 抛出异常
                throw new CustomException("操作失败: 状态2");
            }
            // 调用更新订单方法
            updateOrder(order);
            // 表示操作成功
            return 1;
        }
        // 如果订单状态为 3 【手机端】待发重箱
        if (order.getStatus() == 3){
            // 如果不是手机端用户且订单操作指令错误
            if (orderV0.getOperate() != 4){
                // 抛出异常
                throw new CustomException("操作失败: 状态3");
            }
            // 调用更新订单方法
            updateOrder(order);
            // 表示操作成功
            return 1;
        }
        // 如果订单状态为 5 【手机端】待预约提取
        if (order.getStatus() == 5){
            // 如果不是手机端用户且订单操作指令错误
            if (orderV0.getOperate() != 6){
                // 抛出异常
                throw new CustomException("操作失败: 状态5");
            }
            if (
                // 提取时间或下单时间不存在
                    orderV0.getHeavyBoxCallTime() == null || orderV0.getHeavyBoxOrderTime() == null ||
                            // 提取人姓名不存在或为空
                            orderV0.getHeavyBoxCallName() == null || "".equals(orderV0.getHeavyBoxCallName()) ||
                            // 提取人电话不存在或为空
                            orderV0.getHeavyBoxCallPhone() == null || "".equals(orderV0.getHeavyBoxCallPhone()) ||
                            // 提取地址不存在或为空
                            orderV0.getHeavyBoxCallAddress() == null || "".equals(orderV0.getHeavyBoxCallAddress()) ||
                            // 提取时间段不存在或为空
                            orderV0.getHeavyBoxCallInterval() == null || "".equals(orderV0.getHeavyBoxCallInterval())
            ){
                // 抛出异常
                throw new CustomException("缺少重箱提取信息！！！");
            }
            // 填写重箱提取姓名
            order.setHeavyBoxCallName(orderV0.getHeavyBoxCallName());
            // 填写重箱提取时间
            order.setHeavyBoxCallTime(orderV0.getHeavyBoxCallTime());
            // 填写重箱提取电话
            order.setHeavyBoxCallPhone(orderV0.getHeavyBoxCallPhone());
            // 填写重箱提取下单时间
            order.setHeavyBoxOrderTime(orderV0.getHeavyBoxOrderTime());
            // 填写重箱提取地址
            order.setHeavyBoxCallAddress(orderV0.getHeavyBoxCallAddress());
            // 填写重箱提取时间段
            order.setHeavyBoxCallInterval(orderV0.getHeavyBoxCallInterval());
            // 调用更新订单方法
            updateOrder(order);
            // 表示操作成功
            return 1;
        }
        // 如果订单状态为 7 【手机端】待收重箱
        if (order.getStatus() == 7){
            // 如果不是手机端用户且订单操作指令错误
            if (orderV0.getOperate() != 8){
                // 抛出异常
                throw new CustomException("操作失败: 状态7");
            }
            // 调用更新订单方法
            updateOrder(order);
            // 表示操作成功
            return 1;
        }
        // 如果订单状态为 8 【手机端】待发空箱
        if (order.getStatus() == 8){
            // 如果不是手机端用户且订单操作指令错误
            if (orderV0.getOperate() != 9){
                // 抛出异常
                throw new CustomException("操作失败: 状态8");
            }
            // 调用更新订单方法
            updateOrder(order);
            // 表示操作成功
            return 1;
        }
        // 如果订单状态为 10 【手机端、后台端】可以删除订单
        if (order.getStatus() == 10){
            // 定义变量接收删除手机端订单结果
            int count = orderMapper.deleteOrderByPhone(ParameterUtil.getIdDataUpdateByUpdateTime(order.getId(),SecurityUtils.getUserId(),SecurityUtils.getUsername(),DateUtils.getNowDate()));
            // 如果小于表示订单删除失败
            if (count < 1){
                // 抛出异常
                throw new CustomException("操作失败: 状态10");
            }
            // 表示操作成功
            return 1;
        }
        return 0;
    }

    /**
     * 后台端操作方法
     * @param order 订单对象
     * @param orderV0 前台传的操作信息
     * @return 1: 操作成功  0: 操作失败
     */
    private int backendInto(Order order,OrderV0 orderV0){
        // 如果订单状态为 1 【后台端】待发空箱
        if (order.getStatus() == 1){
            // 如果不是后台端用户且订单操作指令错误
            if (orderV0.getOperate() != 2){
                // 抛出异常
                throw new CustomException("操作失败: 状态1");
            }
            // 调用更新订单方法
            updateOrder(order);
            // 表示操作成功
            return 1;
        }
        // 如果订单状态为 4 【后台端】待收重箱
        if (order.getStatus() == 4){
            // 如果不是后台端用户且订单操作指令错误
            if (orderV0.getOperate() != 5){
                throw new CustomException("操作失败: 状态4");
            }
            // 调用更新订单方法
            updateOrder(order);
            // 表示操作成功
            return 1;
        }
        // 如果订单状态为 6 【后台端】待发重箱
        if (order.getStatus() == 6){
            // 如果不是后台端用户且订单操作指令错误
            if (orderV0.getOperate() != 7){
                throw new CustomException("操作失败: 状态6");
            }
            // 调用更新订单方法
            updateOrder(order);
            // 表示操作成功
            return 1;
        }
        // 如果订单状态为 9 【后台端】待收空箱
        if (order.getStatus() == 9){
            // 如果不是后台端用户且订单操作指令错误
            if (orderV0.getOperate() != 10){
                throw new CustomException("操作失败: 状态9");
            }
            // 调用更新订单方法
            updateOrder(order);
            // 表示操作成功
            return 1;
        }
        // 没通过以上指令，返回0，表示操作失败
        return 0;
    }
}

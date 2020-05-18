package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.common.util.SeqGeneratorUtil;
import com.ruoyi.project.storage.domain.*;
import com.ruoyi.project.storage.mapper.BoxBespeakMapper;
import com.ruoyi.project.storage.mapper.OrderHistoryMapper;
import com.ruoyi.project.storage.mapper.PointMapper;
import com.ruoyi.project.storage.msg.Msg;
import com.ruoyi.project.storage.service.BoxBespeakService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @author :lihao
 * @date :2020/05/09
 * @description :空箱预约service接口实现类
 */
@Service
@Slf4j
public class BoxBespeakServiceImpl extends Msg implements BoxBespeakService {

    /**
     * 积分记录mapper接口
     */
    private final PointMapper pointMapper;

    /**
     * 空箱预约mapper接口
     */
    private final BoxBespeakMapper boxBespeakMapper;

    /**
     * 积分历史记录mapper接口
     */
    private final OrderHistoryMapper orderHistoryMapper;

    /**
     * 通过构造方法注入
     * @param pointMapper 积分mapper接口
     * @param boxBespeakMapper 空箱预约mapper接口
     * @param orderHistoryMapper 订单历史记录mapper接口
     */
    @Autowired
    public BoxBespeakServiceImpl(PointMapper pointMapper, BoxBespeakMapper boxBespeakMapper, OrderHistoryMapper orderHistoryMapper) {
        this.pointMapper = pointMapper;
        this.boxBespeakMapper = boxBespeakMapper;
        this.orderHistoryMapper = orderHistoryMapper;
    }

    /**
     * 获取当前用户默认收货地址
     *
     * @return 结果
     */
    @Override
    public Address getDefaultAddress() {
        // 返回查询结果
        return boxBespeakMapper.getDefaultAddress(SecurityUtils.getUserId());
    }

    /**
     * 有效箱子规格选择（非分页）
     *
     * @return 结果
     */
    @Override
    public List<BoxStandardV1> getStandardList() {
        // 返回查询结果
        return boxBespeakMapper.getStandardList(SecurityUtils.getUserId());
    }

    /**
     * 空箱预约
     *
     * @param order 预约对象
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int boxOrder(Order order) {
        // 获取当前用户积分
        Long userPoint = boxBespeakMapper.queryUserPoint(SecurityUtils.getUserId());
        // 如果当前用户积分不能存在或未零或小于所需积分
        if (userPoint == null || userPoint.intValue() == 0 || userPoint<order.getBoxUnitPrice() * order.getLeaseDuration()){
            // 抛出异常
            throw new CustomException("积分余额不足");
        }
        // 如果当前规格下没有可用箱子
        if (queryIsStandard(order.getBoxStandard(),order.getBoxUnitPrice())==null){
            // 抛出异常
            throw new CustomException("当前规格无空余箱子");
        }
        // 接收获得箱子信息
        BoxInfo boxInfo = randGetBoxInfoByStandard(ParameterUtil.getMapByIdMsg(order.getBoxUnitPrice(),order.getBoxStandard()));
        // 如果没有箱子信息更新条数
        if (updateBoxInfo(boxInfo)<=ERROR){
            // 抛出异常
            throw new CustomException("更新箱子信息失败！！！");
        }
        // 如果没有扣除客户积分
        if(subtractUserPoint(userPoint - boxInfo.getBoxUnitPrice() * order.getLeaseDuration())<=ERROR){
            throw new CustomException("扣除客户积分失败！！！");
        }
        // 实付积分
        order.setTotalPrice(boxInfo.getBoxUnitPrice() * order.getLeaseDuration());
        // 如果没有添加积分记录
        if (insertPoint(boxInfo,order)<=ERROR){
            // 抛出异常
            throw new CustomException("添加积分记录失败！！！");
        }
        return SUCCESS;
    }

    /**
     * 查询当前规格下是否还有未使用的箱子
     * @param boxStandard 箱子规格
     * @return 结果
     */
    private String queryIsStandard(String boxStandard,Long boxUnitPrice){
        // 返回查询结果
        return boxBespeakMapper.queryIsStandard(ParameterUtil.getMapByIdMsg(boxUnitPrice,boxStandard));
    }

    /**
     * 获得当前规格下箱子基本信息
     * @param map 数据集合
     * @return 结果
     */
    private BoxInfo randGetBoxInfoByStandard(Map<String,Object> map){
        // 返回随机获得的箱子基本信息
        return boxBespeakMapper.randGetBoxInfoByStandard(map);
    }

    /**
     * 更新当前的箱子的使用状态和使用人
     * @param boxInfo 箱子信息
     * @return 结果
     */
    private int updateBoxInfo(BoxInfo boxInfo){
        // 更新人
        boxInfo.setUpdateBy(SecurityUtils.getUsername());
        // 更新时间
        boxInfo.setUpdateTime(DateUtils.getNowDate());
        // 使用人id
        boxInfo.setUsedBy(SecurityUtils.getUserId());
        // 设置已使用
        boxInfo.setIsUsed(1);
        // 返回更新结果
        return boxBespeakMapper.updateBoxInfo(boxInfo);
    }

    /**
     * 扣除积分当前用户积分
     * @param point 运算后的积分
     * @return 结果
     */
    private int subtractUserPoint(Long point){
        // 返回扣除积分结果
        return boxBespeakMapper.subtractUserPoint(
                // 封装成map集合
                ParameterUtil.getIdDataUpdateByUpdateTime(
                    // 客户id
                    SecurityUtils.getUserId(),
                    // 剩余积分
                    point,
                    // 更新人
                    SecurityUtils.getUsername(),
                    //更新时间
                    DateUtils.getNowDate()
                )
        );
    }

    /**
     * 创建订单记录
     * @param boxInfo 箱子信息对象
     * @param order 预约对象
     * @return 结果
     */
    private Long createOrderRecord(BoxInfo boxInfo,Order order){
        // 手机端客户id
        order.setUserId(SecurityUtils.getUserId());
        // 当前订单状态
        order.setStatus(1);
        // 生成订单编号
        order.setOrderCode(Long.valueOf(SeqGeneratorUtil.seqGenerator(DateUtils.getNowDateStr(),6)));
        // 箱子id
        order.setBoxId(boxInfo.getId());
        // 箱子编号
        order.setBoxCode(boxInfo.getBoxCode());
        // 创建时间
        order.setCreateTime(DateUtils.getNowDate());
        // 创建人
        order.setCreateBy(SecurityUtils.getUsername());
        // 版本号
        order.setVersion(0L);
        // 未删除
        order.setDelFlag("0");
        // 手机端未删除
        order.setAppDelFlag(0);
        // 后台端为删除
        order.setBackendDelFlag(0);
        // 创建订单
        boxBespeakMapper.createOrderRecord(order);
        // 如果订单id为null
        if (order.getId()==null){
            // 抛出异常
            throw new CustomException("创建订单失败！！！");
        }
        // 如果订单历史记录创建失败
        if (orderHistoryMapper.insertOrderHistory(order.getId()) == ERROR){
            //抛出异常
            throw new CustomException("创建订单历史记录失败！！！");
        }
        // 返回订单id
        return order.getId();
    }

    /**
     * 添加积分使用记录
     * @param boxInfo 箱子信息对象
     * @param order 预约对象
     * @return 结果
     */
    private int insertPoint(BoxInfo boxInfo,Order order){
        Point point = new Point();
        // 用户id
        point.setUserId(SecurityUtils.getUserId());
        // 方式: 3 积分使用
        point.setWay(3);
        // 积分记录
        point.setPoints(boxInfo.getBoxUnitPrice() * order.getLeaseDuration() * -1L);
        // 订单id
        point.setOrderId(createOrderRecord(boxInfo,order));
        // 创建时间
        point.setCreateTime(DateUtils.getNowDate());
        // 创建人
        point.setCreateBy(SecurityUtils.getUsername());
        // 版本号
        point.setVersion(0L);
        // 未删除
        point.setDelFlag("0");
        // 返回添加积分记录结果
        return pointMapper.insertPoint(point);
    }
}

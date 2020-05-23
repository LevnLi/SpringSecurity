package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.common.util.SeqGeneratorUtil;
import com.ruoyi.project.storage.domain.*;
import com.ruoyi.project.storage.mapper.BoxBespeakMapper;
import com.ruoyi.project.storage.mapper.PointMapper;
import com.ruoyi.project.storage.msg.Msg;
import com.ruoyi.project.storage.service.BoxBespeakService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import static com.ruoyi.project.storage.util.InfoUtil.judgeTime;

/**
 * @author :lihao
 * @date :2020/05/09
 * @description :空箱预约service接口实现类
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
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
     * 通过构造方法注入
     * @param pointMapper 积分mapper接口
     * @param boxBespeakMapper 空箱预约mapper接口
     */
    @Autowired
    public BoxBespeakServiceImpl(PointMapper pointMapper, BoxBespeakMapper boxBespeakMapper) {
        this.pointMapper = pointMapper;
        this.boxBespeakMapper = boxBespeakMapper;
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
        return boxBespeakMapper.getStandardList();
    }

    /**
     * 空箱预约
     *
     * @param order 预约对象
     * @return 结果
     */
    @Override
    public int boxOrder(Order order) {
        // 判断预约信息
        judgeBoxBespeakInfo(order);
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
            throw new CustomException("箱子库存不足，请重新选择");
        }
        // 接收获得箱子信息
        BoxInfo boxInfo = randGetBoxInfoByStandard(ParameterUtil.getMapByIdMsg(order.getBoxUnitPrice(),order.getBoxStandard()));
        // 如果没有箱子信息更新条数
        if (updateBoxInfo(boxInfo)<=ERROR){
            // 补偿机制,返回失败,再次预约箱子
            return ERROR;
        }
        // 如果没有扣除客户积分
        if(subtractUserPoint(userPoint - boxInfo.getBoxUnitPrice() * order.getLeaseDuration())<=ERROR){
            throw new CustomException("扣除客户积分失败");
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
        // 空箱上门下单时间
        order.setEmptyBoxOrderTime(DateUtils.getNowDate());
        // 创建时间
        order.setCreateTime(DateUtils.getNowDate());
        // 创建人
        order.setCreateBy(SecurityUtils.getUsername());
        // 更新时间
        order.setUpdateTime(DateUtils.getNowDate());
        // 更新人
        order.setUpdateBy(SecurityUtils.getUsername());
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
            throw new CustomException("提交订单失败，请联系管理员");
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

    /**
     * 判断空箱预约信息是否完整
     * @param order 订单对象
     */
    private void judgeBoxBespeakInfo(Order order){
        if (
            // 订单名称不存在
            order.getOrderName() == null ||
            // 箱子规格不存在或为空
            order.getBoxStandard() == null || "".equals(order.getOrderName())||
            // 箱子积分单价不存在或为空
            order.getBoxUnitPrice() == null || "".equals(order.getBoxStandard()) ||
            // 空箱上门时间不存在或租赁时长小于等于0
            order.getEmptyBoxCallTime() == null || order.getLeaseDuration() <= ERROR ||
            // 空箱上门电话不存在或为空
            order.getEmptyBoxCallPhone() == null || "".equals(order.getEmptyBoxCallPhone()) ||
            // 空箱上门地址不存在或为空
            order.getEmptyBoxCallAddress() == null || "".equals(order.getEmptyBoxCallAddress()) ||
            // 空箱上门时段不存在或为空
            order.getEmptyBoxCallInterval() == null || "".equals(order.getEmptyBoxCallInterval())
        ){
            // 抛异常
            throw new CustomException("缺少预约信息");
        }
        // 如果订单名称过长
        if (order.getOrderName().length() > ORDER_NAME_MAX_LENGTH){
            // 抛异常
            throw new CustomException("订单名称过长");
        }
        // 如果空箱上门姓名过长
        if (order.getEmptyBoxCallName().length() > NAME_MAX_LENGTH){
            // 抛异常
            throw new CustomException("空箱上门姓名过长");
        }
        // 如果空箱上门电话过长
        if (order.getEmptyBoxCallPhone().length() > NUMBER_MAX_LENGTH){
            // 抛异常
            throw new CustomException("空箱上门电话过长");
        }
        // 如果空箱上门地址过长
        if (order.getEmptyBoxCallAddress().length() > ADDRESS_MAX_LENGTH){
            // 抛异常
            throw new CustomException("空箱上门地址过长");
        }
        // 判断上门时间段
        judgeTime(order.getEmptyBoxCallInterval());
    }
}

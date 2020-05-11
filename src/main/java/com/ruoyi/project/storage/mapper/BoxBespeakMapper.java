package com.ruoyi.project.storage.mapper;

import com.ruoyi.project.storage.domain.*;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * @author :lihao
 * @date :2020/05/09
 * @description :空箱预约mapper接口
 */
@Mapper
public interface BoxBespeakMapper {

    /**
     * 获取当前用户默认收货地址
     * @param userId
     * @return map集合
     */
    List<Address> getDefaultAddress(Long userId);

    /**
     * 有效箱子规格选择（非分页）
     * @param userId 用户ID
     * @return 结果
     */
    List<BoxStandardV1> getStandardList(Long userId);

    /**
     * 查询用户当前积分余额
     * @param userId 当前用户id
     * @return 结果
     */
    Long queryUserPoint(Long userId);

    /**
     * 查询当前规格下是否还有未使用的箱子
     * @param map 集合
     * @return 结果
     */
    String queryIsStandard(Map<String,Object> map);

    /**
     * 随机获取一个当前规格未使用的箱子信息
     * @param map 箱子规格
     * @return 结果
     */
    BoxInfo randGetBoxInfoByStandard(Map<String,Object> map);

    /**
     * 登记当前箱子使用人id
     * @param boxInfo 箱子id
     * @return 结果
     */
    int updateBoxInfo(BoxInfo boxInfo);

    /**
     * 创建订单记录
     * @param order 订单对象
     */
    void createOrderRecord(Order order);

    /**
     * 减去客户积分
     * @param map 结合
     * @return 结果
     */
    int subtractUserPoint(Map<String,Object> map);
}

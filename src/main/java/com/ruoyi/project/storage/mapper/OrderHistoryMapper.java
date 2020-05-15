package com.ruoyi.project.storage.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author :lihao
 * @date :2020/05/10
 * @description : 订单历史记录mapper接口
 */
@Mapper
public interface OrderHistoryMapper {

    /**
     * 添加订单历史表
     * @param id 订单ID
     * @return 结果
     */
    int insertOrderHistory(Long id);
}

package com.ruoyi.project.storage.mapper;

import com.ruoyi.project.storage.domain.Point;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/05/03
 * @description :积分记录mapper接口
 */
@Mapper
public interface PointMapper {

    /**
     * 查询积分记录列表
     * @param point 积分实体
     * @return 结果
     */
    List<Point> queryPointList(Point point);

    /**
     * 获取当前用户积分
     * @param userId 客户id
     * @return 查询结果
     */
    Long queryUserPoint(Long userId);

    /**
     * 添加积分记录
     * @param point 积分实体
     * @return 结果
     */
    int insertPoint(Point point);
}

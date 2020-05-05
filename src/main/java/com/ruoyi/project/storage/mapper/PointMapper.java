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
     * @param point
     * @return 结果
     */
    List<Point> queryPointList(Point point);

    /**
     * 查询用户积分
     * @param userId
     * @return 查询结果
     */
    String queryUserPoint(Long userId);

    /**
     * 添加积分记录
     * @param point
     * @return 结果
     */
    int insertPoint(Point point);
}

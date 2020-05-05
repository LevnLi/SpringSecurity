package com.ruoyi.project.storage.service;

import com.ruoyi.project.storage.domain.Point;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/05/03
 * @description : 积分记录service接口
 */
public interface PointService {

    /**
     * 查询积分记录列表
     * @param point
     * @return 结果
     */
    List<Point> queryPointList(Point point);

    /**
     * 查询用户积分
     * @return 返回积分总和
     */
    String queryUserPoint();

    /**
     * 添加积分记录
     * @param point
     * @return 结果
     */
    int insertPoint(Point point);
}

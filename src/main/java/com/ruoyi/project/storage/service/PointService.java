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
     * @param point 积分实体
     * @return 结果
     */
    List<Point> queryPointList(Point point);

    /**
     * 获取当前用户积分
     * @return 返回积分总和
     */
    Long queryUserPoint();

}

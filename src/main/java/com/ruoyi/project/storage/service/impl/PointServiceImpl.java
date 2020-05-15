package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.storage.domain.Point;
import com.ruoyi.project.storage.mapper.PointMapper;
import com.ruoyi.project.storage.msg.Msg;
import com.ruoyi.project.storage.service.PointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/05/03
 * @description : 积分记录service接口实现类
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PointServiceImpl extends Msg implements PointService {

    /**
     * 积分列表Mapper接口
     */
    private final PointMapper pointMapper;

    /**
     * 通过构造方法注入
     * @param pointMapper 积分列表Mapper
     */
    @Autowired
    public PointServiceImpl(PointMapper pointMapper) {
        this.pointMapper = pointMapper;
    }

    /**
     * 查询积分记录列表
     *
     * @param point 积分记录对象
     * @return 结果
     */
    @Override
    public List<Point> queryPointList(Point point) {
        // 传入用户id
        point.setUserId(SecurityUtils.getUserId());
        // 返回查询结果
        return pointMapper.queryPointList(point);
    }

    /**
     * 获取当前用户积分
     * @return 积分
     */
    @Override
    public Long queryUserPoint() {
        // 返回查询结果
        return pointMapper.queryUserPoint(SecurityUtils.getUserId());
    }

}

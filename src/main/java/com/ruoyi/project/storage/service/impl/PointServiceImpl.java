package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.storage.domain.Point;
import com.ruoyi.project.storage.mapper.PointMapper;
import com.ruoyi.project.storage.service.PointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/05/03
 * @description : 积分记录service接口实现类
 */
@Service
@Slf4j
public class PointServiceImpl implements PointService {

    /**
     * 积分列表Mapper接口
     */
    private final PointMapper pointMapper;

    /**
     * 通过构造方法注入
     * @param pointMapper
     */
    public PointServiceImpl(PointMapper pointMapper) {
        this.pointMapper = pointMapper;
    }

    /**
     * 查询积分记录列表
     *
     * @param point
     * @return 结果
     */
    @Override
    public List<Point> queryPointList(Point point) {
        // 传入用户id
        point.setUserId(SecurityUtils.getUserId());
        // 返回查询结果
        return pointMapper.queryPointList(point);
    }

    @Override
    public String queryUserPoint() {
        // 返回查询结果
        return pointMapper.queryUserPoint(SecurityUtils.getUserId());
    }

    /**
     * 添加积分记录
     *
     * @param point
     * @return 结果
     */
    @Override
    public int insertPoint(Point point) {
        // 创建时间
        point.setCreateTime(DateUtils.getNowDate());
        // 版本号
        point.setVersion(0L);
        // 未删除
        point.setDelFlag("0");
        // 修改条数
        int count = pointMapper.insertPoint(point);
        // 乐观锁判断
        if (count == 0){
            log.error("PointServiceImpl.insertPoint failed: 乐观锁");
            // 抛出异常标记乐观锁
            throw new CustomException("积分记录失败，请刷新后重试", HttpStatus.ERROR);
        }
        // 返回修改条数
        return count;
    }

}

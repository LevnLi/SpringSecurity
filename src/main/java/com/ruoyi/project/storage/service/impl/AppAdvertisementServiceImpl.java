package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.storage.domain.Advertisement;
import com.ruoyi.project.storage.domain.Point;
import com.ruoyi.project.storage.mapper.AppAdvertisementMapper;
import com.ruoyi.project.storage.mapper.PointMapper;
import com.ruoyi.project.storage.msg.Msg;
import com.ruoyi.project.storage.service.AppAdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/04/28
 * @description :手机端广告service接口实现类
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AppAdvertisementServiceImpl extends Msg implements AppAdvertisementService {

    /**
     * 手机端广告Mapper接口
     */
    private final AppAdvertisementMapper appAdvertisementMapper;

    /**
     * 积分记录mapper接口
     */
    private final PointMapper pointMapper;

    /**
     * 通过构造方法注入
     * @param appAdvertisementMapper 手机端广告mapper
     * @param pointMapper 积分记录mapper
     */
    @Autowired
    public AppAdvertisementServiceImpl(AppAdvertisementMapper appAdvertisementMapper, PointMapper pointMapper) {
        this.appAdvertisementMapper = appAdvertisementMapper;
        this.pointMapper = pointMapper;
    }

    /**
     * 查询广告列表
     * @return 广告集合
     */
    @Override
    public List<Advertisement> selectAdvertisementList() {
        // 返回广告列表数据
        return appAdvertisementMapper.selectAdvertisementList();
    }

    /**
     * 广告积分获取
     *
     * @param advertisement 广告对象
     * @return 结果
     */
    @Override
    public int getAdvertisementPoints(Advertisement advertisement) {
        // 广告不存在或积分错误
        if (appAdvertisementMapper.queryByIdPoints(advertisement.getId(),advertisement.getPoints()) == null){
            // 抛出异常
            throw new CustomException("广告不存在或积分错误");
        }
        // 返回操作条数
        int count =  appAdvertisementMapper.getAdvertisementPoints(
                        // 封装map集合
                        ParameterUtil.getIdDataUpdateByUpdateTime(
                                // 客户id
                                SecurityUtils.getUserId(),
                                // 可获积分
                                advertisement.getPoints(),
                                // 更新人
                                SecurityUtils.getUsername(),
                                // 更新时间
                                DateUtils.getNowDate()));
        // 如果更新客户积分失败
        if (count == ERROR){
            // 抛出异常
            throw new CustomException("积分获取失败，请联系管理员");
        }
        // 调用添加积分记录方法
        insertPoint(advertisement.getId(),advertisement.getPoints());
        // 返回成功信息
        return SUCCESS;
    }

    /**
     * 添加积分记录
     * @param id 广告id
     * @param points 可获积分
     */
    private void insertPoint(Long id, Long points){
        // new一个积分对象
        Point point = new Point();
        // 创建人
        point.setCreateBy(SecurityUtils.getUsername());
        // 创建时间
        point.setCreateTime(DateUtils.getNowDate());
        // 客户id
        point.setUserId(SecurityUtils.getUserId());
        // 广告id
        point.setAdvertisementId(id);
        // 获取方式
        point.setWay(2);
        // 获得积分
        point.setPoints(points);
        // 版本号
        point.setVersion(0L);
        // 未删除
        point.setDelFlag("0");
        // 向积分表添加记录
        if (pointMapper.insertPoint(point) == ERROR){
            // 抛出异常
            throw new CustomException("积分获取失败，请联系管理员");
        }
    }
}

package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.storage.domain.Advertisement;
import com.ruoyi.project.storage.mapper.AppAdvertisementMapper;
import com.ruoyi.project.storage.service.AppAdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/04/28
 * @description :手机端广告service接口实现类
 */
@Service
@Slf4j
public class AppAdvertisementServiceImpl implements AppAdvertisementService {

    /**
     * 广告Mapper接口
     */
    private final AppAdvertisementMapper appAdvertisementMapper;

    /**
     * 通过构造方法注入
     * @param appAdvertisementMapper
     */
    @Autowired
    public AppAdvertisementServiceImpl(AppAdvertisementMapper appAdvertisementMapper) {
        this.appAdvertisementMapper = appAdvertisementMapper;
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
     * @param id     广告id
     * @param points 可获积分
     * @return 结果
     */
    @Override
    public int getAdvertisementPoints(Long id, Long points) {
        // 返回操作条数
        return appAdvertisementMapper.getAdvertisementPoints(ParameterUtil.getMapByMsg(SecurityUtils.getUserId(),points,SecurityUtils.getUsername()));
    }
}

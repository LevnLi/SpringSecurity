package com.ruoyi.project.storage.service;

import com.ruoyi.project.storage.domain.Advertisement;

import java.util.List;

/**
 * @author :lihao
 * @date :2020/04/28
 * @description :手机端广告Service接口
 */
public interface AppAdvertisementService {

    /**
     * 查询广告列表
     * @return 广告集合
     */
    List<Advertisement> selectAdvertisementList();

    /**
     * 广告积分获取
     * @param advertisement 广告对象
     * @return 结果
     */
    int getAdvertisementPoints(Advertisement advertisement);

}

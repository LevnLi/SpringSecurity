package com.ruoyi.project.storage.backend.advertisement.service;

import com.ruoyi.project.storage.backend.advertisement.domain.Advertisement;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :lihao
 * @date :2020/04/28
 * @description :广告Service接口
 */
public interface AdvertisementService {

    /**
     * 查询广告列表
     * @param advertisement 广告
     * @return 广告集合
     */
    List<Advertisement> selectAdvertisementList(Advertisement advertisement);

    /**
     * 新增广告
     * @param advertisement 广告
     * @return 结果
     */
    int insertAdvertisement(Advertisement advertisement);

    /**
     * 修改广告
     * @param advertisement 广告
     * @return 结果
     */
    int updateAdvertisement(Advertisement advertisement);

    /**
     * 批量删除广告
     * @param ids 需要删除的广告ID数组
     * @return 结果
     */
    int deleteAdvertisementByIds(Long[] ids);

    /**
     * 批量启用/停用广告
     * @param operate 操作类型（“enable”: 启用; "disable": 停用
     * @param ids     需要启用/停用的广告ID数组
     * @return 结果
     */
    int operateAdvertisementByIds(String operate,Long[] ids);
}

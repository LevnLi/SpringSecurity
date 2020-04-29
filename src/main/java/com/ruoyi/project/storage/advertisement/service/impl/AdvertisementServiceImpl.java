package com.ruoyi.project.storage.advertisement.service.impl;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.storage.advertisement.domain.Advertisement;
import com.ruoyi.project.storage.advertisement.mapper.AdvertisementMapper;
import com.ruoyi.project.storage.advertisement.service.AdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/04/28
 * @description :广告service接口实现类
 */
@Service
@Slf4j
public class AdvertisementServiceImpl implements AdvertisementService {

    /**
     * 广告Mapper
     */
    @Resource
    private AdvertisementMapper advertisementMapper;

    /**
     * 查询广告列表
     * @param advertisement 广告
     * @return 广告集合
     */
    @Override
    public List<Advertisement> selectAdvertisementList(Advertisement advertisement) {
        // 返回广告列表数据
        return advertisementMapper.selectAdvertisementList(advertisement);
    }

    /**
     * 新增广告
     * @param advertisement 广告
     * @return 结果
     */
    @Override
    public int insertAdvertisement(Advertisement advertisement) {
        // 设置创建时间
        advertisement.setCreateTime(DateUtils.getNowDate());
        // 设置创建人
        advertisement.setCreateBy(SecurityUtils.getUsername());
        // 设置启用
        advertisement.setIsEnable(0);
        // 设置版本号
        advertisement.setVersion(0L);
        // 设置未删除
        advertisement.setDelFlag("0");
        // 返回插入条数
        return advertisementMapper.insertAdvertisement(advertisement);
    }

    /**
     * 修改广告
     * @param advertisement 广告
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAdvertisement(Advertisement advertisement) {
        // 设置更新时间
        advertisement.setUpdateTime(DateUtils.getNowDate());
        // 设置更新人
        advertisement.setUpdateBy(SecurityUtils.getUsername());
        // 修改条数
        int count = advertisementMapper.updateAdvertisement(advertisement);
        // 乐观锁判断
        if (count == 0){
            log.error("AdvertisementServiceImpl.updateAdvertisement failed: 乐观锁");
            // 抛出异常标记乐观锁
            throw new CustomException("广告【" + advertisement.getTitle() + "】已被他人率先修改，请刷新后重试", HttpStatus.ERROR);
        }
        // 返回修改条数
        return count;
    }

    /**
     * 批量删除广告
     * @param ids 需要删除的广告ID数组
     * @return 结果
     */
    @Override
    public int deleteAdvertisementByIds(Long[] ids) {
        return advertisementMapper.deleteAdvertisementByIds(ParameterUtil.getBatchUpdateMapByIds(ids));
    }

    @Override
    public int operateAdvertisementByIds(String operate, Long[] ids) {
        return advertisementMapper.operateAdvertisementByIds(ParameterUtil.getBatchUpdateMapByOperateIds(operate, ids));
    }
}

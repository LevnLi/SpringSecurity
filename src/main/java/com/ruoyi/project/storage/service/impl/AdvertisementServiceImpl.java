package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.storage.domain.Advertisement;
import com.ruoyi.project.storage.mapper.AdvertisementMapper;
import com.ruoyi.project.storage.msg.Msg;
import com.ruoyi.project.storage.service.AdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/04/28
 * @description :广告service接口实现类
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AdvertisementServiceImpl extends Msg implements AdvertisementService {

    /**
     * 广告Mapper接口
     */
    private final AdvertisementMapper advertisementMapper;

    /**
     * 通过构造方法注入
     * @param advertisementMapper 广告Mapper
     */
    @Autowired
    public AdvertisementServiceImpl(AdvertisementMapper advertisementMapper) {
        this.advertisementMapper = advertisementMapper;
    }

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
        // 如果可获积分小于等于0
        if (advertisement.getPoints().intValue() <= ERROR){
            // 抛出异常
            throw new CustomException("广告可获得积分不能小于等于0");
        }
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
        int count = advertisementMapper.insertAdvertisement(advertisement);
        // 如果更新客户积分失败
        if (count == ERROR){
            // 抛出异常
            throw new CustomException("新增广告信息失败");
        }
        // 返回成功
        return SUCCESS;
    }

    /**
     * 修改广告
     * @param advertisement 广告
     * @return 结果
     */
    @Override
    public int updateAdvertisement(Advertisement advertisement) {
        // 如果可获积分小于等于0
        if (advertisement.getPoints().intValue() <= ERROR){
            // 抛出异常
            throw new CustomException("广告可获得积分不能小于等于0");
        }
        // 设置更新时间
        advertisement.setUpdateTime(DateUtils.getNowDate());
        // 设置更新人
        advertisement.setUpdateBy(SecurityUtils.getUsername());
        // 修改条数
        int count = advertisementMapper.updateAdvertisement(advertisement);
        // 如果更新客户积分失败
        if (count == ERROR){
            // 抛出异常
            throw new CustomException("新增广告信息失败");
        }
        // 返回修改条数
        return SUCCESS;
    }

    /**
     * 批量删除广告
     * @param ids 需要删除的广告ID数组
     * @return 结果
     */
    @Override
    public int deleteAdvertisementByIds(Long[] ids) {
        // 定义变量接收更新条数
        int count = advertisementMapper.deleteAdvertisementByIds(ParameterUtil.getBatchUpdateMapByIds(ids));
        // 如果更新客户积分失败
        if (count != ids.length){
            // 抛出异常
            throw new CustomException("新增广告信息失败");
        }
        // 返回成功
        return SUCCESS;
    }

    /**
     * 停用或启用广告
     * @param operate 操作类型（“enable”: 启用; "disable": 停用
     * @param ids     需要启用/停用的广告ID数组
     * @return 结果
     */
    @Override
    public int operateAdvertisementByIds(String operate, Long[] ids) {
        // 如果是停用指令
        if (DISABLE.equals(operate)){
            // 定义变量接收操作条数
            int count = advertisementMapper.operateAdvertisementByIds(ParameterUtil.getBatchUpdateMapByOperateIds(operate, ids));
            // 如果更新条数不等于数组长度
            if (count != ids.length){
                // 抛异常
                throw new CustomException("停用失败");
            }
            // 返回成功信息
            return SUCCESS;
        }
        // 如果是启用指令
        if (ENABLE.equals(operate)){
            // 定义变量接收操作条数
            int count = advertisementMapper.operateAdvertisementByIds(ParameterUtil.getBatchUpdateMapByOperateIds(operate, ids));
            // 如果更新条数不等于数组长度
            if (count != ids.length){
                // 抛异常
                throw new CustomException("启用失败");
            }
            // 返回成功信息
            return SUCCESS;
        }
        // 返回成功
        return SUCCESS;
    }
}

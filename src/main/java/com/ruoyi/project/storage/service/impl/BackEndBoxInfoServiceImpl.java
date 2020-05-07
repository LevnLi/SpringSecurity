package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.MathUtils;
import com.ruoyi.project.storage.domain.BoxInfo;
import com.ruoyi.project.storage.mapper.BackEndBoxInfoMapper;
import com.ruoyi.project.storage.service.BackEndBoxInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author :lihao
 * @date :2020/05/06
 * @description :箱子信息service实现类
 */
@Service
@Slf4j
public class BackEndBoxInfoServiceImpl implements BackEndBoxInfoService {

    /**
     * 箱子信息
     */
    private final BackEndBoxInfoMapper backEndBoxInfoMapper;

    /**
     * 通过构造方法注入
     * @param backEndBoxInfoMapper
     */
    @Autowired
    public BackEndBoxInfoServiceImpl(BackEndBoxInfoMapper backEndBoxInfoMapper) {
        this.backEndBoxInfoMapper = backEndBoxInfoMapper;
    }

    /**
     * 添加箱子信息
     *
     * @param boxInfo
     * @return
     */
    @Override
    public int insertBoxInfo(BoxInfo boxInfo) {
        // 箱子编号: 年月日+随机六位数
        boxInfo.setBoxCode(Long.valueOf(DateUtils.getNowDateStr() + MathUtils.randomDigitNumber(6)));
        // 未使用
        boxInfo.setIsUsed(0);
        // 创建时间
        boxInfo.setCreateTime(DateUtils.getNowDate());
        // 创建人
        boxInfo.setCreateBy(SecurityUtils.getUsername());
        // 版本号
        boxInfo.setVersion(0L);
        // 未删除
        boxInfo.setDelFlag("0");
        // 序号
        boxInfo.setSortNo(1L);
        // 返回添加条数
        return backEndBoxInfoMapper.insertBoxInfo(boxInfo);
    }
}

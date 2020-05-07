package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.common.util.SeqGeneratorUtil;
import com.ruoyi.project.storage.domain.BoxInfo;
import com.ruoyi.project.storage.domain.BoxStandard;
import com.ruoyi.project.storage.mapper.BackEndBoxInfoMapper;
import com.ruoyi.project.storage.service.BackEndBoxInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * 箱子信息列表（分页）
     *
     * @param boxInfo
     * @return 结果
     */
    @Override
    public List<BoxInfo> queryBoxInfoList(BoxInfo boxInfo) {
        return backEndBoxInfoMapper.queryBoxInfoList(boxInfo);
    }

    /**
     * 删除箱子信息
     *
     * @param ids
     * @return 结果
     */
    @Override
    public int deleteBoxInfo(Long[] ids) {
        return backEndBoxInfoMapper.deleteBoxInfo(ParameterUtil.getBatchUpdateMapByIds(ids));
    }

    /**
     * 添加箱子信息
     *
     * @param boxInfo
     * @return 结果
     */
    @Override
    public int insertBoxInfo(BoxInfo boxInfo) {
        // 箱子编号: 年月日+随机六位数
        boxInfo.setBoxCode(Long.valueOf(SeqGeneratorUtil.seqGenerator(DateUtils.getNowDateStr(),6)));
        // 未使用
        boxInfo.setIsUsed(0);
        // 创建时间
        boxInfo.setCreateTime(DateUtils.getNowDate());
        // 创建人
        boxInfo.setCreateBy("admin");
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

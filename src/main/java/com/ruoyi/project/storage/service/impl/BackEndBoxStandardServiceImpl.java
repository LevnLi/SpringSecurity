package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.storage.domain.BoxStandard;
import com.ruoyi.project.storage.domain.BoxStandardV0;
import com.ruoyi.project.storage.mapper.BackEndBoxStandardMapper;
import com.ruoyi.project.storage.service.BackEndBoxStandardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/05/06
 * @description :箱子规格service接口实现类
 */
@Service
@Slf4j
public class BackEndBoxStandardServiceImpl implements BackEndBoxStandardService {

    /**
     * 箱子规格mapper接口
     */
    private final BackEndBoxStandardMapper backEndBoxStandardMapper;

    /**
     * 通过构造方法注入
     * @param backEndBoxStandardMapper
     */
    @Autowired
    public BackEndBoxStandardServiceImpl(BackEndBoxStandardMapper backEndBoxStandardMapper) {
        this.backEndBoxStandardMapper = backEndBoxStandardMapper;
    }


    /**
     * 箱子规格列表（分页）
     *
     * @param boxStandard
     * @return 结果
     */
    @Override
    public List<BoxStandard> queryBoxStandardList(BoxStandard boxStandard) {
        // 返回查询分页列表
        return backEndBoxStandardMapper.queryBoxStandardList(boxStandard);
    }

    /**
     * 箱子规格新增
     *
     * @param boxStandard
     * @return 结果
     */
    @Override
    public int insertBoxStandard(BoxStandard boxStandard) {
        // 创建时间
        boxStandard.setCreateTime(DateUtils.getNowDate());
        // 创建人
        boxStandard.setCreateBy(SecurityUtils.getUsername());
        // 版本号
        boxStandard.setVersion(0L);
        // 未删除
        boxStandard.setDelFlag("0");
        // 返回插入条数
        return backEndBoxStandardMapper.insertBoxStandard(boxStandard);
    }

    /**
     * 通过id查询当前箱子规格是否使用、存在
     *
     * @param id
     * @return
     */
    @Override
    public String queryBoxStandardById(Long id) {
        /**
         * 不存在返回: null
         * 存在返回: 1
         */
        return backEndBoxStandardMapper.queryBoxStandardById(id);
    }

    /**
     * 箱子规格删除
     *
     * @param boxStandard
     * @return 结果
     */
    @Override
    public int deleteBoxStandard(BoxStandard boxStandard) {
        // 修改人
        boxStandard.setUpdateBy("admin");
        // 修改时间
        boxStandard.setUpdateTime(DateUtils.getNowDate());
        // 返回修改条数
        return backEndBoxStandardMapper.deleteBoxStandard(boxStandard);
    }

    /**
     * 箱子规格下拉列表（非分页）
     *
     * @return 结果
     */
    @Override
    public List<BoxStandardV0> selectBoxStandard() {
        // 返回箱子规格下拉列表
        return backEndBoxStandardMapper.selectBoxStandard();
    }

    /**
     * 通过规格和积分查是否存在
     *
     * @param boxStandard  规格
     * @param boxUnitPrice 积分单价
     * @return 结果
     */
    @Override
    public String queryBox(String boxStandard, Long boxUnitPrice) {
        /**
         * 不存在返回: null
         * 存在返回: 1
         */
        return backEndBoxStandardMapper.queryBox(ParameterUtil.getMapByIdMsg(boxUnitPrice,boxStandard));
    }
}

package com.ruoyi.project.storage.service;

import com.ruoyi.project.storage.domain.BoxStandard;
import com.ruoyi.project.storage.domain.BoxStandardV0;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/05/06
 * @description :箱子规格service接口
 */
public interface BackEndBoxStandardService {

    /**
     * 箱子规格列表（分页）
     * @param boxStandard 箱子规格对象
     * @return 结果
     */
    List<BoxStandard> queryBoxStandardList(BoxStandard boxStandard);

    /**
     * 箱子规格新增
     * @param boxStandard 箱子规格对象
     * @return 结果
     */
    int insertBoxStandard(BoxStandard boxStandard);

    /**
     * 箱子规格删除
     * @param ids 箱子规格id数组
     * @return 结果
     */
    int deleteBoxStandard(Long[] ids);

    /**
     * 箱子规格下拉列表（非分页）
     * @return 结果
     */
    List<BoxStandardV0> selectBoxStandard();

}

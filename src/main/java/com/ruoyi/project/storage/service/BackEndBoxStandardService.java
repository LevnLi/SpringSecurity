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
     * 通过id查询当前箱子规格是否使用
     * @param id 箱子规格id
     * @return 结果
     */
    String queryBoxStandardById(Long id);

    /**
     * 箱子规格删除
     * @param id 箱子规格id
     * @return 结果
     */
    int deleteBoxStandard(Long id);

    /**
     * 箱子规格下拉列表（非分页）
     * @return 结果
     */
    List<BoxStandardV0> selectBoxStandard();

    /**
     * 通过规格和积分查是否存在
     * @param boxStandard 规格
     * @param boxUnitPrice 积分单价
     * @return 结果
     */
    String queryBox(String boxStandard, Long boxUnitPrice);
}

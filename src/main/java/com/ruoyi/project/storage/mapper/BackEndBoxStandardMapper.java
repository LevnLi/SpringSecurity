package com.ruoyi.project.storage.mapper;

import com.ruoyi.project.storage.domain.BoxStandard;
import com.ruoyi.project.storage.domain.BoxStandardV0;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * @author :lihao
 * @date :2020/05/06
 * @description :箱子规格mapper接口
 */
@Mapper
public interface BackEndBoxStandardMapper {
    /**
     * 箱子规格列表（分页）
     * @param boxStandard
     * @return 结果
     */
    List<BoxStandard> queryBoxStandardList(BoxStandard boxStandard);

    /**
     * 箱子规格新增
     * @param boxStandard
     * @return 结果
     */
    int insertBoxStandard(BoxStandard boxStandard);

    /**
     * 通过id查询当前箱子规格是否使用
     * @param ids
     * @return
     */
    String queryBoxStandardById(Long id);

    /**
     * 箱子规格删除
     * @param map
     * @return 结果
     */
    int deleteBoxStandard(Map<String,Object> map);

    /**
     * 箱子规格下拉列表（非分页）
     * @return 结果
     */
    List<BoxStandardV0> selectBoxStandard();

    /**
     * 通过规格和积分查是否存在
     * @param map
     * @return 结果
     */
    String queryBox(Map<String, Object> map);
}

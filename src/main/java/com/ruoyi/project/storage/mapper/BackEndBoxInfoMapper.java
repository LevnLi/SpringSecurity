package com.ruoyi.project.storage.mapper;

import com.ruoyi.project.storage.domain.BoxInfo;
import com.ruoyi.project.storage.domain.BoxStandard;

import java.util.List;
import java.util.Map;

/**
 * @author :lihao
 * @date :2020/05/06
 * @description :箱子信息mapper接口
 */
public interface BackEndBoxInfoMapper {

    /**
     * 箱子信息列表（分页）
     * @param boxInfo
     * @return 结果
     */
    List<BoxInfo> queryBoxInfoList(BoxInfo boxInfo);

    /**
     * 删除箱子信息
     * @param map
     * @return 结果
     */
    int deleteBoxInfo(Map<String,Object> map);

    /**
     * 添加箱子信息
     * @param list
     * @return 结果
     */
    int insertBoxInfo(List<BoxInfo> list);
}

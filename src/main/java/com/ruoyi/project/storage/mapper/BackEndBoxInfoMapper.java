package com.ruoyi.project.storage.mapper;

import com.ruoyi.project.storage.domain.BoxInfo;

/**
 * @author :lihao
 * @date :2020/05/06
 * @description :箱子信息mapper接口
 */
public interface BackEndBoxInfoMapper {

    /**
     * 添加箱子信息
     * @param boxInfo
     * @return
     */
    int insertBoxInfo(BoxInfo boxInfo);
}

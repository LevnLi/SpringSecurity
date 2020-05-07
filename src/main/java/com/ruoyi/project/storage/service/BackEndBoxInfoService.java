package com.ruoyi.project.storage.service;

import com.ruoyi.project.storage.domain.BoxInfo;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/05/06
 * @description :箱子信息service接口
 */
public interface BackEndBoxInfoService {

    /**
     * 箱子信息列表（分页）
     * @param boxInfo
     * @return 结果
     */
    List<BoxInfo> queryBoxInfoList(BoxInfo boxInfo);

    /**
     * 删除箱子信息
     * @param ids
     * @return
     */
    int deleteBoxInfo(Long[] ids);

    /**
     * 添加箱子信息
     * @param boxInfo
     * @return
     */
    int insertBoxInfo(BoxInfo boxInfo);


}

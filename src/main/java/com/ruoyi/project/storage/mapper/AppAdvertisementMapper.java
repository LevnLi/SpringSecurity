package com.ruoyi.project.storage.mapper;

import com.ruoyi.project.storage.domain.Advertisement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * @author :lihao
 * @date :2020/04/28
 * @description : 手机端广告mapper接口
 */
@Mapper
public interface AppAdvertisementMapper {

    /**
     * 查询广告列表
     * @return 广告集合
     */
    List<Advertisement> selectAdvertisementList();

    /**
     * 广告积分获取
     * @param map
     * @return 结果
     */
    int getAdvertisementPoints(Map<String,Object> map);

}

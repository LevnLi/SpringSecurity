package com.ruoyi.project.storage.mapper;

import com.ruoyi.project.storage.domain.Personal;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author :lihao
 * @date :2020/05/04
 * @description :个人Mapper接口
 */
@Mapper
public interface PersonalMapper {

    /**
     * 查询旧密码
     * @param map
     * @return 返回结果
     */
    String queryOldPassword(Map<String, Object> map);

    /**
     * 更新密码
     * @param map
     * @return 返回结果
     */
    int updatePassword (Map<String, Object> map);
}

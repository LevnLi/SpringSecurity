package com.ruoyi.project.storage.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author :lihao
 * @date :2020/05/19
 * @description :用户角色mapper接口
 */
public interface UserRoleMapper {

    /**
     * 添加用户角色信息
     * @param userId 用户id
     * @param roleId 角色
     * @return 结果
     */
    int insertUserRole(@Param("userId") Long userId,@Param("roleId") Long roleId);
}

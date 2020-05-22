package com.ruoyi.project.storage.mapper;

import com.ruoyi.project.storage.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author :lihao
 * @date :2020/05/04
 * @description :密码mapper接口
 */
@Mapper
public interface PasswordMapper {

    /**
     * 查询旧密码
     * @param userId 用户id
     * @return 返回结果
     */
    String queryOldPassword(Long userId);

    /**
     * 更新密码
     * @param user 用户对象
     * @return 返回结果
     */
    int updatePassword (User user);
}

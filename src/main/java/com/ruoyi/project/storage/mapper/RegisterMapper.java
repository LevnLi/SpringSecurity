package com.ruoyi.project.storage.mapper;

import com.ruoyi.project.storage.domain.Register;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author :lihao
 * @date :2020/05/04
 * @description : 注册mapper接口
 */
@Mapper
public interface RegisterMapper {

    /**
     * 手机用户注册
     * @param register
     * @return 结果
     */
    int registerUser(Register register);

    /**
     * 账号是否存在
     * @param userName
     * @return 结果
     */
    String queryByUserName(String userName);

    /**
     * 姓名是否存在
     * @param phonenumber
     * @return 结果
     */
    String queryByPhoneNumber(String phonenumber);

    /**
     * 邮箱是否存在
     * @param email
     * @return 结果
     */
    String queryByEmail(String email);

    /**
     * 通过id查用户名
     * @param userName
     * @return 结果
     */
    Long queryIdByName(String userName);
}

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
     * @param register 注册对象
     * @return 结果
     */
    int registerUser(Register register);

    /**
     * 账号是否存在
     * @param userName 账号
     * @return 结果
     */
    String queryByUserName(String userName);

    /**
     * 电话是否存在
     * @param phonenumber 电话号码
     * @return 结果
     */
    String queryByPhoneNumber(String phonenumber);

    /**
     * 邮箱是否存在
     * @param email 邮箱
     * @return 结果
     */
    String queryByEmail(String email);

}

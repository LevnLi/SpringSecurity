package com.ruoyi.project.storage.service;

import com.ruoyi.project.storage.domain.Register;

/**
 * @author :lihao
 * @date :2020/05/04
 * @description : 手机端注册service接口
 */
public interface RegisterService {

    /**
     * 手机用户注册
     * @param register
     * @return 结果
     */
    int registerUser(Register register);

    /**
     * 账号是否存在
     * @param register
     * @return 结果
     */
    String queryByUserName(Register register);

    /**
     * 姓名是否存在
     * @param register
     * @return 结果
     */
    String queryByPhoneNumber(Register register);

    /**
     * 邮箱是否存在
     * @param register
     * @return 结果
     */
    String queryByEmail(Register register);

    /**
     * 通过id查用户名
     * @param register
     * @return 结果
     */
    Long queryIdByName(Register register);

}

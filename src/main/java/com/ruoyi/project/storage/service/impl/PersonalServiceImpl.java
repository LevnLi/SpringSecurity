package com.ruoyi.project.storage.service.impl;

import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.storage.domain.Personal;
import com.ruoyi.project.storage.mapper.PersonalMapper;
import com.ruoyi.project.storage.service.PersonalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author :lihao
 * @date :2020/05/04
 * @description : 个人service接口实现类
 */
@Service
@Slf4j
public class PersonalServiceImpl implements PersonalService {

    /**
     * 个人Mapper接口
     */
    private final PersonalMapper personalMapper;

    /**
     * 通过构造方法注入
     * @param personalMapper
     */
    @Autowired
    public PersonalServiceImpl(PersonalMapper personalMapper) {
        this.personalMapper = personalMapper;
    }


    /**
     * 查询旧密码
     *
     * @param id          用户id
     * @param userType    用户类型
     * @return 返回结果
     */
    @Override
    public String queryOldPassword(Long id,String userType) {
        // 返回查询结果
        return personalMapper.queryOldPassword(ParameterUtil.getMapByOperateIds(id,null,userType));
    }

    /**
     * 更新密码
     *
     * @param id          用户id
     * @param newPassword 新密码
     * @param userType    用户类型
     * @return 返回结果
     */
    @Override
    public int updatePassword(Long id, String newPassword, String userType) {
        // 返回操作条数
        return personalMapper.updatePassword(ParameterUtil.getMapByOperateIds(id, newPassword, userType));
    }
}

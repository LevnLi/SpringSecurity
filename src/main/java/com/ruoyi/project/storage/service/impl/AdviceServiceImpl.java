package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.storage.domain.Advice;
import com.ruoyi.project.storage.mapper.AdviceMapper;
import com.ruoyi.project.storage.service.AdviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/04/29
 * @description : 意见service接口实现类
 */
@Service
@Slf4j
public class AdviceServiceImpl implements AdviceService {

    /**
     * 意见mapper接口
     */
    private final AdviceMapper adviceMapper;

    /**
     * 通过构造方法注入
     * @param adviceMapper
     */
    @Autowired
    public AdviceServiceImpl(AdviceMapper adviceMapper) {
        this.adviceMapper = adviceMapper;
    }

    /**
     * 查询未删除的意见建议
     * @param advice
     * @return 查询结果
     */
    @Override
    public List<Advice> queryAllAdvice(Advice advice) {
        // 返回查询结果
        return adviceMapper.queryAllAdvice(advice);
    }

    /**
     * 插入一条建议
     * @param advice
     * @return
     */
    @Override
    public int insertAdvice(Advice advice) {
        // 设置用户id
        advice.setUserId(SecurityUtils.getUserId());
        // 设置创建时间
        advice.setCreateTime(DateUtils.getNowDate());
        // 设置创建人
        advice.setCreateBy(SecurityUtils.getUsername());
        // 设置版本号
        advice.setVersion(0L);
        // 设置未删除
        advice.setDelFlag("0");
        // 返回插入条数
        return adviceMapper.insertAdvice(advice);
    }
}

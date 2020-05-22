package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.storage.domain.Advice;
import com.ruoyi.project.storage.mapper.AdviceMapper;
import com.ruoyi.project.storage.msg.Msg;
import com.ruoyi.project.storage.service.AdviceService;
import com.ruoyi.project.storage.util.InfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.ruoyi.project.storage.msg.Msg.TITLE_MAX_LENGTH;

/**
 * @author :lihao
 * @date :2020/04/29
 * @description : 意见service接口实现类
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AdviceServiceImpl extends Msg implements AdviceService {

    /**
     * 意见mapper接口
     */
    private final AdviceMapper adviceMapper;

    /**
     * 通过构造方法注入
     * @param adviceMapper 意见mapper
     */
    @Autowired
    public AdviceServiceImpl(AdviceMapper adviceMapper) {
        this.adviceMapper = adviceMapper;
    }

    /**
     * 查询未删除的意见建议
     * @param advice 意见实体
     * @return 查询结果
     */
    @Override
    public List<Advice> queryAllAdvice(Advice advice) {
        // 如果搜索值不为空
        if (advice.getSearchValue()!=null){
            // 如果存在非法字符
            if (InfoUtil.isHaveIllegalChar(advice.getSearchValue())){
                // 抛出异常
                throw new CustomException("存在非法字符");
            }
        }
        // 返回查询结果
        return adviceMapper.queryAllAdvice(advice);
    }

    /**
     * 插入一条建议
     * @param advice 意见实体
     * @return 结果
     */
    @Override
    public int insertAdvice(Advice advice) {
        // 校验输入信息
        adviceInfo(advice);
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
        // 如果插入失败
        if (adviceMapper.insertAdvice(advice) == ERROR){
            // 抛出异常
            throw new CustomException("新增失败");
        }
        // 返回成功信息
        return SUCCESS;
    }

    /**
     * 意见信息判断
     * @param advice 意见对象
     */
    private void adviceInfo(Advice advice){
        if (
            // 标题为空或不存在
            advice.getTitle() == null || "".equals(advice.getTitle()) ||
            // 内容为空或不存在
            advice.getContent() == null || "".equals(advice.getContent())
        ){
            // 抛异常
            throw new CustomException("标题/内容为空");
        }
        // 如果标题过长
        if (advice.getTitle().length()>TITLE_MAX_LENGTH){
            // 抛异常
            throw new CustomException("标题过长");
        }
        // 如果内容过长
        if (advice.getContent().length()>CONTENT_MAX_LENGTH){
            // 抛异常
            throw new CustomException("内容过长");
        }
    }
}

package com.ruoyi.project.storage.mapper;

import com.ruoyi.project.storage.domain.Advice;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/04/29
 * @description : 意见建议mapper接口
 */
@Mapper
public interface AdviceMapper {

    /**
     * 查询所有的意见建议
     *
     * @param advice
     * @return 意见建议集合
     */
    List<Advice> queryAllAdvice(Advice advice);

    /**
     * 插入一条意见
     * @param advice
     * @return
     */
    int insertAdvice(Advice advice);
}

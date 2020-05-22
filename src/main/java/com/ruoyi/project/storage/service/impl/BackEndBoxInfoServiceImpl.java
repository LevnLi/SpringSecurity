package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.common.util.SeqGeneratorUtil;
import com.ruoyi.project.storage.domain.BoxInfo;
import com.ruoyi.project.storage.domain.BoxStandard;
import com.ruoyi.project.storage.domain.BoxStandardV1;
import com.ruoyi.project.storage.mapper.BackEndBoxInfoMapper;
import com.ruoyi.project.storage.mapper.BackEndBoxStandardMapper;
import com.ruoyi.project.storage.msg.Msg;
import com.ruoyi.project.storage.service.BackEndBoxInfoService;
import com.ruoyi.project.storage.util.InfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :lihao
 * @date :2020/05/06
 * @description :箱子信息service实现类
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BackEndBoxInfoServiceImpl extends Msg implements BackEndBoxInfoService {

    /**
     * 箱子信息mapper接口
     */
    private final BackEndBoxInfoMapper backEndBoxInfoMapper;

    /**
     * 箱子规格mapper接口
     */
    private final BackEndBoxStandardMapper backEndBoxStandardMapper;
    /**
     * 通过构造方法注入
     * @param backEndBoxInfoMapper 箱子信息mapper
     * @param backEndBoxStandardMapper 箱子规格mapper
     */
    @Autowired
    public BackEndBoxInfoServiceImpl(BackEndBoxInfoMapper backEndBoxInfoMapper, BackEndBoxStandardMapper backEndBoxStandardMapper) {
        this.backEndBoxInfoMapper = backEndBoxInfoMapper;
        this.backEndBoxStandardMapper = backEndBoxStandardMapper;
    }

    /**
     * 箱子信息列表（分页）
     *
     * @param boxInfo 箱子信息实体
     * @return 结果
     */
    @Override
    public List<BoxInfo> queryBoxInfoList(BoxInfo boxInfo) {
        // 如果是否使用字段存在
        if (boxInfo.getIsUsed()!=null){
            // 如果使用字段输入格式错误
            if (boxInfo.getIsUsed() != NO_USED || boxInfo.getIsUsed() != IS_USED){
                // 抛异常
                throw new CustomException("使用状态格式错误");
            }
        }
        // 如果存在使用人姓名
        if (boxInfo.getUsedByName()!=null){
            // 如果姓名存在非法字符
            if (InfoUtil.isHaveIllegalChar(boxInfo.getUsedByName())){
                // 抛异常
                throw new CustomException("使用姓名存在非法字符");
            }
        }
        // 返回查询结果
        return backEndBoxInfoMapper.queryBoxInfoList(boxInfo);
    }

    /**
     * 删除箱子信息
     *
     * @param ids 箱子信息id数组
     * @return 结果
     */
    @Override
    public int deleteBoxInfo(Long[] ids) {
        // 定义变量记录更新条数
        int count = backEndBoxInfoMapper.deleteBoxInfo(ParameterUtil.getBatchUpdateMapByIds(ids));
        // 如果更新条数不等于数组长度
        if (count != ids.length){
            // 抛出异常
            throw new CustomException("箱子仍有订单,箱子不能删除");
        }
        // 返回成功信息
        return SUCCESS;
    }

    /**
     * 添加箱子信息
     *
     * @param boxInfo 箱子信息实体
     * @return 结果
     */
    @Override
    public int insertBoxInfo(BoxInfo boxInfo) {
        // 如果箱子积分单价小于0
        if (boxInfo.getBoxUnitPrice().intValue()<=ERROR){
            // 抛出异常
            throw new CustomException("箱子积分不能小于等于零");
        }
        // 获取当前箱子规格的基本信息
        BoxStandardV1 boxStandardV1 = backEndBoxStandardMapper.queryBox(boxInfo.getBoxStandard());
        // 如果不存在当前箱子规格
        if (boxStandardV1 == null){
            // 调用插入箱子规格方法
            insertStandard(boxInfo);
        }else {
            // 如果当前规格下积分不相同
            if (!boxStandardV1.getBoxUnitPrice().equals(boxInfo.getBoxUnitPrice())){
                // 抛出异常
                throw new CustomException("当前规格【" + boxInfo.getBoxStandard() + "】已存在，所需积分为【" + boxStandardV1.getBoxUnitPrice() + "】");
            }
        }
        // 调用插入箱子信息方法
        insertInfo(boxInfo);
        // 返回成功信息
        return SUCCESS;
    }

    /**
     * 添加箱子信息
     * @param boxInfo 箱子信息对象
     */
    private void insertInfo(BoxInfo boxInfo){
        // 生成箱子编号: 年月日+随机六位数
        boxInfo.setBoxCode(Long.valueOf(SeqGeneratorUtil.seqGenerator(DateUtils.getNowDateStr(),6)));
        // 未使用
        boxInfo.setIsUsed(0);
        // 创建时间
        boxInfo.setCreateTime(DateUtils.getNowDate());
        // 创建人
        boxInfo.setCreateBy(SecurityUtils.getUsername());
        // 版本号
        boxInfo.setVersion(0L);
        // 未删除
        boxInfo.setDelFlag("0");
        // new一个list
        List<BoxInfo> list = new ArrayList<>();
        // 添加箱子信息实体
        list.add(boxInfo);
        // 定义变量记录箱子信息添加条数
        int count = backEndBoxInfoMapper.insertBoxInfo(list);
        // 如果添加条数不等于库存数量
        if (count == ERROR) {
            // 抛出异常
            throw new CustomException("添加箱子信息失败");
        }
    }

    /**
     * 添加箱子规格
     * @param boxInfo 箱子信息对象
     */
    private void insertStandard(BoxInfo boxInfo){
        BoxStandard boxStandard = new BoxStandard();
        // 箱子规格
        boxStandard.setBoxStandard(boxInfo.getBoxStandard());
        // 箱子积分
        boxStandard.setBoxUnitPrice(boxInfo.getBoxUnitPrice());
        // 箱子描述
        boxStandard.setRemark(boxInfo.getRemark());
        // 创建人
        boxStandard.setCreateBy(SecurityUtils.getUsername());
        // 创建时间
        boxStandard.setCreateTime(DateUtils.getNowDate());
        // 版本号
        boxStandard.setVersion(0L);
        // 未删除
        boxStandard.setDelFlag("0");
        // 添加箱子规格
        int count = backEndBoxStandardMapper.insertBoxStandard(boxStandard);
        // 如果添加失败
        if (count == ERROR){
            // 抛出异常
            throw new CustomException("添加箱子规格失败");
        }
    }
}

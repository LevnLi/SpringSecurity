package com.ruoyi.project.storage.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.util.ParameterUtil;
import com.ruoyi.project.common.util.SeqGeneratorUtil;
import com.ruoyi.project.storage.domain.BoxInfo;
import com.ruoyi.project.storage.domain.BoxStandard;
import com.ruoyi.project.storage.domain.BoxStandardV0;
import com.ruoyi.project.storage.domain.BoxStandardV1;
import com.ruoyi.project.storage.mapper.BackEndBoxInfoMapper;
import com.ruoyi.project.storage.mapper.BackEndBoxStandardMapper;
import com.ruoyi.project.storage.msg.Msg;
import com.ruoyi.project.storage.service.BackEndBoxStandardService;
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
 * @description :箱子规格service接口实现类
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BackEndBoxStandardServiceImpl extends Msg implements BackEndBoxStandardService {

    /**
     * 箱子规格mapper接口
     */
    private final BackEndBoxStandardMapper backEndBoxStandardMapper;

    /**
     * 箱子信息mapper接口
     */
    private final BackEndBoxInfoMapper backEndBoxInfoMapper;

    /**
     * 通过构造方法注入
     * @param backEndBoxStandardMapper 箱子规格mapper
     * @param backEndBoxInfoMapper 箱子信息mapper
     */
    @Autowired
    public BackEndBoxStandardServiceImpl(BackEndBoxStandardMapper backEndBoxStandardMapper, BackEndBoxInfoMapper backEndBoxInfoMapper) {
        this.backEndBoxStandardMapper = backEndBoxStandardMapper;
        this.backEndBoxInfoMapper = backEndBoxInfoMapper;
    }

    /**
     * 箱子规格列表（分页）
     *
     * @param boxStandard 箱子规格对象
     * @return 结果
     */
    @Override
    public List<BoxStandard> queryBoxStandardList(BoxStandard boxStandard) {
        // 如果存在箱子规格
        if (boxStandard.getBoxStandard() != null){
            // 如果箱子规格不合法
            if (!InfoUtil.isHaveIllegalBoxStandard(boxStandard.getBoxStandard())){
                // 抛异常
                throw new CustomException("请输入正确箱子规格");
            }
        }
        // 返回查询分页列表
        return backEndBoxStandardMapper.queryBoxStandardList(boxStandard);
    }

    /**
     * 箱子规格新增
     *
     * @param boxStandard 箱子规格对象
     * @return 结果
     */
    @Override
    public int insertBoxStandard(BoxStandard boxStandard) {
        // 调用规格信息判断方法
        isIllegalInfo(boxStandard);
        // 获取当前箱子规格的id、积分单价
        BoxStandardV1 boxStandardV1 = backEndBoxStandardMapper.queryBox(boxStandard.getBoxStandard());
        // 如果不存在当前箱子规格
        if (boxStandardV1 == null){
            // 调用插入箱子规格方法
            insertStandard(boxStandard);
        }else {
            // 如果当前规格下积分不相同
            if (!boxStandardV1.getBoxUnitPrice().equals(boxStandard.getBoxUnitPrice())){
                // 抛出异常
                throw new CustomException("当前规格【" + boxStandard.getBoxStandard() + "】已存在，所需积分为【" + boxStandardV1.getBoxUnitPrice() + "】");
            }
        }
        // 调用插入箱子信息方法
        insertInfo(boxStandard);
        // 返回成功
        return SUCCESS;
    }

    /**
     * 箱子规格删除
     *
     * @param ids 箱子规格id数组
     * @return 结果
     */
    @Override
    public int deleteBoxStandard(Long[] ids) {
        // 对ids进行foreach循环
        for (Long id : ids) {
            // 如果当前箱子规格下有箱子
            if (backEndBoxStandardMapper.queryBoxStandardById(id)!=null){
                // 抛出异常
                throw new CustomException("删除箱子规格失败，规格下仍有箱子");
            }
        }
        // 定义count接收修改条数
        int count = backEndBoxStandardMapper.deleteBoxStandard(ParameterUtil.getIdsUpdateByUpdateTime(ids,SecurityUtils.getUsername(),DateUtils.getNowDate()));
        // 如果修改条数不等于当前ids数组长度
        if (count !=ids.length){
            // 抛出异常
            throw new CustomException("已有他人率先操作，请刷新后重试");
        }
        // 返回成功
        return SUCCESS;
    }

    /**
     * 箱子规格下拉列表（非分页）
     *
     * @return 结果
     */
    @Override
    public List<BoxStandardV0> selectBoxStandard() {
        // 返回箱子规格下拉列表
        return backEndBoxStandardMapper.selectBoxStandard();
    }

    /**
     * 添加箱子信息
     * @param boxStandard 箱子规格对象
     */
    private void insertInfo(BoxStandard boxStandard){
        // new一个list
        List<BoxInfo> list = new ArrayList<>();
        // for循环添加箱子信息实体
        for (int i = 0; i < boxStandard.getInventoryNumber().intValue(); i++) {
            // new一个箱子信息对象
            BoxInfo boxInfo = new BoxInfo();
            // 箱子编号
            boxInfo.setBoxCode(Long.valueOf(SeqGeneratorUtil.seqGenerator(DateUtils.getNowDateStr(),6)));
            // 添加箱子规格
            boxInfo.setBoxStandard(boxStandard.getBoxStandard());
            // 添加箱子积分单价
            boxInfo.setBoxUnitPrice(boxStandard.getBoxUnitPrice());
            // 添加备注
            boxInfo.setRemark(boxStandard.getRemark());
            // 创建人
            boxInfo.setCreateBy(SecurityUtils.getUsername());
            // 创建时间
            boxInfo.setCreateTime(DateUtils.getNowDate());
            // 未使用
            boxInfo.setIsUsed(0);
            // 版本号
            boxInfo.setVersion(0L);
            // 未删除
            boxInfo.setDelFlag("0");
            // 添加实例到箱子信息list中
            list.add(boxInfo);
        }
        // 定义变量记录箱子信息添加条数
        int count = backEndBoxInfoMapper.insertBoxInfo(list);
        // 如果添加条数不等于库存数量
        if (count != boxStandard.getInventoryNumber().intValue()) {
            // 抛出异常
            throw new CustomException("添加箱子信息失败");
        }
    }

    /**
     * 添加箱子规格
     * @param boxStandard 箱子规格对象
     */
    private void insertStandard(BoxStandard boxStandard){
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

    /**
     * 判断添加箱子规格信息
     * @param boxStandard 箱子规格对象
     */
    private void isIllegalInfo(BoxStandard boxStandard){
        // 如果箱子规格不存在或为空
        if (
                // 箱子规格为空或不存在
                boxStandard.getBoxStandard() == null || "".equals(boxStandard.getBoxStandard())||
                // 箱子积分为空或箱子数理为空
                boxStandard.getBoxUnitPrice() == null || boxStandard.getInventoryNumber() == null||
                // 备注信息为空
                boxStandard.getRemark() == null
        ){
            // 抛异常
            throw new CustomException("请完善箱子规格信息");
        }
        // 如果箱子规格不合法
        if (!InfoUtil.isHaveIllegalBoxStandard(boxStandard.getBoxStandard())){
            // 抛异常
            throw new CustomException("请输入正确箱子规格");
        }
        // 如果箱子积分单价小于0
        if (boxStandard.getBoxUnitPrice().intValue() <= ERROR){
            // 抛出异常
            throw new CustomException("箱子积分不能小于等于零");
        }
        // 如果库存数量小于等于0
        if (boxStandard.getInventoryNumber().intValue() <= ERROR){
            // 抛出异常
            throw new CustomException("库存数量不能小于等于零");
        }
        // 如果备注过长
        if (boxStandard.getRemark().length()>REMARK_MAX_LENGTH){
            // 抛异常
            throw new CustomException("备注信息过长");
        }
    }
}

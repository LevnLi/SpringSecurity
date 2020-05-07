package com.ruoyi.project.storage.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

/**
 * @author :lihao
 * @date :2020/05/06
 * @description :箱子规格实体类
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "箱子规格对象", description = "箱子规格对象")
public class BoxStandard extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 箱子规格（如20*20*20）
     */
    @ApiModelProperty(value = "箱子规格（如20*20*20）")
    private String boxStandard;

    /**
     * 箱子积分单价（每月积分单价）
     */
    @ApiModelProperty(value = "箱子积分单价（每月积分单价）")
    private Long boxUnitPrice;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Long sortNo;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Long version;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ApiModelProperty(value = "删除标志（0代表存在 2代表删除）")
    private String delFlag;

    /**
     * 总数量
     */
    @ApiModelProperty(value = "总数量")
    private Long totalNumber;

    /**
     * 已使用数量
     */
    @ApiModelProperty(value = "已使用数量")
    private Long usedNumber;

    /**
     * 库存数量
     */
    @ApiModelProperty(value = "库存数量")
    private Long inventoryNumber;

    /**
     * 使用比例
     */
    @ApiModelProperty(value = "使用比例")
    private String useRatio;

}

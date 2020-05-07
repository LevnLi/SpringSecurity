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
@ApiModel(value = "箱子规格对象V0", description = "箱子规格对象V0")
public class BoxStandardV0 {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;

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

}

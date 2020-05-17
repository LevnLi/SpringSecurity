package com.ruoyi.project.storage.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author :lihao
 * @date :2020/04/29
 * @description : 地址实体类
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "地址对象", description = "地址对象")
public class Address {

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
     * 手机端用户id
     */
    @ApiModelProperty(value = "手机端用户id")
    private Long userId;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String contacts;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
        private String contactsPhone;

    /**
     * 省编号
     */
    @ApiModelProperty(value = "省编号")
    private String province;

    /**
     * 省名称
     */
    @ApiModelProperty(value = "省名称")
    private String provinceName;

    /**
     * 市编号
     */
    @ApiModelProperty(value = "市编号")
    private String city;

    /**
     * 市名称
     */
    @ApiModelProperty(value = "市名称")
    private String cityName;

    /**
     * 内容
     */
    @ApiModelProperty(value = "市名称")
    private String area;

    /**
     * 区名称
     */
    @ApiModelProperty(value = "区名称")
    private String areaName;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "省编号")
    private String address;

    /**
     * 是否是默认地址（0：是；1：否）
     */
    @ApiModelProperty(value = "是否是默认地址（0：是；1：否）")
    private Integer isDefault;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Long sortNo;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Long version;

    /**
     * 删除标记（0：未删除；2：已删除）
     */
    @ApiModelProperty(value = "删除标记（0：未删除；2：已删除）")
    private String delFlag;
}

package com.ruoyi.project.storage.advice.domain;

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
 * @date :2020/04/29
 * @description : 意见建议实体类
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "意见建议对象", description = "意见建议对象")
public class Advice  extends BaseEntity {

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
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String nickName;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
        private String title;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private String sortNo;

    /**
     * 创建时间
     */
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

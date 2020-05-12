package com.ruoyi.project.storage.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @date :2020/05/10
 * @description :预约对象
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "订单对象", description = "订单对象")
public class Order {

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
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "手机端用户ID")
    private Long userId;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "客户姓名")
    private String nickName;

    /**
     * 手机端对状态的解读
     * 1：已下上门单；
     * 2：待收空箱；
     * 3：已收空箱；
     * 4：已发重箱；
     * 5：已存储；
     * 6：已下提取单；
     * 7：待收重箱；
     * 8：已收重箱；
     * 9：已发空箱；
     * 10：已完成
     *
     * 后台端对状态的解读
     * 1：待发空箱；
     * 2：已发空箱；
     * 3：送达空箱；
     * 4：待发重箱；
     * 5：已存储；
     * 6：待发重箱；
     * 7：已发重箱；
     * 8：送达重箱；
     * 9：待收空箱；
     * 10：已完成
     */
    @ApiModelProperty(value = "订单状态")
    private Integer status;

    /**
     * 订单编号（年月日+6位序列）
     */
    @ApiModelProperty(value = "订单编号（年月日+6位序列）")
    private Long orderCode;

    /**
     * 订单名称
     */
    @ApiModelProperty(value = "订单名称")
    private String orderName;

    /**
     * 空箱上门下单时间（订单状态为1-5，显示）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "空箱上门下单时间")
    private Date emptyBoxOrderTime;

    /**
     * 空箱上门姓名（订单状态为1-5，显示）
     */
    @ApiModelProperty(value = "空箱上门姓名")
    private String emptyBoxCallName;

    /**
     * 空箱上门电话（订单状态为1-5，显示）
     */
    @ApiModelProperty(value = "空箱上门电话")
    private String emptyBoxCallPhone;

    /**
     * 空箱上门地址（订单状态为1-5，显示）
     */
    @ApiModelProperty(value = "空箱上门地址")
    private String emptyBoxCallAddress;

    /**
     * 空箱上门时间（年月日，订单状态为1-5，显示）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "空箱上门时间（年月日）")
    private Date emptyBoxCallTime;

    /**
     * 空箱上门时段（如09:00-13:00，订单状态为1-5，显示）
     */
    @ApiModelProperty(value = "空箱上门时段（如09:00-13:00）")
    private String emptyBoxCallInterval;

    /**
     * 重箱提取下单时间（订单状态为6-10，显示）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "重箱提取下单时间（订单状态为6-10，显示）")
    private Date heavyBoxOrderTime;

    /**
     * 重箱提取姓名（订单状态为6-10，显示）
     */
    @ApiModelProperty(value = "重箱提取姓名（订单状态为6-10，显示）")
    private String heavyBoxCallName;

    /**
     * 重箱提取电话（订单状态为6-10，显示）
     */
    @ApiModelProperty(value = "重箱提取电话（订单状态为6-10，显示）")
    private String heavyBoxCallPhone;

    /**
     * 重箱提取地址（订单状态为6-10，显示）
     */
    @ApiModelProperty(value = "重箱提取地址（订单状态为6-10，显示）")
    private String heavyBoxCallAddress;

    /**
     * 重箱提取时间（年月日，订单状态为6-10，显示）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "重箱提取时间（年月日，订单状态为6-10，显示）")
    private Date heavyBoxCallTime;

    /**
     * 重箱提取时段（如09:00-13:00，订单状态为6-10，显示）
     */
    @ApiModelProperty(value = "重箱提取时段（如09:00-13:00，订单状态为6-10，显示）")
    private String heavyBoxCallInterval;

    /**
     * 箱子id
     */
    @ApiModelProperty(value = "箱子id")
    private Long boxId;

    /**
     * 箱子编号（年月日+6位序列）
     */
    @ApiModelProperty(value = "箱子编号（年月日+6位序列）")
    private Long boxCode;

    /**
     * 箱子规格（如202020）
     */
    @ApiModelProperty(value = "箱子规格（如202020）")
    private String boxStandard;

    /**
     * 箱子积分单价（每月积分单价）
     */
    @ApiModelProperty(value = "箱子积分单价（每月积分单价）")
    private Long boxUnitPrice;

    /**
     * 租赁时长（月）
     */
    @ApiModelProperty(value = "租赁时长（月）")
    private Integer leaseDuration;

    /**
     * 实付积分
     */
    @ApiModelProperty(value = "实付积分")
    private Long totalPrice;

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
     * 手机端删除标记（0：未删除；2：已删除）
     */
    @ApiModelProperty(value = "手机端删除标记（0：未删除；2：已删除）")
    private Integer appDelFlag;

    /**
     * 后台端删除标记（0：未删除；2：已删除）
     */
    @ApiModelProperty(value = "后台端删除标记（0：未删除；2：已删除）")
    private Integer backendDelFlag;
}

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
 * @description :订单对象V1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "订单对象V1", description = "订单对象V1")
public class OrderV1 {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "手机端用户ID")
    private Long userId;

    /**
     * 客户姓名
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
     * 空箱上门下单开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "空箱上门下单开始时间")
    private String emptyBoxOrderTimeStart;

    /**
     * 空箱上门下单结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "空箱上门下单结束时间")
    private String emptyBoxOrderTimeEnd;

    /**
     * 重箱提取下单开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "重箱提取下单开始时间")
    private String heavyBoxOrderTimeStart;

    /**
     * 重箱提取下单结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "重箱提取下单结束时间")
    private String heavyBoxOrderTimeEnd;
}

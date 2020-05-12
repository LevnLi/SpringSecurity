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
 * @description :订单对象V0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "订单对象V0", description = "订单对象V0")
public class OrderV0 {

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
    private Integer operate;

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
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Long version;

    /**
     * 信息
     */
    private String msg;
}

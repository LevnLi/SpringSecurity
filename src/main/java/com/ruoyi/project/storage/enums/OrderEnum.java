package com.ruoyi.project.storage.enums;

/**
 * @author :lihao
 * @date :2020/05/13
 * @description : 订单状态枚举类
 */
public enum OrderEnum {


    /**
     * 订单状态一: 后台端待发空箱
     */
    STATUS1(1),

    /**
     * 订单状态二: 手机端待收空箱
     */
    STATUS2(2),

    /**
     * 订单状态三: 手机端待发重箱
     */
    STATUS3(3),

    /**
     * 订单状态四: 后台端待收重箱
     */
    STATUS4(4),

    /**
     * 订单状态五: 手机端可预约提取
     */
    STATUS5(5),

    /**
     * 订单状态六: 后台端待发重箱
     */
    STATUS6(6),

    /**
     * 订单状态七: 手机端待收重箱
     */
    STATUS7(7),

    /**
     * 订单状态八: 手机端待发空箱
     */
    STATUS8(8),

    /**
     * 订单状态九: 后台端待收空箱
     */
    STATUS9(9),

    /**
     * 订单状态十: 订单完成，手机端可删除订单，后台端清空箱子信息
     */
    STATUS10(10);

    /**
     * 定义一个字符
     */
    private final int value;

    /**
     * 有参构造方法
     * @param value 状态值
     */
    OrderEnum (int value){
        this.value = value;
    }

    /**
     * 获取枚举类型的值
     * @return 当前枚举类型的值
     */
    public int getValue(){
        return value;
    }
}

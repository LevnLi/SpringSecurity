package com.ruoyi.project.storage.msg;

/**
 * @author :lihao
 * @date :2020/05/14
 * @description :静态信息类
 */
public class Msg {

    /**
     * 静态私有常量 SUCCESS表示操作成功
     */
    public static final int SUCCESS = 1;

    /**
     * 静态私有常量 ERROR表示操作失败
     */
    public static final int ERROR = 0;

    /**
     * 静态私有常量 BACKEND表示后台端
     */
    public static final Long BACKEND = 3L;

    /**
     * 静态私有常量 APP表示手机端
     */
    public static final Long APP = 2L;

    /**
     * 静态常量指令: 停用 
     */
    public static final String DISABLE = "disable";

    /**
     * 静态常量指令: 启用
     */
    public static final String ENABLE = "enable";

    /**
     * 用户名最大长度
     */
    public static final int USERNAME_MAX_LENGTH = 16;

    /**
     * 密码最大长度
     */
    public static final int PASSWORD_MAX_LENGTH = 16;

    /**
     * 手机号最大长度
     */
    public static final int NUMBER_MAX_LENGTH = 11;

    /**
     * 姓名最大长度
     */
    public static final int NAME_MAX_LENGTH = 10;

    /**
     * 男性
     */
    public static final String SEX_MAN = "0";

    /**
     * 女性
     */
    public static final String SEX_WOMAN = "1";

    /**
     * 地址最大长度
     */
    public static final int ADDRESS_MAX_LENGTH = 256;

    /**
     * 标题最大长度
     */
    public static final int TITLE_MAX_LENGTH = 20;

    /**
     * 内容最大长度
     */
    public static final int CONTENT_MAX_LENGTH = 1000;

    /**
     * 订单名称最大长度
     */
    public static final int ORDER_NAME_MAX_LENGTH = 16;

    /**
     * 图片地址最大长度
     */
    public static final int URL_MAX_LENGTH = 512;

    /**
     * 备注最大长度
     */
    public static final int REMARK_MAX_LENGTH = 256;
}

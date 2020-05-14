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
    public static final String BACKEND = "backend";

    /**
     * 静态私有常量 APP表示手机端
     */
    public static final String APP = "app";

    /**
     * 静态常量指令: 停用 
     */
    public static final String DISABLE = "disable";

    /**
     * 静态常量指令: 启用
     */
    public static final String ENABLE = "enable";
}

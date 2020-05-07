package com.ruoyi.project.common.util;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数工具类
 *
 * @author wangdong
 * @date 2020.04.28
 */
public class ParameterUtil {

    /**
     * 通过传入id数组返回Map<String, Object>
     *
     * @param ids 传入id数组
     * @return Map<String, Object>
     */
    public static Map<String, Object> getBatchUpdateMapByIds(Long[] ids){
        // 初始化map
        Map<String, Object> map = initMap();
        // 设置id数组
        map.put("ids", ids);
        // 返回map
        return map;
    }



    /**
     * 通过传入id数组及操作返回Map<String, Object>
     *
     * @param operate 操作类型（"enable"：启用；"disable"：停用）
     * @param ids 传入id数组
     * @return Map<String, Object>
     */
    public static Map<String, Object> getBatchUpdateMapByOperateIds(String operate, Long[] ids){
        // 初始化map
        Map<String, Object> map = initMap();
        // 设置操作类型
        map.put("operate", operate);
        // 设置id数组
        map.put("ids", ids);
        // 返回map
        return map;
    }

    /**
     * 通过传入id数组及操作返回Map<String, Object>
     *
     * @param id
     * @param msg
     * @param userType
     * @return Map<String, Object>
     */
    public static Map<String, Object> getMapByOperateIds(Long id, String msg,String userType){
        // 初始化map
        Map<String, Object> map = new HashMap(3);
        // 设置id数组
        map.put("id", id);
        // 设置操作类型
        map.put("msg", msg);
        // 设置用户类型
        map.put("userType",userType);
        // 返回map
        return map;
    }

    /**
     * 通过传入id数组及操作返回Map<String, Object>
     *
     * @param id
     * @param msg
     * @param userType
     * @return Map<String, Object>
     */
    public static Map<String, Object> getMapByOperateIdsArray(Long[] id, String userName, Date updateTime){
        // 初始化map
        Map<String, Object> map = new HashMap(3);
        // 设置id数组
        map.put("ids", id);
        // 设置操作类型
        map.put("userName", userName);
        // 设置用户类型
        map.put("updateTime",updateTime);
        // 返回map
        return map;
    }

    /**
     * 通过传入id及操作返回Map<String, Object>
     *
     * @param id
     * @param msg
     * @return Map<String, Object>
     */
    public static Map<String, Object> getMapByIdMsg(Long id, String msg){
        // 初始化map
        Map<String, Object> map = new HashMap(3);
        // 设置id
        map.put("id", id);
        // 设置操作类型
        map.put("msg", msg);
        // 返回map
        return map;
    }

    /**
     * 通过传入id数组及操作返回Map<String, Object>
     *
     * @param id
     * @param msg
     * @return Map<String, Object>
     */
    public static Map<String, Object> getMapByMsg(Long userId, Long id, String msg){
        // 初始化map
        Map<String, Object> map = new HashMap(3);
        // 设置id数组
        map.put("userId", userId);
        // 设置操作类型
        map.put("id", id);
        // 设置用户类型
        map.put("msg",msg);
        // 返回map
        return map;
    }

    /**
     * 初始化返回参数Map<String, Object>
     *
     * @return 返回参数
     */
    private static Map<String, Object> initMap() {
        // 定义Map
        Map<String, Object> map = new HashMap<>(16);
        // 设置更新时间
        map.put("time", DateUtils.getNowDate());
        // 设置更新人
        map.put("user", SecurityUtils.getUsername());
        return map;
    }

}

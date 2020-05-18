package com.ruoyi.project.storage.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.storage.domain.Order;
import com.ruoyi.project.storage.service.BoxBespeakService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author :lihao
 * @date :2020/05/09
 * @description :箱子预约管理类
 */
@RestController
@RequestMapping("/app/box")
@Api(tags = {"【手机端】5.2.3 空箱预约"},description = "获取当前用户默认收货地址、")
public class AppBoxBespeakController extends BaseController {

    /**
     * 空箱预约service接口
     */
    private final BoxBespeakService boxBespeakService;

    /**
     * 通过构造方法注入
     * @param boxBespeakService 空箱预约service
     */
    @Autowired
    public AppBoxBespeakController(BoxBespeakService boxBespeakService) {
        this.boxBespeakService = boxBespeakService;
    }

    /**
     * 查询地址列表
     * @return 查询结果
     */
    @Log(title = "5.2.3.1 获取当前用户默认收货地址", businessType = BusinessType.OTHER)
    @GetMapping("/defaultAddress")
    @ApiOperation(value = "5.2.3.1 获取当前用户默认收货地址",notes = "获取当前用户默认收货地址")
    public AjaxResult defaultAddress(){
        // 返回当前用户默认收货地址
        return AjaxResult.success("查询成功",boxBespeakService.getDefaultAddress());
    }

    /**
     * 有效箱子规格选择（非分页）
     * @return 箱子规格
     */
    @Log(title = "5.2.3.2 有效箱子规格选择（非分页）", businessType = BusinessType.OTHER)
    @GetMapping("/standard/select")
    @ApiOperation(value = "5.2.3.2 有效箱子规格选择（非分页）",notes = "有效箱子规格选择（非分页）")
    public AjaxResult selectBoxStandard(){
        // 返回当前箱子规格下拉列表
        return AjaxResult.success("查询成功",boxBespeakService.getStandardList());
    }

    /**
     * 空箱预约
     * @param order 预约对象
     * @return 预约结果
     */
    @Log(title = "5.2.3.3 预约", businessType = BusinessType.OTHER)
    @PostMapping("/order")
    @ApiOperation(value = "5.2.3.3 预约",notes = "预约")
    public AjaxResult boxOrder(@RequestBody Order order){
        // 空箱预约结果: 大于0: 预约成功 否则: 预约失败
        return boxBespeakService.boxOrder(order)>0 ?
                AjaxResult.success("预约成功") :
                AjaxResult.error("预约失败");
    }
}

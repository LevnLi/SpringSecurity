package com.ruoyi.project.storage.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.storage.domain.Point;
import com.ruoyi.project.storage.service.PointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :lihao
 * @date :2020/05/03
 * @description :手机端积分管理
 */
@RestController
@RequestMapping("/app/point")
@Api(tags = {"【手机端】5.2.7 积分记录"},description = "积分记录列表（分页）")
public class AppPointController extends BaseController {

    /**
     * 积分记录service接口
     */
    private final PointService pointService;

    /**
     * 通过构造方法注入
     * @param pointService 积分记录service
     */
    @Autowired
    public AppPointController(PointService pointService) {
        this.pointService = pointService;
    }

    /**
     * 查询积分列表
     * @param point 积分
     * @return 分页结果
     */
    @Log(title = "5.2.7.1 积分记录列表（分页）", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    @ApiOperation(value = "5.2.7.1 积分记录列表（分页）",notes = "积分记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pageNum",dataType = "int",value = "当前页数",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "pageSize",dataType = "int",value = "每页显示记录数",defaultValue = "10")
    })
    public TableDataInfo list(Point point){
        // 获取分页信息
        startPage();
        // 返回想用请求分页数据
        return getDataTable(pointService.queryPointList(point));
    }

    @GetMapping("/select")
    @ApiOperation(value = "5.2.7.2 获取当前用户积分",notes = "获取当前用户积分")
    public AjaxResult queryUserPoint(){
        // 返回当前用户积分总和
        return AjaxResult.success("查询成功",pointService.queryUserPoint());
    }
}

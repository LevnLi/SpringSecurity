package com.ruoyi.project.storage.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.storage.domain.Point;
import com.ruoyi.project.storage.mapper.AppAdvertisementMapper;
import com.ruoyi.project.storage.service.AppAdvertisementService;
import com.ruoyi.project.storage.service.PointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @author :lihao
 * @date :2020/04/28
 * @description : 手机端广告管理
 */
@RestController
@RequestMapping("/app/advertisement")
@Api(tags = {"【手机端】5.2.2 首页"},description = "广告列表（非分页）、广告积分获取")
public class AppAdvertisementController extends BaseController {

    /**
     * 广告Service接口
     */
    private final AppAdvertisementService appAdvertisementService;

    /**
     * 积分记录service接口
     */
    private final PointService pointService;

    /**
     * 通过构造方法注入
     * @param appAdvertisementService
     * @param pointService
     */
    @Autowired
    public AppAdvertisementController(AppAdvertisementService appAdvertisementService, PointService pointService) {
        this.appAdvertisementService = appAdvertisementService;
        this.pointService = pointService;
    }

    /**
     * 查询广告列表
     * @return 结果
     */
    @Log(title = "5.3.7.1 广告列表（非分页）", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    @ApiOperation(value = "5.3.7.1 广告列表（非分页）",notes = "广告列表")
    public AjaxResult list(){
        // 返回想用请求分页数据
        return AjaxResult.success("查询成功",appAdvertisementService.selectAdvertisementList());
    }

    /**
     * 广告积分获取
     * @param id 广告id
     * @param points 可获积分
     * @return 结果
     */
    @Log(title = "5.2.2.2 广告积分获取", businessType = BusinessType.UPDATE)
    @PutMapping("/points")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "5.2.2.2 广告积分获取",notes = "广告积分获取")
    public AjaxResult getPoints(Long id,Long points){
        // new一个积分对象
        Point point = new Point();
        // 创建人
        point.setCreateBy(SecurityUtils.getUsername());
        // 广告id
        point.setAdvertisementId(id);
        // 获得积分
        point.setPoints(points);
        // 获得方式
        point.setWay(2);
        // 向积分表添加数据
        pointService.insertPoint(point);
        return appAdvertisementService.getAdvertisementPoints(id, points)>0 ? AjaxResult.success("获取积分成功") : AjaxResult.error("获取积分失败");
    }

}

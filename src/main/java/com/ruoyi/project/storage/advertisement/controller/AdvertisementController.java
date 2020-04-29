package com.ruoyi.project.storage.advertisement.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.storage.advertisement.domain.Advertisement;
import com.ruoyi.project.storage.advertisement.service.AdvertisementService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author :lihao
 * @date :2020/04/28
 * @description : 广告controller
 */
@RestController
@RequestMapping("/backend/advertisement")
@Api(tags = {"5.3.7.1 广告列表（分页）"},description = "广告列表（分页）、广告新增、广告编辑、广告删除、广告启用/停用")
public class AdvertisementController extends BaseController {

    /**
     * 广告Service
     */
    @Resource
    private AdvertisementService advertisementService;

    /**
     * 查询广告列表
     * @param advertisement 广告
     * @return 分页结果
     */
    @Log(title = "5.3.7.1 广告列表（分页）", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    @ApiOperation(value = "5.3.7.1 广告列表（分页）",notes = "查询广告列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pageNum",dataType = "int",value = "当前页数",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "pageSize",dataType = "int",value = "每页显示记录数",defaultValue = "10")
    })
    public TableDataInfo list(Advertisement advertisement){
        // 获取分页信息
        startPage();
        // 返回想用请求分页数据
        return getDataTable(advertisementService.selectAdvertisementList(advertisement));
    }

    /**
     * 新增广告
     * @param advertisement 广告对象
     * @return 结果
     */
    @Log(title = "5.3.7.2 广告新增", businessType = BusinessType.INSERT)
    @PostMapping("/create")
    @ApiOperation(value = "5.3.7.2 广告新增",notes = "新增广告")
    public AjaxResult add(@RequestBody Advertisement advertisement){
        // 返回响应结果
        return toAjax(advertisementService.insertAdvertisement(advertisement));
    }

    /**
     * 修改广告
     * @param advertisement 广告对象
     * @return 结果
     */
    @Log(title = "5.3.7.3 广告编辑", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    @ApiOperation(value = "5.3.7.2 广告编辑",notes = "修改广告")
    public AjaxResult edit(@RequestBody Advertisement advertisement){
        // 返回响应结果
        return toAjax(advertisementService.updateAdvertisement(advertisement));
    }

    /**
     * 删除广告
     * @param ids 广告ID
     * @return 结果
     */
    @Log(title = "5.3.7.4 广告删除", businessType = BusinessType.DELETE)
    @PutMapping("/delete/{ids}")
    @ApiOperation(value = "5.3.7.4 广告删除",notes = "删除广告")
    public AjaxResult remove(@PathVariable Long[] ids){
        // 返回响应结果
        return toAjax(advertisementService.deleteAdvertisementByIds(ids));
    }

    /**
     * 启用/停用广告
     * @param operate 操作
     * @param ids 广告ID
     * @return 结果
     */
    @Log(title = "5.3.7.5 广告启用/停用", businessType = BusinessType.UPDATE)
    @PutMapping("/{operate}/{ids}")
    @ApiOperation(value = "5.3.7.5 广告启用/停用",notes = "启用/停用广告")
    public AjaxResult remove(@PathVariable String operate, @PathVariable Long[] ids){
        // 返回响应结果
        return toAjax(advertisementService.operateAdvertisementByIds(operate, ids));
    }
}

package com.ruoyi.project.storage.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.storage.domain.Advertisement;
import com.ruoyi.project.storage.service.AdvertisementService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author :lihao
 * @date :2020/04/28
 * @description : 后台端广告管理
 */
@RestController
@RequestMapping("/backend/advertisement")
@Api(tags = {"【后台端】5.3.7 广告管理"},description = "广告列表（分页）、广告新增、广告编辑、广告删除、广告启用/停用")
public class BackEndAdvertisementController extends BaseController {

    /**
     * 广告Service接口
     */
    private final AdvertisementService advertisementService;

    /**
     * 通过构造方法注入
     * @param advertisementService
     */
    @Autowired
    public BackEndAdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    /**
     * 查询广告列表
     * @param advertisement 广告
     * @return 分页结果
     */
    @Log(title = "5.3.7.1 广告列表（分页）", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    @ApiOperation(value = "5.3.7.1 广告列表（分页）",notes = "广告列表")
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
        /**
         * 广告新增结果:
         *  大于0，返回新增成功
         *  否则，返回新增失败
         */
        return advertisementService.insertAdvertisement(advertisement)>0 ?
                AjaxResult.success("新增成功") :
                AjaxResult.error("新增失败");
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
        /**
         * 广告修改结果:
         *  大于0，返回新增成功
         *  否则，返回新增失败
         */
        return advertisementService.updateAdvertisement(advertisement)>0 ?
                AjaxResult.success("修改成功") :
                AjaxResult.error("修改失败");
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
        /**
         * 广告删除结果:
         *  大于0，返回删除成功
         *  否则，返回删除失败
         */
        return advertisementService.deleteAdvertisementByIds(ids)>0 ?
                AjaxResult.success("删除成功") :
                AjaxResult.error("删除失败");
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
        // 定义字符串: "disable"
        String disable = "disable";
        // 定义字符串: "enable"
        String enable = "enable";
        // 如果是停用指令
        if (disable.equals(operate)){
            /**
             * 广告操作结果:
             *  大于0，返回停用成功
             *  否则，返回停用失败
             */
            return advertisementService.operateAdvertisementByIds(operate, ids)>0 ?
                    AjaxResult.success("停用成功") :
                    AjaxResult.error("停用失败");
        }
        // 如果是启用指令
        if (enable.equals(operate)){
            /**
             * 广告操作结果:
             *  大于0，返回启用成功
             *  否则，返回启用失败
             */
            return advertisementService.operateAdvertisementByIds(operate, ids)>0 ?
                    AjaxResult.success("启用成功") :
                    AjaxResult.error("启用失败");
        }
        // 错误指令，返回错误信息
        return AjaxResult.error("操作错误, 请联系管理员");
    }
}

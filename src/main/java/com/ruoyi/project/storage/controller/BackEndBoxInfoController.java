package com.ruoyi.project.storage.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.storage.domain.BoxInfo;
import com.ruoyi.project.storage.domain.BoxStandard;
import com.ruoyi.project.storage.service.BackEndBoxInfoService;
import com.ruoyi.project.storage.service.BackEndBoxStandardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author :lihao
 * @date :2020/05/06
 * @description :后台端箱子信息管理
 */
@RestController
@RequestMapping("/backend/box/info")
@Api(tags = {"【后台端】5.3.5 箱子信息"},description = "箱子信息列表（分页）、新增、删除")
public class BackEndBoxInfoController extends BaseController {

    /**
     * 箱子信息service接口
     */
    private final BackEndBoxInfoService infoService;

    /**
     * 通过构造方法注入
     * @param infoService
     */
    @Autowired
    public BackEndBoxInfoController(BackEndBoxInfoService infoService) {
        this.infoService = infoService;
    }

    /**
     * 查询箱子信息列表
     * @param boxInfo 箱子信息
     * @return 分页结果
     */
    @Log(title = "5.3.5.1 箱子信息列表（分页）", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    @ApiOperation(value = "5.3.5.1 箱子信息列表（分页）",notes = "箱子信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pageNum",dataType = "int",value = "当前页数",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "pageSize",dataType = "int",value = "每页显示记录数",defaultValue = "10")
    })
    public TableDataInfo list(BoxInfo boxInfo){
        // 获取分页信息
        startPage();
        // 返回想用请求分页数据
        return getDataTable(infoService.queryBoxInfoList(boxInfo));
    }

    /**
     * 新增箱子信息
     * @param boxInfo 箱子信息
     * @return 结果
     */
    @Log(title = "5.3.5.2 箱子信息新增", businessType = BusinessType.INSERT)
    @PostMapping("/create")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "5.3.5.2 箱子信息新增",notes = "箱子信息新增")
    public AjaxResult add(@RequestBody BoxInfo boxInfo){
        /**
         * 箱子信息新增结果:
         *  大于0，返回信息：新增成功
         *  否则，返回信息：新增失败
         */
        return infoService.insertBoxInfo(boxInfo)>0?
                AjaxResult.success("新增成功") :
                AjaxResult.error("新增失败");
    }

    /**
     * 删除箱子信息
     * @param ids 箱子信息ID数组
     * @return 结果
     */
    @Log(title = "5.3.5.3 箱子信息删除", businessType = BusinessType.DELETE)
    @PutMapping("/delete/{ids}")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "5.3.5.3 箱子信息删除",notes = "箱子信息删除")
    public AjaxResult remove(@PathVariable Long[] ids){
        /**
         * 箱子信息删除结果:
         *  大于0，返回信息：删除成功
         *  否则，返回信息：删除失败
         */
        return infoService.deleteBoxInfo(ids)>0?
                AjaxResult.success("删除成功"):
                AjaxResult.error("删除失败");
    }

}

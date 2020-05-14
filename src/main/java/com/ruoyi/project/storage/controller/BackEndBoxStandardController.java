package com.ruoyi.project.storage.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.storage.domain.BoxStandard;
import com.ruoyi.project.storage.service.BackEndBoxStandardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author :lihao
 * @date :2020/05/06
 * @description :后台端箱子规格管理
 */
@RestController
@RequestMapping("/backend/box/standard")
@Api(tags = {"【后台端】5.3.4 箱子规格"},description = "箱子规格列表（分页）、新增、删除、下拉列表（非分页）")
public class BackEndBoxStandardController extends BaseController {

    /**
     * 箱子规格service接口
     */
    private final BackEndBoxStandardService standardService;

    /**
     * 通过构造方法注入
     * @param standardService 规格service
     */
    @Autowired
    public BackEndBoxStandardController(BackEndBoxStandardService standardService) {
        this.standardService = standardService;
    }

    /**
     * 查询箱子规格列表
     * @param boxStandard 箱子规格
     * @return 分页结果
     */
    @Log(title = "5.3.4.1 箱子规格列表（分页）", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    @ApiOperation(value = "5.3.4.1 箱子规格列表（分页）",notes = "箱子规格列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pageNum",dataType = "int",value = "当前页数",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "pageSize",dataType = "int",value = "每页显示记录数",defaultValue = "10")
    })
    public TableDataInfo list(BoxStandard boxStandard){
        // 获取分页信息
        startPage();
        // 返回想用请求分页数据
        return getDataTable(standardService.queryBoxStandardList(boxStandard));
    }

    /**
     * 删除箱子规格
     * @param ids 箱子规格ID数组
     * @return 结果
     */
    @Log(title = "5.3.4.3 箱子规格删除", businessType = BusinessType.DELETE)
    @PutMapping("/delete/{ids}")
    @ApiOperation(value = "5.3.4.3 箱子规格删除",notes = "箱子规格删除")
    public AjaxResult remove(@PathVariable Long[] ids){
        // 捕获异常
        try{
            /**
             * 删除箱子规格结果:
             *   大于0,返回删除成功
             *   否则，返回删除失败
             */
            return standardService.deleteBoxStandard(ids)>0 ?
                    AjaxResult.success("删除成功") :
                    AjaxResult.success("删除失败");
        // 处理异常
        }catch (Exception e){
            // 返回异常信息
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 新增箱子规格
     * @param boxStandard 箱子规格对象
     * @return 结果
     */
    @Log(title = "5.3.4.2 箱子规格新增", businessType = BusinessType.INSERT)
    @PostMapping("/create")
    @ApiOperation(value = "5.3.4.2 箱子规格新增",notes = "箱子规格新增")
    public AjaxResult add(@RequestBody BoxStandard boxStandard){
        // 捕获异常
        try {
            /**
             * 新增箱子规格结果:
             *   大于0,返回新增成功
             *   否则，返回新增失败
             */
            return standardService.insertBoxStandard(boxStandard)>0 ?
                    AjaxResult.success("新增成功") :
                    AjaxResult.success("新增失败");
        //处理异常
        }catch (Exception e){
            // 返回异常信息
            return AjaxResult.error(e.getMessage());
        }
    }

    @Log(title = "5.3.4.4 箱子规格下拉列表（非分页", businessType = BusinessType.OTHER)
    @GetMapping("/select")
    @ApiOperation(value = "5.3.4.4 箱子规格下拉列表（非分页）",notes = "箱子规格下拉列表（非分页）")
    public AjaxResult selectBoxStandard(){
        // 返回当前箱子规格下拉列表
        return AjaxResult.success("查询成功",standardService.selectBoxStandard());
    }
}

package com.ruoyi.project.storage.advice.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.storage.advice.domain.Advice;
import com.ruoyi.project.storage.advice.service.AdviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author :lihao
 * @date :2020/04/29
 * @description : 意见建议控制类
 */
@RestController
@Api(tags = {"5.3.8 意见建议"},description = "处理所有的意见和建议动作")
public class AdviceController extends BaseController {

    @Resource
    private AdviceService adviceService;

    /**
     * 查询意见建议列表
     * @param advice 意见对象
     * @return 分页结果
     */
    @Log(title = "5.3.8.1 意见建议列表（分页）", businessType = BusinessType.OTHER)
    @GetMapping("/backend/advice/list")
    @ApiOperation(value = "5.3.8.1 意见建议列表（分页）",notes = "查询意见列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pageNum",dataType = "int",value = "当前页数",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "pageSize",dataType = "int",value = "每页显示记录数",defaultValue = "10")
    })
    public TableDataInfo adviceList(Advice advice){
        // 获取分页信息
        startPage();
        // 返回想用请求分页数据
        return getDataTable(adviceService.queryAllAdvice(advice));
    }

    /**
     * 新增意见
     * @param advice 意见对象
     * @return 执行结果
     */
    @Log(title = "5.3.8.2 广告新增", businessType = BusinessType.INSERT)
    @PostMapping("/app/personal/advice")
    @ApiOperation(value = "5.3.8.2 广告新增",notes = "新增意见")
    public AjaxResult insetAdvice(Advice advice){
        return toAjax(adviceService.insertAdvice(advice));
    }

}

package com.ruoyi.project.storage.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.storage.domain.Advice;
import com.ruoyi.project.storage.service.AdviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author :lihao
 * @date :2020/04/29
 * @description : 后台端意见管理
 */
@RestController
@RequestMapping("/backend/advice")
@Api(tags = {"【后台端】5.3.8 意见建议"},description = "查看意见建议列表")
public class BackEndAdviceController extends BaseController {

    /**
     * 意见service接口
     */
    private final AdviceService adviceService;

    /**
     * 通过构造方法注入
     * @param adviceService 意见service
     */
    @Autowired
    public BackEndAdviceController(AdviceService adviceService) {
        this.adviceService = adviceService;
    }

    /**
     * 查询意见建议列表
     * @param advice 意见对象
     * @return 分页结果
     */
    @Log(title = "【后台端】5.3.8 意见建议", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    @ApiOperation(value = "【后台端】5.3.8 意见建议",notes = "查询意见列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pageNum",dataType = "int",value = "当前页数",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "pageSize",dataType = "int",value = "每页显示记录数",defaultValue = "10")
    })
    public TableDataInfo adviceList(Advice advice){
        // 获取分页信息
        startPage();
        // 返回请求分页数据
        return getDataTable(adviceService.queryAllAdvice(advice));
    }
}

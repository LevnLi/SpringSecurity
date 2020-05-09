package com.ruoyi.project.storage.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.storage.domain.Advice;
import com.ruoyi.project.storage.service.AdviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :lihao
 * @date :2020/05/05
 * @description :手机端意见管理
 */
@RestController
@RequestMapping("/app/personal")
@Api(tags = {"【手机端】5.2.8 意见建议"},description = "新增意见")
public class AppAdviceController extends BaseController {

    /**
     * 意见service接口
     */
    private final AdviceService adviceService;

    /**
     * 通过构造方法注入
     * @param adviceService
     */
    @Autowired
    public AppAdviceController(AdviceService adviceService) {
        this.adviceService = adviceService;
    }

    /**
     * 新增意见
     * @param advice 意见对象
     * @return 执行结果
     */
    @Log(title = "【手机端】5.2.8 意见建议", businessType = BusinessType.INSERT)
    @PostMapping("/advice")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "【手机端】5.2.8 意见建议",notes = "新增意见")
    public AjaxResult insetAdvice(@RequestBody Advice advice){
        /**
         * 意见新增结果:
         *  大于0，返回新增成功
         *  否则，返回新增失败
         */
        return adviceService.insertAdvice(advice)>0 ? AjaxResult.success("新增成功") : AjaxResult.error("新增失败");
    }
}

package com.ruoyi.project.storage.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.storage.domain.User;
import com.ruoyi.project.storage.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :lihao
 * @date :2020/05/04
 * @description : 手机端用户注册类
 */
@RestController
@RequestMapping("/app")
@Api(tags = {"【手机端】5.2.1 注册"},description = "手机端用户注册")
public class AppRegisterController extends BaseController {

    /**
     * 手机端注册service接口
     */
    private final RegisterService registerService;

    /**
     * 通过构造方法注入
     * @param registerService 注册service
     */
    @Autowired
    public AppRegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    /**
     * 客户注册
     * @param user 客户注册对象
     * @return 结果
     */
    @Log(title = "5.2.1 注册", businessType = BusinessType.INSERT)
    @PostMapping("/regist")
    @ApiOperation(value = "5.2.1 注册",notes = "手机端用户注册")
    public AjaxResult add(@RequestBody User user){
        // 返回注册结果
        return registerService.registerUser(user)>0 ?
                AjaxResult.success("注册成功") :
                AjaxResult.error("注册失败");
    }
}
package com.ruoyi.project.storage.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.storage.service.PasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :lihao
 * @date :2020/05/04
 * @description : 后台端修改密码
 */
@RestController
@RequestMapping("/backend/home")
@Api(tags = {"【后台端】5.3.1 首页"},description = "修改个人密码")
public class BackEndPersonalController extends BaseController {

    /**
     * 个人service接口
     */
    private final PasswordService passwordService;

    /**
     * 通过构造方法注入
     * @param passwordService 个人service
     */
    @Autowired
    public BackEndPersonalController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    /**
     * 修改密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 结果
     */
    @Log(title = "5.2.5.1 修改密码", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePassword/{oldPassword}/{newPassword}")
    @ApiOperation(value = "5.2.5.1 修改密码",notes = "修改密码")
    public AjaxResult updatePassword(@PathVariable String oldPassword,@PathVariable String newPassword){
        // 密码更新结果
        return passwordService.updatePassword(oldPassword,newPassword)>0?
                AjaxResult.success("密码修改成功") :
                AjaxResult.error("密码修改失败");
    }
}

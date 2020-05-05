package com.ruoyi.project.storage.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.storage.service.PersonalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author :lihao
 * @date :2020/05/04
 * @description :
 */
@RestController
@RequestMapping("/backend/home")
@Api(tags = {"【后台端】5.3.1 首页"},description = "修改个人密码")
public class BackEndPersonalController extends BaseController {

    /**
     * 个人service接口
     */
    private final PersonalService personalService;

    /**
     * 通过构造方法注入
     * @param personalService
     */
    @Autowired
    public BackEndPersonalController(PersonalService personalService) {
        this.personalService = personalService;
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
        // 如果输入的旧密码和数据库中的旧密码不一样
        if (
            !SecurityUtils.matchesPassword(
                // 旧密码
                oldPassword,
                    // 查询数据库中旧密码
                personalService.queryOldPassword(
                    SecurityUtils.getUserId(),
                    SecurityUtils.getUserType()
                )
            )
        ){
            // 返回旧密码错误
            return AjaxResult.error("旧密码错误");
        }
        // 如果输入旧密码正确但是新密码与数据库旧密码相同
        if(
            SecurityUtils.matchesPassword(
                // 新密码
                newPassword,
                    // 查询数据库中新密码
                personalService.queryOldPassword(
                    SecurityUtils.getUserId(),
                    SecurityUtils.getUserType()
                )
            )
        ){
            // 返回错误码，和错误信息
            return AjaxResult.error(500,"修改密码失败，新密码不能与旧密码相同");
        }
        // 以上都成功，更新数据库当前用户密码
        return personalService.updatePassword(
                // 当前用户id
                SecurityUtils.getUserId(),
                // 加密后的新密码
                SecurityUtils.encryptPassword(newPassword),
                // 当前用户类型
                SecurityUtils.getUserType()
            /**
             * 更新结果:
             *  大于0: 返回操作成功
             *  否则: 返回操作失败
             */
        )>0? AjaxResult.success("操作成功") : AjaxResult.error("操作失败");
    }
}

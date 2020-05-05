package com.ruoyi.project.storage.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.storage.domain.Point;
import com.ruoyi.project.storage.domain.Register;
import com.ruoyi.project.storage.service.PointService;
import com.ruoyi.project.storage.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
     * 积分记录接口
     */
    private final PointService pointService;

    /**
     * 通过构造方法注入
     * @param registerService
     * @param pointService
     */
    @Autowired
    public AppRegisterController(RegisterService registerService, PointService pointService) {
        this.registerService = registerService;
        this.pointService = pointService;
    }

    /**
     * 客户注册
     * @param register 客户注册对象
     * @return 结果
     */
    @Log(title = "5.2.1 注册", businessType = BusinessType.INSERT)
    @PostMapping("/regist")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "5.2.1 注册",notes = "手机端用户注册")
    public AjaxResult add(@RequestBody Register register){
        // 如果用户账号不为空
        if (registerService.queryByUserName(register)!=null){
            // 返回错误码，新增用户失败，登录账号已存在
            return AjaxResult.error(500,"新增用户'"+register.getUserName()+"'失败，登录账号已存在");
        }
        // 如果手机号码不为空
        else if (registerService.queryByPhoneNumber(register)!=null){
            // 返回错误码，新增用户失败，手机号码已存在
            return AjaxResult.error(500,"新增用户'"+register.getUserName()+"'失败，手机号码已存在");
        }
        // 如果邮箱账号不为空
        else if (registerService.queryByEmail(register)!=null){
            // 返回错误码，新增用户失败，邮箱账号已存在
            return AjaxResult.error(500,"新增用户'"+register.getUserName()+"'失败，邮箱账号已存在");
        }
        // 不存在唯一账户信息
        else {
            // 向用户表添加数据
            registerService.registerUser(register);
            // new一个积分对象
            Point point = new Point();
            // 创建人
            point.setCreateBy(register.getUserName());
            // 通过注册用户名查询用户id，传给point对象
            point.setUserId(registerService.queryIdByName(register));
            // 获取方式
            point.setWay(1);
            // 获得积分
            point.setPoints(1000L);
            // 向积分表添加记录
            pointService.insertPoint(point);
            // 返回注册成功
            return AjaxResult.success("注册成功");
        }
    }
}
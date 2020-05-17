package com.ruoyi.project.storage.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.storage.domain.User;
import com.ruoyi.project.storage.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author :lihao
 * @date :2020/04/30
 * @description :后台端用户管理
 */
@RestController
@RequestMapping("/backend/user")
@Api(tags = {"【后台端】5.3.2 用户管理"},description = "用户列表（分页）、用户新增、用户编辑、用户删除、用户启用/停用，用户密码重置")
public class BackEndUserController extends BaseController {

    /**
     * 用户Service接口
     */
    private final UserService userService;

    /**
     * 通过构造方法注入
     * @param userService 用户Service
     */
    @Autowired
    public BackEndUserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询用户列表
     * @param user 用户
     * @return 分页结果
     */
    @Log(title = "5.3.2.1 用户列表（分页）", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    @ApiOperation(value = "5.3.3.1 用户列表（分页）",notes = "查询用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pageNum",dataType = "int",value = "当前页数",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "pageSize",dataType = "int",value = "每页显示记录数",defaultValue = "10")
    })
    public TableDataInfo list(User user){
        // 获取分页信息
        startPage();
        // 返回想用请求分页数据
        return getDataTable(userService.queryUserList(user));
    }

    /**
     * 新增用户
     * @param user 用户对象
     * @return 结果
     */
    @Log(title = "5.3.2.2 用户新增", businessType = BusinessType.INSERT)
    @PostMapping("/create")
    @ApiOperation(value = "5.3.3.2 用户新增",notes = "新增用户")
    public AjaxResult add(@RequestBody User user){
        // 用户新增结果:
        return userService.insertUser(user)>0 ?
                AjaxResult.success("新增成功") :
                AjaxResult.error("新增失败");
    }

    /**
     * 修改用户
     * @param user 用户对象
     * @return 结果
     */
    @Log(title = "5.3.2.3 用户编辑", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    @ApiOperation(value = "5.3.3.2 用户编辑",notes = "修改用户")
    public AjaxResult edit(@RequestBody User user){
        // 用户修改结果: 大于0，返回修改成功  否则，返回修改失败
        return userService.updateUser(user)>0 ?
                AjaxResult.success("修改成功") :
                AjaxResult.error("修改失败");
    }

    /**
     * 删除用户
     * @param ids 用户ID
     * @return 结果
     */
    @Log(title = "5.3.2.4 用户删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{ids}")
    @ApiOperation(value = "5.3.3.4 用户删除",notes = "删除用户")
    public AjaxResult deleteCustomer(@PathVariable Long[] ids){
        // 用户删除结果: 大于0，返回删除成功  否则，返回删除失败
        return userService.deleteUserByIds(ids)>0 ?
                AjaxResult.success("删除成功") :
                AjaxResult.error("删除失败");
    }

    /**
     * 重置用户密码
     * @param userIds 用户ID
     * @return 结果
     */
    @Log(title = "5.3.2.5 用户重置密码", businessType = BusinessType.UPDATE)
    @PutMapping("/reset/{userIds}")
    @ApiOperation(value = "5.3.3.5 用户重置密码",notes = "用户重置密码")
    public AjaxResult resetCustomerPassword(@PathVariable Long[] userIds){
        // 用户重置密码结果: 大于0，返回密码重置成功  否则，返回密码重置失败
        return userService.resetUserPassword(userIds)>0 ?
                AjaxResult.success("密码重置成功") :
                AjaxResult.error("密码重置失败");
    }

    /**
     * 启用/停用用户
     * @param operate 操作
     * @param ids 用户ID
     * @return 结果
     */
    @Log(title = "5.3.2.6 用户启用/停用", businessType = BusinessType.UPDATE)
    @PutMapping("/{operate}/{ids}")
    @ApiOperation(value = "5.3.3.6 用户启用/停用",notes = "启用/停用用户")
    public AjaxResult operateCustomer(@PathVariable String operate, @PathVariable Long[] ids){
        if(ENABLE.equals(operate)){
            // 用户启用结果: 大于0，返回启用成功  否则，返回启用失败
            return userService.operateUserByIds(operate, ids)>0 ?
                    AjaxResult.success("启用成功") :
                    AjaxResult.error("启用失败");
        }
        if (DISABLE.equals(operate)){
            // 用户停用密码结果: 大于0，返回停用成功  否则，返回停用失败
            return userService.operateUserByIds(operate, ids)>0 ?
                    AjaxResult.success("停用成功") :
                    AjaxResult.error("停用失败");
        }
        return AjaxResult.error("操作失败");
    }
}

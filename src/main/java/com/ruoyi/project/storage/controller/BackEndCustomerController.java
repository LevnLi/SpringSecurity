package com.ruoyi.project.storage.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.storage.domain.Customer;
import com.ruoyi.project.storage.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @author :lihao
 * @date :2020/04/30
 * @description :后台端客户管理
 */
@RestController
@RequestMapping("/backend/customer")
@Api(tags = {"【后台端】5.3.3 客户管理"},description = "客户列表（分页）、客户新增、客户编辑、客户删除、客户启用/停用，客户密码重置")
public class BackEndCustomerController extends BaseController {

    /**
     * 客户Service
     */
    private final CustomerService customerService;

    /**
     * 通过构造方法注入
     * @param customerService
     */
    @Autowired
    public BackEndCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * 查询客户列表
     * @param customer 客户
     * @return 分页结果
     */
    @Log(title = "5.3.3.1 客户列表（分页）", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    @ApiOperation(value = "5.3.3.1 客户列表（分页）",notes = "查询客户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pageNum",dataType = "int",value = "当前页数",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "pageSize",dataType = "int",value = "每页显示记录数",defaultValue = "10")
    })
    public TableDataInfo list(Customer customer){
        // 获取分页信息
        startPage();
        // 返回请求分页数据
        return getDataTable(customerService.queryCustomerList(customer));
    }

    /**
     * 新增客户
     * @param customer 客户对象
     * @return 结果
     */
    @Log(title = "5.3.3.2 客户新增", businessType = BusinessType.INSERT)
    @PostMapping("/create")
    @ApiOperation(value = "5.3.3.2 客户新增",notes = "新增客户")
    public AjaxResult add(@RequestBody Customer customer){
        /**
         * 客户新增结果:
         *  大于0，返回新增成功
         *  否则，返回新增失败
         */
        return customerService.insertCustomer(customer)>0 ?
                AjaxResult.success("新增成功") :
                AjaxResult.error("新增失败");
    }

    /**
     * 修改客户
     * @param customer 客户对象
     * @return 结果
     */
    @Log(title = "5.3.3.3 客户编辑", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    @ApiOperation(value = "5.3.3.2 客户编辑",notes = "修改客户")
    public AjaxResult edit(@RequestBody Customer customer){
        /**
         * 客户修改结果:
         *  大于0，返回修改成功
         *  否则，返回修改失败
         */
        return customerService.updateCustomer(customer)>0 ?
                AjaxResult.success("修改成功") :
                AjaxResult.error("修改失败");
    }

    /**
     * 删除客户
     * @param ids 客户ID
     * @return 结果
     */
    @Log(title = "5.3.3.4 客户删除", businessType = BusinessType.DELETE)
    @PutMapping("/delete/{ids}")
    @ApiOperation(value = "5.3.3.4 客户删除",notes = "删除客户")
    public AjaxResult deleteCustomer(@PathVariable Long[] ids){
        /**
         * 客户删除结果:
         *  大于0，返回删除成功
         *  否则，返回删除失败
         */
        return customerService.deleteCustomerByIds(ids)>0 ?
                AjaxResult.success("删除成功") :
                AjaxResult.error("删除失败");
    }

    /**
     * 重置客户密码
     * @param userIds 客户ID
     * @return 结果
     */
    @Log(title = "5.3.3.5 客户重置密码", businessType = BusinessType.UPDATE)
    @PutMapping("/reset/{userIds}")
    @ApiOperation(value = "5.3.3.5 客户重置密码",notes = "客户重置密码")
    public AjaxResult resetCustomerPassword(@PathVariable Long[] userIds){
        /**
         * 客户重置密码结果:
         *  大于0，返回密码重置成功
         *  否则，返回密码重置失败
         */
        return customerService.resetCustomerPassword(userIds)>0 ?
                AjaxResult.success("密码重置成功") :
                AjaxResult.error("密码重置失败");
    }

    /**
     * 启用/停用客户
     * @param operate 操作
     * @param ids 客户ID
     * @return 结果
     */
    @Log(title = "5.3.3.6 客户启用/停用", businessType = BusinessType.UPDATE)
    @PutMapping("/{operate}/{ids}")
    @ApiOperation(value = "5.3.3.6 客户启用/停用",notes = "启用/停用客户")
    public AjaxResult operateCustomer(@PathVariable String operate, @PathVariable Long[] ids){
        // 定义字符串: "disable"
        String disable = "disable";
        // 定义字符串: "enable"
        String enable = "enable";
        // 如果是停用指令
        if (disable.equals(operate)){
            /**
             * 客户操作结果:
             *  大于0，返回停用成功
             *  否则，返回停用失败
             */
            return customerService.operateCustomerByIds(operate, ids)>0 ?
                    AjaxResult.success("停用成功") :
                    AjaxResult.error("停用失败");
        }
        // 如果是启用指令
        if (enable.equals(operate)){
            /**
             * 客户操作结果:
             *  大于0，返回停用成功
             *  否则，返回停用失败
             */
            return customerService.operateCustomerByIds(operate, ids)>0 ?
                    AjaxResult.success("启用成功") :
                    AjaxResult.error("启用失败");
        }
        // 错误指令，返回错误信息
        return AjaxResult.error("操作错误, 请联系管理员");
    }
}

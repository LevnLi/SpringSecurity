package com.ruoyi.project.storage.customer.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.storage.advertisement.domain.Advertisement;
import com.ruoyi.project.storage.customer.domin.Customer;
import com.ruoyi.project.storage.customer.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author :lihao
 * @date :2020/04/30
 * @description :处理所有的客户操作类
 */
@RestController
@RequestMapping("/backend/customer")
@Api(tags = {"5.3.3 客户管理"},description = "客户列表（分页）、客户新增、客户编辑、客户删除、客户启用/停用，客户密码重置")
public class CustomerController extends BaseController {

    /**
     * 客户Service
     */
    @Resource
    private CustomerService customerService;

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
        // 返回想用请求分页数据
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
        // 返回响应结果
        return toAjax(customerService.insertCustomer(customer));
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
        // 返回响应结果
        return toAjax(customerService.updateCustomer(customer));
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
        // 返回响应结果
        return toAjax(customerService.deleteCustomerByIds(ids));
    }

    /**
     * 重置客户密码
     * @param ids 客户ID
     * @return 结果
     */
    @Log(title = "5.3.3.5 客户重置密码", businessType = BusinessType.UPDATE)
    @PutMapping("/reset/{userIds}")
    @ApiOperation(value = "5.3.3.5 客户重置密码",notes = "客户重置密码")
    public AjaxResult resetCustomerPassword(@PathVariable Long[] userIds){
        // 返回响应结果
        return toAjax(customerService.resetCustomerPassword(userIds));
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
        // 返回响应结果
        return toAjax(customerService.operateCustomerByIds(operate, ids));
    }
}

package com.ruoyi.project.storage.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.storage.domain.Address;
import com.ruoyi.project.storage.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author :lihao
 * @date :2020/04/28
 * @description : 手机端地址管理
 */
@RestController
@RequestMapping("/app/address")
@Api(tags = {"【手机端】5.2.6 地址管理"},description = "地址列表（分页）、地址新增、地址编辑、地址删除、设置默认地址")
public class AppAddressController extends BaseController {

    /**
     * 地址Service接口
     */
    private final AddressService addressService;

    /**
     * 通过构造方法注入
     * @param addressService 地址Service
     */
    @Autowired
    public AppAddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * 查询地址列表
     * @param address 地址
     * @return 分页结果
     */
    @Log(title = "5.2.6.1 地址列表（分页）", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    @ApiOperation(value = "5.3.7、6.1 地址列表（分页）",notes = "地址列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pageNum",dataType = "int",value = "当前页数",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "pageSize",dataType = "int",value = "每页显示记录数",defaultValue = "10")
    })
    public TableDataInfo list(Address address){
        // 获取分页信息
        startPage();
        // 返回想用请求分页数据
        return getDataTable(addressService.selectAddressList(address));
    }

    /**
     * 新增地址
     * @param address 地址对象
     * @return 结果
     */
    @Log(title = "5.3.6.2 地址新增", businessType = BusinessType.INSERT)
    @PostMapping("/create")
    @ApiOperation(value = "5.3.6.2 地址新增",notes = "新增地址")
    public AjaxResult add(@RequestBody Address address){
        // 地址新增结果: 大于0，返回新增成功  否则，返回新增失败
        return addressService.insertAddress(address)>0 ?
                AjaxResult.success("新增成功") :
                AjaxResult.error("新增失败");
    }

    /**
     * 修改地址
     * @param address 地址对象
     * @return 结果
     */
    @Log(title = "5.3.6.3 地址编辑", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    @ApiOperation(value = "5.3.6.2 地址编辑",notes = "修改地址")
    public AjaxResult edit(@RequestBody Address address){
        // 地址修改结果: 大于0，返回修改成功  否则，返回修改失败
        return addressService.updateAddress(address)>0 ?
                AjaxResult.success("修改成功") :
                AjaxResult.error("修改失败");
    }

    /**
     * 删除地址
     * @param id 地址ID
     * @return 结果
     */
    @Log(title = "5.3.6.4 地址删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "5.3.6.4 地址删除",notes = "删除地址")
    public AjaxResult remove(@PathVariable Long id){
        // 地址删除结果: 大于0，返回删除成功  否则，返回删除失败
        return addressService.deleteAddressById(id)>0 ?
                AjaxResult.success("删除成功") :
                AjaxResult.error("删除失败");
    }

    /**
     * 设置默认地址
     * @param id 地址ID
     * @return 结果
     */
    @Log(title = "5.2.6.5 设置默认地址", businessType = BusinessType.UPDATE)
    @PutMapping("/default/{id}")
    @ApiOperation(value = "5.2.6.5 设置默认地址",notes = "设置默认地址")
    public AjaxResult defaultAddress(@PathVariable Long id){
        // 设置默认地址结果: 大于0，返回删除成功  否则，返回删除失败
        return addressService.defaultAddressById(id)>0 ?
                AjaxResult.success("设置默认地址成功") :
                AjaxResult.error("设置默认地址失败");
    }
}

package com.ruoyi.project.storage.controller;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.storage.domain.OrderV0;
import com.ruoyi.project.storage.domain.OrderV1;
import com.ruoyi.project.storage.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import static com.ruoyi.project.storage.msg.Msg.BACKEND;

/**
 * @author :lihao
 * @date :2020/05/12
 * @description :【后台端】订单管理控制类
 */
@RestController
@RequestMapping("/backend/order")
@Api(tags = {"【后台端】5.3.6 订单管理"},description = "订单列表（分页）、订单详情、订单操作、订单删除")
public class BackEndOrderController extends BaseController {

    /**
     * 订单service接口
     */
    private final OrderService orderService;

    /**
     * 通过构造方法注入
     * @param orderService 订单service
     */
    @Autowired
    public BackEndOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 查询订单列表（分页）
     * @param orderV1 订单实体类
     * @return 分页结果
     */
    @Log(title = "5.3.6.1 订单列表（分页）", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    @ApiOperation(value = "5.3.6.1 订单列表（分页）",notes = "订单列表（分页）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pageNum",dataType = "int",value = "当前页数",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "pageSize",dataType = "int",value = "每页显示记录数",defaultValue = "10")
    })
    public TableDataInfo list(OrderV1 orderV1) throws ParseException {
        // 获取分页信息
        startPage();
        // 返回想用请求分页数据
        return getDataTable(orderService.getOrderList(orderV1));
    }

    /**
     * 订单详情
     * @param id 订单id
     * @return 订单内容
     */
    @Log(title = "5.2.6.2 订单详情", businessType = BusinessType.OTHER)
    @GetMapping("/info/{id}")
    @ApiOperation(value = "5.2.6.2 订单详情",notes = "订单详情")
    public AjaxResult orderInfo(@PathVariable Long id){
        return AjaxResult.success(orderService.getOrderInfoById(id,"1"));
    }

    /**
     * 订单操作
     * @param id 订单id
     * @param operate 操作指令
     * @param version 订单版本号
     */
    @Log(title = "5.2.6.3 订单操作", businessType = BusinessType.UPDATE)
    @PutMapping("/operate/{id}/{operate}/{version}")
    @ApiOperation(value = "5.2.6.3 订单操作",notes = "订单操作")
    public AjaxResult orderOperation(@PathVariable Long id, @PathVariable Integer operate, @PathVariable Long version){
        // 空值判断
        if (id == null || operate == null || version == null){
            throw new CustomException("订单id/订单操作/版本号不存在");
        }
        OrderV0 orderV0 = new OrderV0();
        // 装入订单id
        orderV0.setId(id);
        // 装入订单状态
        orderV0.setOperate(operate);
        // 装入订单版本号
        orderV0.setVersion(version);
        // 转入信息
        orderV0.setMsg(BACKEND);
        // 订单操作结果: 大于0，操作成功  否则，操作失败
        return orderService.orderOperation(orderV0)==SUCCESS ?
                AjaxResult.success("操作成功") :
                AjaxResult.error("当前订单已被他人操作，请刷新后重试");
    }

    /**
     * 订单详情
     * @param ids 订单id数组
     * @return 订单内容
     */
    @Log(title = "5.3.6.4 订单删除", businessType = BusinessType.UPDATE)
    @DeleteMapping("/delete/{ids}")
    @ApiOperation(value = "5.3.6.4 订单删除",notes = "订单删除")
    public AjaxResult deleteOrder(@PathVariable Long[] ids){
        // 删除订单结果: 大于0，删除成功  否则，删除失败
        return orderService.deleteOrder(ids)>0 ?
                AjaxResult.success("删除成功") :
                AjaxResult.error("删除失败");
    }
}

package com.ruoyi.project.storage.controller;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.storage.domain.BoxInfo;
import com.ruoyi.project.storage.domain.BoxStandard;
import com.ruoyi.project.storage.service.BackEndBoxInfoService;
import com.ruoyi.project.storage.service.BackEndBoxStandardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author :lihao
 * @date :2020/05/06
 * @description :后台端箱子规格管理
 */
@RestController
@RequestMapping("/backend/box/standard")
@Api(tags = {"【后台端】5.3.4 箱子规格"},description = "箱子规格列表（分页）、新增、删除、下拉列表（非分页）")
public class BackEndBoxStandardController extends BaseController {

    /**
     * 箱子规格service接口
     */
    private final BackEndBoxStandardService standardService;

    /**
     * 箱子信息service接口
     */
    private final BackEndBoxInfoService infoService;

    /**
     * 通过构造方法注入
     * @param standardService
     */
    @Autowired
    public BackEndBoxStandardController(BackEndBoxStandardService standardService, BackEndBoxInfoService infoService) {
        this.standardService = standardService;
        this.infoService = infoService;
    }

    /**
     * 查询箱子规格列表
     * @param boxStandard 箱子规格
     * @return 分页结果
     */
    @Log(title = "5.3.4.1 箱子规格列表（分页）", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    @ApiOperation(value = "5.3.4.1 箱子规格列表（分页）",notes = "箱子规格列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pageNum",dataType = "int",value = "当前页数",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "pageSize",dataType = "int",value = "每页显示记录数",defaultValue = "10")
    })
    public TableDataInfo list(BoxStandard boxStandard){
        // 获取分页信息
        startPage();
        // 返回想用请求分页数据
        return getDataTable(standardService.queryBoxStandardList(boxStandard));
    }

    /**
     * 删除箱子规格
     * @param ids 箱子规格ID数组
     * @return 结果
     */
    @Log(title = "5.3.4.3 箱子规格删除", businessType = BusinessType.DELETE)
    @PutMapping("/delete/{ids}")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "5.3.4.3 箱子规格删除",notes = "箱子规格删除")
    public AjaxResult remove(@PathVariable Long[] ids){
        // 定义count接收修改条数
        int count = 0;
        // 对ids进行foreach循环
        for (Long id : ids) {
            // 如果当前箱子规格下有箱子
            if (standardService.queryBoxStandardById(id)!=null){
                // 抛出异常
                throw new CustomException("删除箱子规格失败，规格下仍有箱子");
            }
            // 其余情况
            else {
                // 加合当前修改条数
                count += standardService.deleteBoxStandard(id);
            }
        }
        // 如果修改条数大于等于当前ids数组长度
        if (count>=ids.length){
            // 表示全部删除，返回成功信息
            return AjaxResult.success("删除成功");
        }
        // 其余情况是一条也没删
        else {
            // 抛出异常
            throw new CustomException("删除箱子规格失败，规格下仍有箱子");
        }
    }

    /**
     * 新增箱子规格
     * @param boxStandard 箱子规格对象
     * @return 结果
     */
    @Log(title = "5.3.4.2 箱子规格新增", businessType = BusinessType.INSERT)
    @PostMapping("/create")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "5.3.4.2 箱子规格新增",notes = "箱子规格新增")
    public AjaxResult add(@RequestBody BoxStandard boxStandard){
        // 定义count变量接收修改条数
        Long count = 0L;
        // 如果箱子积分单价小于0
        if (boxStandard.getBoxUnitPrice()<0L){
            // 返回错误信息
            return AjaxResult.error("箱子积分单价小于0");
        }
        // 如果库存数量小于等于0
        if (boxStandard.getInventoryNumber()<=0L){
            // 返回错误信息
            return AjaxResult.error("库存数量小于0");
        }
        // new一个箱子信息对象
        BoxInfo boxInfo = new BoxInfo();
        // 添加箱子规格
        boxInfo.setBoxStandard(boxStandard.getBoxStandard());
        // 添加箱子积分单价
        boxInfo.setBoxUnitPrice(boxStandard.getBoxUnitPrice());
        // 添加备注
        boxInfo.setRemark(boxStandard.getRemark());
        // 如果当前箱子规格、积分单价存在
        if (standardService.queryBox(boxStandard.getBoxStandard(),boxStandard.getBoxUnitPrice())!=null){
            // for循环执行要添加当前箱子信息个数
            for(int i = 0;i < boxStandard.getInventoryNumber();i ++){
                // count记录添加条数
                count+=infoService.insertBoxInfo(boxInfo);
            }
            /**
             * 插入箱子信息条数结果:
             *  等于添加库存数，返回新增成功
             *  否则，返回新增失败
             */
            return count.equals(boxStandard.getInventoryNumber()) ?
                    AjaxResult.success("新增成功") :
                    AjaxResult.success("新增失败");
        }
        // 当前箱子规格、积分单价不存在
        else {
            // for循环执行要添加当前箱子信息个数
            for(int i = 0;i < boxStandard.getInventoryNumber();i ++){
                // count记录添加条数
                count+=infoService.insertBoxInfo(boxInfo);
            }
            // 如果箱子规格添加成功且箱子信息添加条数等于新增库存数
            if (standardService.insertBoxStandard(boxStandard)>0&&count.equals(boxStandard.getInventoryNumber())){
                // 返回成功信息
                return  AjaxResult.success("新增成功");
            }
            // 否则
            else {
                // 返回失败信息
                return AjaxResult.success("新增失败");
            }
        }
    }

    @Log(title = "5.3.4.4 箱子规格下拉列表（非分页", businessType = BusinessType.OTHER)
    @GetMapping("/select")
    @ApiOperation(value = "5.3.4.4 箱子规格下拉列表（非分页）",notes = "箱子规格下拉列表（非分页）")
    public AjaxResult selectBoxStandard(){
        // 返回当前箱子规格下拉列表
        return AjaxResult.success("查询成功",standardService.selectBoxStandard());
    }
}

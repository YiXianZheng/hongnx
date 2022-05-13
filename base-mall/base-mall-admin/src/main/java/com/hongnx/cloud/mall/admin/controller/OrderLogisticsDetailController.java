package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.OrderLogisticsDetailService;
import com.hongnx.cloud.mall.common.entity.OrderLogisticsDetail;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 物流详情
 *
 * @date 2019-09-21 12:39:00
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/orderlogisticsdetail")
@Api(value = "orderlogisticsdetail", tags = "物流详情管理")
public class OrderLogisticsDetailController {

    private final OrderLogisticsDetailService orderLogisticsDetailService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param orderLogisticsDetail 物流详情
    * @return
    */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:orderlogisticsdetail:index')")
    public R getOrderLogisticsDetailPage(Page page, OrderLogisticsDetail orderLogisticsDetail) {
        return R.ok(orderLogisticsDetailService.page(page,Wrappers.query(orderLogisticsDetail)));
    }

    /**
    * 通过id查询物流详情
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询物流详情")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:orderlogisticsdetail:get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(orderLogisticsDetailService.getById(id));
    }

    /**
    * 新增物流详情
    * @param orderLogisticsDetail 物流详情
    * @return R
    */
	@ApiOperation(value = "新增物流详情")
    @SysLog("新增物流详情")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:orderlogisticsdetail:add')")
    public R save(@RequestBody OrderLogisticsDetail orderLogisticsDetail){
        return R.ok(orderLogisticsDetailService.save(orderLogisticsDetail));
    }

    /**
    * 修改物流详情
    * @param orderLogisticsDetail 物流详情
    * @return R
    */
	@ApiOperation(value = "修改物流详情")
    @SysLog("修改物流详情")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:orderlogisticsdetail:edit')")
    public R updateById(@RequestBody OrderLogisticsDetail orderLogisticsDetail){
        return R.ok(orderLogisticsDetailService.updateById(orderLogisticsDetail));
    }

    /**
    * 通过id删除物流详情
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id删除物流详情")
    @SysLog("删除物流详情")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:orderlogisticsdetail:del')")
    public R removeById(@PathVariable String id){
        return R.ok(orderLogisticsDetailService.removeById(id));
    }

}

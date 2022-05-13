package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.OrderLogisticsService;
import com.hongnx.cloud.mall.common.entity.OrderLogistics;
import com.hongnx.cloud.mall.common.enums.OrderLogisticsEnum;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 订单物流
 *
 * @date 2019-09-16 09:53:17
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/orderlogistics")
@Api(value = "orderlogistics", tags = "订单物流管理")
public class OrderLogisticsController {

    private final OrderLogisticsService orderLogisticsService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param orderLogistics 订单物流
    * @return
    */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:orderlogistics:index')")
    public R getOrderLogisticsPage(Page page, OrderLogistics orderLogistics) {
        return R.ok(orderLogisticsService.page(page,Wrappers.query(orderLogistics)));
    }

    /**
    * 通过id查询订单物流
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询订单物流")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:orderlogistics:get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(orderLogisticsService.getById(id));
    }

    /**
    * 新增订单物流
    * @param orderLogistics 订单物流
    * @return R
    */
	@ApiOperation(value = "新增订单物流")
    @SysLog("新增订单物流")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:orderlogistics:add')")
    public R save(@RequestBody OrderLogistics orderLogistics){
        return R.ok(orderLogisticsService.save(orderLogistics));
    }

    /**
    * 修改订单物流
    * @param orderLogistics 订单物流
    * @return R
    */
	@ApiOperation(value = "修改订单物流")
    @SysLog("修改订单物流")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:orderlogistics:edit')")
    public R updateById(@RequestBody OrderLogistics orderLogistics){
        return R.ok(orderLogisticsService.updateById(orderLogistics));
    }

    /**
    * 通过id删除订单物流
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id删除订单物流")
    @SysLog("删除订单物流")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:orderlogistics:del')")
    public R removeById(@PathVariable String id){
        return R.ok(orderLogisticsService.removeById(id));
    }

	/**
	 * 获取相关枚举数据的字典
	 * @param type
	 * @return
	 */
	@ApiOperation(value = "获取相关枚举数据的字典")
	@GetMapping("/dict/{type}")
	public R getDictByType(@PathVariable String type) {
		return R.ok(OrderLogisticsEnum.queryAll(type));
	}
}

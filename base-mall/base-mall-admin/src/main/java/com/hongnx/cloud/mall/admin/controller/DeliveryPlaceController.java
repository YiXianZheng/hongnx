package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.DeliveryPlaceService;
import com.hongnx.cloud.mall.common.entity.DeliveryPlace;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

import java.util.List;

/**
 * 发货地
 *
 * @date 2020-02-09 22:23:53
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/deliveryplace")
@Api(value = "deliveryplace", tags = "发货地管理")
public class DeliveryPlaceController {

    private final DeliveryPlaceService deliveryPlaceService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param deliveryPlace 发货地
     * @return
     */
	@ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:deliveryplace:index')")
    public R getPage(Page page, DeliveryPlace deliveryPlace) {
        return R.ok(deliveryPlaceService.page(page, Wrappers.query(deliveryPlace)));
    }

    /**
     * 发货地查询
     * @param id
     * @return R
     */
	@ApiOperation(value = "发货地查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:deliveryplace:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(deliveryPlaceService.getById(id));
    }

	/**
	 * list查询
	 * @param deliveryPlace
	 * @return
	 */
	@ApiOperation(value = "list查询")
	@GetMapping("/list")
	public List<DeliveryPlace> getList(DeliveryPlace deliveryPlace) {
		return deliveryPlaceService.list(Wrappers.query(deliveryPlace).lambda()
				.select(DeliveryPlace::getId,
						DeliveryPlace::getPlace));
	}

    /**
     * 发货地新增
     * @param deliveryPlace 发货地
     * @return R
     */
	@ApiOperation(value = "发货地新增")
    @SysLog("新增发货地")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:deliveryplace:add')")
    public R save(@RequestBody DeliveryPlace deliveryPlace) {
        return R.ok(deliveryPlaceService.save(deliveryPlace));
    }

    /**
     * 发货地修改
     * @param deliveryPlace 发货地
     * @return R
     */
	@ApiOperation(value = "发货地修改")
    @SysLog("修改发货地")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:deliveryplace:edit')")
    public R updateById(@RequestBody DeliveryPlace deliveryPlace) {
        return R.ok(deliveryPlaceService.updateById(deliveryPlace));
    }

    /**
     * 发货地删除
     * @param id
     * @return R
     */
	@ApiOperation(value = "发货地删除")
    @SysLog("删除发货地")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:deliveryplace:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(deliveryPlaceService.removeById(id));
    }

}

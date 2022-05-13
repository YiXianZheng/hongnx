package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.OrderInfoService;
import com.hongnx.cloud.mall.admin.service.OrderLogisticsService;
import com.hongnx.cloud.mall.admin.service.UserInfoService;
import com.hongnx.cloud.mall.common.constant.MallConstants;
import com.hongnx.cloud.mall.common.constant.MyReturnCode;
import com.hongnx.cloud.mall.common.entity.OrderInfo;
import com.hongnx.cloud.mall.common.entity.OrderItem;
import com.hongnx.cloud.mall.common.entity.OrderLogistics;
import com.hongnx.cloud.mall.common.enums.OrderInfoEnum;
import com.hongnx.cloud.mall.common.feign.FeignWxAppService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

import java.util.Map;

/**
 * 商城订单
 *
 * @date 2019-09-10 15:21:22
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/orderinfo")
@Api(value = "orderinfo", tags = "商城订单管理")
public class OrderInfoController {

    private final OrderInfoService orderInfoService;
	private final UserInfoService userInfoService;
	private final FeignWxAppService feignWxAppService;
	private final OrderLogisticsService orderLogisticsService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param orderInfo 商城订单
    * @return
    */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:orderinfo:index')")
    public R getOrderInfoPage(Page page, OrderInfo orderInfo) {
        return R.ok(orderInfoService.page1(page, Wrappers.query(orderInfo)));
    }

	/**
	 * 查询数量
	 * @param orderInfo
	 * @return
	 */
	@ApiOperation(value = "查询数量")
	@GetMapping("/count")
	public R getCount(OrderInfo orderInfo) {
		return R.ok(orderInfoService.count(Wrappers.query(orderInfo)));
	}

    /**
    * 通过id查询商城订单
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询商城订单")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:orderinfo:get')")
    public R getById(@PathVariable("id") String id){
		OrderInfo orderInfo = orderInfoService.getById(id);
		R r2 = feignWxAppService.getById(orderInfo.getAppId(), SecurityConstants.FROM_IN);
		orderInfo.setUserInfo(userInfoService.getById(orderInfo.getUserId()));
		orderInfo.setApp((Map<Object, Object>)r2.getData());
		OrderLogistics orderLogistics = orderLogisticsService.getById(orderInfo.getLogisticsId());
		orderInfo.setOrderLogistics(orderLogistics);
        return R.ok(orderInfo);
    }

    /**
    * 新增商城订单
    * @param orderInfo 商城订单
    * @return R
    */
	@ApiOperation(value = "新增商城订单")
    @SysLog("新增商城订单")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:orderinfo:add')")
    public R save(@RequestBody OrderInfo orderInfo){
        return R.ok(orderInfoService.save(orderInfo));
    }

    /**
    * 修改商城订单
    * @param orderInfo 商城订单
    * @return R
    */
	@ApiOperation(value = "修改商城订单")
    @SysLog("修改商城订单")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:orderinfo:edit')")
    public R updateById(@RequestBody OrderInfo orderInfo){
        return R.ok(orderInfoService.updateById(orderInfo));
    }

    /**
    * 通过id删除商城订单
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id删除商城订单")
    @SysLog("删除商城订单")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:orderinfo:del')")
    public R removeById(@PathVariable String id){
        return R.ok(orderInfoService.removeById(id));
    }

	/**
	 * 修改商城订单价格
	 * @param orderItem
	 * @return R
	 */
	@ApiOperation(value = "修改商城订单价格")
	@SysLog("修改商城订单价格")
	@PutMapping("/editPrice")
	@PreAuthorize("@ato.hasAuthority('mall:orderinfo:edit')")
	public R editPrice(@RequestBody OrderItem orderItem){
		orderInfoService.editPrice(orderItem);
		return R.ok();
	}

	/**
	 * 取消商城订单
	 * @param id 商城订单
	 * @return R
	 */
	@ApiOperation(value = "取消商城订单")
	@SysLog("取消商城订单")
	@PutMapping("/cancel/{id}")
	@PreAuthorize("@ato.hasAuthority('mall:orderinfo:edit')")
	public R orderCancel(@PathVariable String id){
		OrderInfo orderInfo = orderInfoService.getById(id);
		if(orderInfo == null){
			return R.failed(MyReturnCode.ERR_70005.getCode(), MyReturnCode.ERR_70005.getMsg());
		}
		if(!CommonConstants.NO.equals(orderInfo.getIsPay())){//只有未支付订单能取消
			return R.failed(MyReturnCode.ERR_70001.getCode(), MyReturnCode.ERR_70001.getMsg());
		}
		orderInfoService.orderCancel(orderInfo);
		return R.ok();
	}

	/**
	 * 商城订单自提
	 * @param id 商城订单ID
	 * @return R
	 */
	@ApiOperation(value = "商城订单自提")
	@PutMapping("/takegoods/{id}")
	@PreAuthorize("@ato.hasAuthority('mall:orderinfo:edit')")
	public R takeGoods(@PathVariable String id){
		OrderInfo orderInfo = orderInfoService.getById(id);
		if(orderInfo == null){
			return R.failed(MyReturnCode.ERR_70005.getCode(), MyReturnCode.ERR_70005.getMsg());
		}
		if(!MallConstants.DELIVERY_WAY_2.equals(orderInfo.getDeliveryWay())
				&& !OrderInfoEnum.STATUS_1.getValue().equals(orderInfo.getStatus())){//只有待自提订单能确认收货
			return R.failed(MyReturnCode.ERR_70001.getCode(), MyReturnCode.ERR_70001.getMsg());
		}
		orderInfoService.orderReceive(orderInfo);
		return R.ok();
	}
}

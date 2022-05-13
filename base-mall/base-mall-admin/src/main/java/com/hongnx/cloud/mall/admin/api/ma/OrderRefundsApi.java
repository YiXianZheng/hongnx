package com.hongnx.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.data.tenant.TenantContextHolder;
import com.hongnx.cloud.mall.admin.service.OrderInfoService;
import com.hongnx.cloud.mall.admin.service.OrderRefundsService;
import com.hongnx.cloud.mall.common.entity.OrderRefunds;
import com.hongnx.cloud.mall.common.feign.FeignWxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 退款详情
 *
 * @date 2019-11-14 16:35:25
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/orderrefunds")
@Api(value = "orderrefunds", tags = "退款详情API")
public class OrderRefundsApi {

    private final OrderRefundsService orderRefundsService;
	private final OrderInfoService orderInfoService;
	private final FeignWxPayService feignWxPayService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param orderRefunds 退款详情
     * @return
     */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public R getOrderRefundsPage(Page page, OrderRefunds orderRefunds) {
		return R.ok(orderRefundsService.page(page, Wrappers.query(orderRefunds)));
    }

    /**
     * 通过id查询退款详情
     * @param id
     * @return R
     */
	@ApiOperation(value = "通过id查询退款详情")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") String id) {
		return R.ok(orderRefundsService.getById(id));
    }

    /**
     * 新增退款详情(发起退款)
     * @param orderRefunds 退款详情
     * @return R
     */
	@ApiOperation(value = "新增退款详情")
    @PostMapping
    public R save(@RequestBody OrderRefunds orderRefunds) {
		return R.ok(orderRefundsService.saveRefunds(orderRefunds));
    }

    /**
     * 修改退款详情
     * @param orderRefunds 退款详情
     * @return R
     */
	@ApiOperation(value = "修改退款详情")
    @PutMapping
    public R updateById(@RequestBody OrderRefunds orderRefunds) {
		return R.ok(orderRefundsService.updateById(orderRefunds));
    }

	/**
	 * 退款回调
	 * @param xmlData
	 * @return
	 * @throws WxPayException
	 */
	@ApiOperation(value = "退款回调")
	@PostMapping("/notify-refunds")
	public String notifyRefunds(@RequestBody String xmlData) {
		log.info("退款回调:"+xmlData);
		R<WxPayRefundNotifyResult> r = feignWxPayService.notifyRefunds(xmlData, SecurityConstants.FROM_IN);
		if(r.isOk()){
			TenantContextHolder.setTenantId(r.getMsg());
			WxPayRefundNotifyResult notifyResult = r.getData();
			OrderRefunds orderRefunds = orderRefundsService.getById(notifyResult.getReqInfo().getOutRefundNo());
			if(orderRefunds != null){
				orderRefundsService.notifyRefunds(orderRefunds);
				return WxPayNotifyResponse.success("成功");
			}else{
				return WxPayNotifyResponse.fail("无此订单详情");
			}
		}else{
			return WxPayNotifyResponse.fail(r.getMsg());
		}
	}
}

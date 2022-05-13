package com.hongnx.cloud.weixin.admin.controller;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayOrderQueryRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.security.annotation.Inside;
import com.hongnx.cloud.weixin.admin.service.WxAppService;
import com.hongnx.cloud.weixin.admin.config.pay.WxPayConfiguration;
import com.hongnx.cloud.weixin.common.entity.WxApp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信支付
 *
 * @date 2019-03-23 21:26:35
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxpay")
@Api(value = "wxpay", tags = "微信支付")
public class WxPayController {

	private final WxAppService wxAppService;

	/**
	 * 调用统一下单接口，并组装生成支付所需参数对象.
	 *
	 * @param request 统一下单请求参数
	 * @return 返回 {@link com.github.binarywang.wxpay.bean.order}包下的类对象
	 */
	@ApiOperation(value = "调用统一下单接口")
	@Inside
	@PostMapping("/unifiedOrder")
	public R unifiedOrder(@RequestBody WxPayUnifiedOrderRequest request) {
		WxPayService wxPayService = WxPayConfiguration.getPayService(request.getAppid());
		try {
			return R.ok(wxPayService.createOrder(request));
		} catch (WxPayException e) {
			e.printStackTrace();
			return R.failed(e.getReturnMsg() + "" + e.getCustomErrorMsg() + "" + e.getErrCodeDes());
		}
	}

	/**
	 * 处理支付回调数据
	 * @param xmlData
	 * @return
	 */
	@ApiOperation(value = "处理支付回调数据")
	@Inside
	@PostMapping("/notifyOrder")
	public R notifyOrder(@RequestBody String xmlData) {
		String appId = WxPayOrderNotifyResult.fromXML(xmlData).getAppid();
		WxPayService wxPayService = WxPayConfiguration.getPayService(appId);
		try {
			WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);
			WxApp wxApp = wxAppService.findByAppId(appId);
			return R.ok(notifyResult,wxApp.getTenantId());
		} catch (WxPayException e) {
			e.printStackTrace();
			return R.failed(e.getReturnMsg() + "" + e.getCustomErrorMsg() + "" + e.getErrCodeDes());
		}
	}

	/**
	 * <pre>
	 * 微信支付-申请退款.
	 * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
	 * 接口链接：https://api.mch.weixin.qq.com/secapi/pay/refund
	 * </pre>
	 *
	 * @param request 请求对象
	 * @return 退款操作结果 wx pay refund result
	 * @throws WxPayException the wx pay exception
	 */
	@ApiOperation(value = "申请退款")
	@Inside
	@PostMapping("/refundOrder")
	public R refundOrder(@RequestBody WxPayRefundRequest request) {
		WxPayService wxPayService = WxPayConfiguration.getPayService(request.getAppid());
		try {
			return R.ok(wxPayService.refund(request));
		} catch (WxPayException e) {
			e.printStackTrace();
			return R.failed(e.getReturnMsg() + "" + e.getCustomErrorMsg() + "" + e.getErrCodeDes());
		}
	}

	/**
	 * 处理退款回调数据
	 * @param xmlData
	 * @return
	 */
	@ApiOperation(value = "处理退款回调数据")
	@Inside
	@PostMapping("/notifyRefunds")
	public R notifyRefunds(@RequestBody String xmlData) {
		String appId = WxPayOrderNotifyResult.fromXML(xmlData).getAppid();
		WxPayService wxPayService = WxPayConfiguration.getPayService(appId);
		try {
			WxPayRefundNotifyResult notifyResult = wxPayService.parseRefundNotifyResult(xmlData);
			WxApp wxApp = wxAppService.findByAppId(appId);
			return R.ok(notifyResult,wxApp.getTenantId());
		} catch (WxPayException e) {
			e.printStackTrace();
			return R.failed(e.getReturnMsg() + "" + e.getCustomErrorMsg() + "" + e.getErrCodeDes());
		}
	}

	/**
	 * 查询订单
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "查询订单")
	@Inside
	@PostMapping("/queryOrder")
	public R queryOrder(@RequestBody WxPayOrderQueryRequest request) {
		WxPayService wxPayService = WxPayConfiguration.getPayService(request.getAppid());
		try {
			return R.ok(wxPayService.queryOrder(request));
		} catch (WxPayException e) {
			e.printStackTrace();
			return R.failed(e.getReturnMsg() + "" + e.getCustomErrorMsg() + "" + e.getErrCodeDes());
		}
	}
}

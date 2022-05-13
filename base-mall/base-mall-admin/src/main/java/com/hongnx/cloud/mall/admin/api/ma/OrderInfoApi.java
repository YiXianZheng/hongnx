package com.hongnx.cloud.mall.admin.api.ma;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.common.core.util.LocalDateTimeUtils;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.data.tenant.TenantContextHolder;
import com.hongnx.cloud.mall.admin.config.MallConfigProperties;
import com.hongnx.cloud.mall.admin.service.*;
import com.hongnx.cloud.mall.common.entity.*;
import com.hongnx.cloud.mall.admin.service.*;
import com.hongnx.cloud.mall.common.constant.MallConstants;
import com.hongnx.cloud.mall.common.constant.MyReturnCode;
import com.hongnx.cloud.mall.common.dto.PlaceOrderDTO;
import com.hongnx.cloud.mall.common.enums.OrderInfoEnum;
import com.hongnx.cloud.mall.common.feign.FeignWxPayService;
import com.hongnx.cloud.weixin.common.entity.WxUser;
import com.hongnx.cloud.weixin.common.util.ThirdSessionHolder;
import com.hongnx.cloud.weixin.common.util.WxMaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 商城订单
 *
 * @date 2019-09-10 15:21:22
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/orderinfo")
@Api(value = "orderinfo", tags = "商城订单API")
public class OrderInfoApi {

    private final OrderInfoService orderInfoService;
	private final FeignWxPayService feignWxPayService;
	private final MallConfigProperties mallConfigProperties;
	private final OrderLogisticsService orderLogisticsService;
	private final GrouponInfoService grouponInfoService;
	private final GrouponUserService grouponUserService;
	private final BargainUserService bargainUserService;
	private final SeckillInfoService seckillInfoService;
	private final SeckillHallInfoService seckillHallInfoService;
	private final SeckillHallService seckillHallService;

	/**
	* 分页查询
	* @param page 分页对象
	* @param orderInfo 商城订单
	* @return
	*/
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public R getOrderInfoPage(Page page, OrderInfo orderInfo) {
		orderInfo.setUserId(ThirdSessionHolder.getMallUserId());
        return R.ok(orderInfoService.page2(page,orderInfo));
    }

    /**
    * 通过id查询商城订单
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询商城订单")
    @GetMapping("/{id}")
    public R getById(HttpServletRequest request, @PathVariable("id") String id){
		return R.ok(orderInfoService.getById2(id));
    }

    /**
    * 新增商城订单
    * @param placeOrderDTO 商城订单
    * @return R
    */
	@ApiOperation(value = "新增商城订单")
    @PostMapping
    public R save(@RequestBody PlaceOrderDTO placeOrderDTO){
		placeOrderDTO.setUserId(ThirdSessionHolder.getMallUserId());
		placeOrderDTO.setAppId(ThirdSessionHolder.getThirdSession().getAppId());
		if(StrUtil.isBlank(placeOrderDTO.getPaymentType())){
			return R.failed(MyReturnCode.ERR_70002.getCode(), MyReturnCode.ERR_70002.getMsg());
		}
		SeckillInfo seckillInfo = null;
		//秒杀订单处理
		if(MallConstants.ORDER_TYPE_3.equals(placeOrderDTO.getOrderType())){
			seckillInfo = seckillInfoService.getById(placeOrderDTO.getMarketId());
			if(seckillInfo.getLimitNum() <= seckillInfo.getSeckillNum()){
				return R.failed(MyReturnCode.ERR_80031.getCode(), MyReturnCode.ERR_80031.getMsg());
			}
			//查出当前用户已经购买该秒杀商品的次数
			int count = orderInfoService.count(Wrappers.<OrderInfo>lambdaQuery()
					.eq(OrderInfo::getUserId,ThirdSessionHolder.getMallUserId())
					.eq(OrderInfo::getOrderType,MallConstants.ORDER_TYPE_3)
					.eq(OrderInfo::getMarketId,placeOrderDTO.getMarketId())
					.and(wrapper -> wrapper.ne(OrderInfo::getStatus,OrderInfoEnum.STATUS_5.getValue())
							.or()
							.isNull(OrderInfo::getStatus)));
			if(count >= seckillInfo.getEachLimitNum()){
				return R.failed(MyReturnCode.ERR_80030.getCode(), MyReturnCode.ERR_80030.getMsg());
			}
			//校验秒杀合法性
			int seckillHallInfoCount = seckillHallInfoService.count(Wrappers.<SeckillHallInfo>lambdaQuery()
					.eq(SeckillHallInfo::getSeckillHallId,placeOrderDTO.getRelationId())
					.eq(SeckillHallInfo::getSeckillInfoId,placeOrderDTO.getMarketId()));
			if(seckillHallInfoCount <= 0){
				return R.failed(MyReturnCode.ERR_80032.getCode(), MyReturnCode.ERR_80032.getMsg());
			}
			//校验秒杀时间
			SeckillHall seckillHall = seckillHallService.getById(placeOrderDTO.getRelationId());
			if(seckillHall.getHallTime() != LocalDateTime.now().getHour()){
				return R.failed(MyReturnCode.ERR_80033.getCode(), MyReturnCode.ERR_80033.getMsg());
			}
		}
		placeOrderDTO.setAppType(MallConstants.APP_TYPE_1);
		placeOrderDTO.setPaymentWay(MallConstants.PAYMENT_WAY_2);
		OrderInfo orderInfo = orderInfoService.orderSub(placeOrderDTO, seckillInfo);
		if(orderInfo == null){
			return R.failed(MyReturnCode.ERR_70003.getCode(), MyReturnCode.ERR_70003.getMsg());
		}
		return R.ok(orderInfo);
    }

    /**
    * 通过id删除商城订单
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id删除商城订单")
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable String id){
		OrderInfo orderInfo = orderInfoService.getById(id);
		if(orderInfo == null){
			return R.failed(MyReturnCode.ERR_70005.getCode(), MyReturnCode.ERR_70005.getMsg());
		}
		if(!OrderInfoEnum.STATUS_5.getValue().equals(orderInfo.getStatus()) || CommonConstants.YES.equals(orderInfo.getIsPay())){
			return R.failed(MyReturnCode.ERR_70001.getCode(), MyReturnCode.ERR_70001.getMsg());
		}
		return R.ok(orderInfoService.removeById(id));
    }

	/**
	 * 取消商城订单
	 * @param id 商城订单
	 * @return R
	 */
	@ApiOperation(value = "取消商城订单")
	@PutMapping("/cancel/{id}")
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
	 * 商城订单确认收货
	 * @param id 商城订单
	 * @return R
	 */
	@ApiOperation(value = "商城订单确认收货")
	@PutMapping("/receive/{id}")
	public R orderReceive(@PathVariable String id){
		OrderInfo orderInfo = orderInfoService.getById(id);
		if(orderInfo == null){
			return R.failed(MyReturnCode.ERR_70005.getCode(), MyReturnCode.ERR_70005.getMsg());
		}
		if(!OrderInfoEnum.STATUS_2.getValue().equals(orderInfo.getStatus())){//只有待收货订单能确认收货
			return R.failed(MyReturnCode.ERR_70001.getCode(), MyReturnCode.ERR_70001.getMsg());
		}
		orderInfoService.orderReceive(orderInfo);
		return R.ok();
	}

	/**
	 * 调用统一下单接口，并组装生成支付所需参数对象.
	 *
	 * @param orderInfo 统一下单请求参数
	 * @return 返回 {@link com.github.binarywang.wxpay.bean.order}包下的类对象
	 */
	@ApiOperation(value = "调用统一下单接口")
	@PostMapping("/unifiedOrder")
	public R unifiedOrder(HttpServletRequest request, @RequestBody OrderInfo orderInfo){
		//检验用户session登录
		WxUser wxUser = new WxUser();
		wxUser.setAppId(ThirdSessionHolder.getThirdSession().getAppId());
		wxUser.setId(ThirdSessionHolder.getThirdSession().getWxUserId());
		wxUser.setSessionKey(ThirdSessionHolder.getThirdSession().getSessionKey());
		wxUser.setOpenId(ThirdSessionHolder.getThirdSession().getOpenId());
		wxUser.setMallUserId(ThirdSessionHolder.getThirdSession().getMallUserId());
		orderInfo = orderInfoService.getById(orderInfo.getId());
		if(orderInfo == null){
			return R.failed(MyReturnCode.ERR_70005.getCode(), MyReturnCode.ERR_70005.getMsg());
		}
		if(!CommonConstants.NO.equals(orderInfo.getIsPay())){//只有未支付的详单能发起支付
			return R.failed(MyReturnCode.ERR_70004.getCode(), MyReturnCode.ERR_70004.getMsg());
		}
		if(orderInfo.getPaymentPrice().compareTo(BigDecimal.ZERO)==0){//0元购买不调支付
			orderInfo.setPaymentTime(LocalDateTime.now());
			orderInfoService.notifyOrder(orderInfo);
			return R.ok();
		}
		if(MallConstants.ORDER_TYPE_1.equals(orderInfo.getOrderType())) {//砍价订单
			BargainUser bargainUser = bargainUserService.getById(orderInfo.getRelationId());
			if(CommonConstants.YES.equals(bargainUser.getIsBuy())){
				return R.failed(MyReturnCode.ERR_80006.getCode(), MyReturnCode.ERR_80006.getMsg());
			}
		}
		if(MallConstants.ORDER_TYPE_2.equals(orderInfo.getOrderType())){//拼团订单
			GrouponInfo grouponInfo = grouponInfoService.getOne(Wrappers.<GrouponInfo>lambdaQuery()
					.eq(GrouponInfo::getId,orderInfo.getMarketId())
					.eq(GrouponInfo::getEnable,CommonConstants.YES)
					.lt(GrouponInfo::getValidBeginTime,LocalDateTime.now())
					.gt(GrouponInfo::getValidEndTime,LocalDateTime.now()));
			if(grouponInfo == null){//判断拼团的有效性
				return R.failed(MyReturnCode.ERR_80010.getCode(), MyReturnCode.ERR_80010.getMsg());
			}
			if(StrUtil.isNotBlank(orderInfo.getRelationId())){
				//校验当前用户是否已经参与
				GrouponUser grouponUser1 = grouponUserService.getOne(Wrappers.<GrouponUser>lambdaQuery()
						.eq(GrouponUser::getGroupId,orderInfo.getRelationId())
						.eq(GrouponUser::getUserId,wxUser.getMallUserId()));
				if(grouponUser1 != null){
					return R.failed(MyReturnCode.ERR_80012.getCode(), MyReturnCode.ERR_80012.getMsg());
				}
				//校验拼团人数
				GrouponUser grouponUser = grouponUserService.getById(orderInfo.getRelationId());
				Integer havCountGrouponing = grouponUserService.selectCountGrouponing(orderInfo.getRelationId());
				if(havCountGrouponing >= grouponUser.getGrouponNum()){
					return R.failed(MyReturnCode.ERR_80011.getCode(), MyReturnCode.ERR_80011.getMsg());
				}
			}
		}
		String appId = WxMaUtil.getAppId(request);
		WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
		wxPayUnifiedOrderRequest.setAppid(appId);
		String body = orderInfo.getName();
		body = body.length() > 40 ? body.substring(0,39) : body;
		wxPayUnifiedOrderRequest.setBody(body);
		wxPayUnifiedOrderRequest.setOutTradeNo(orderInfo.getOrderNo());
		wxPayUnifiedOrderRequest.setTotalFee(orderInfo.getPaymentPrice().multiply(new BigDecimal(100)).intValue());
		wxPayUnifiedOrderRequest.setTradeType("JSAPI");
		wxPayUnifiedOrderRequest.setNotifyUrl(mallConfigProperties.getNotifyHost()+"/mall/api/ma/orderinfo/notify-order");
		wxPayUnifiedOrderRequest.setSpbillCreateIp("127.0.0.1");
		wxPayUnifiedOrderRequest.setOpenid(wxUser.getOpenId());
		return feignWxPayService.unifiedOrder(wxPayUnifiedOrderRequest, SecurityConstants.FROM_IN);
	}

	/**
	 * 支付回调
	 * @param xmlData
	 * @return
	 * @throws WxPayException
	 */
	@ApiOperation(value = "支付回调")
	@PostMapping("/notify-order")
	public String notifyOrder(@RequestBody String xmlData) {
		log.info("支付回调:"+xmlData);
		R<WxPayOrderNotifyResult> r = feignWxPayService.notifyOrder(xmlData, SecurityConstants.FROM_IN);
		if(r.isOk()){
			TenantContextHolder.setTenantId(r.getMsg());
			WxPayOrderNotifyResult notifyResult = r.getData();
			OrderInfo orderInfo = orderInfoService.getOne(Wrappers.<OrderInfo>lambdaQuery()
					.eq(OrderInfo::getOrderNo,notifyResult.getOutTradeNo()));
			if(orderInfo != null){
				if(orderInfo.getPaymentPrice().multiply(new BigDecimal(100)).intValue() == notifyResult.getTotalFee()){
					String timeEnd = notifyResult.getTimeEnd();
					LocalDateTime paymentTime = LocalDateTimeUtils.parse(timeEnd);
					orderInfo.setPaymentTime(paymentTime);
					orderInfo.setTransactionId(notifyResult.getTransactionId());
					orderInfoService.notifyOrder(orderInfo);
					return WxPayNotifyResponse.success("成功");
				}else{
					return WxPayNotifyResponse.fail("付款金额与订单金额不等");
				}
			}else{
				return WxPayNotifyResponse.fail("无此订单");
			}
		}else{
			return WxPayNotifyResponse.fail(r.getMsg());
		}
	}

	/**
	 * 物流信息回调
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "物流信息回调")
	@PostMapping("/notify-logisticsr")
	public String notifyLogisticsr(HttpServletRequest request, HttpServletResponse response) {
		String param = request.getParameter("param");
		String logisticsId = request.getParameter("logisticsId");
		String tenantId = request.getParameter("tenantId");
		TenantContextHolder.setTenantId(tenantId);
		log.info("物流信息回调:"+param);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result",false);
		map.put("returnCode","500");
		map.put("message","保存失败");
		try {
			JSONObject jsonObject = JSONUtil.parseObj(param);
			orderInfoService.notifyLogisticsr(logisticsId, jsonObject);
			map.put("result",true);
			map.put("returnCode","200");
			map.put("message","保存成功");
			//这里必须返回，否则认为失败，过30分钟又会重复推送。
			response.getWriter().print(JSONUtil.parseObj(map));
		} catch (Exception e) {
			map.put("message","保存失败" + e.getMessage());
			//保存失败，服务端等30分钟会重复推送。
			try {
				response.getWriter().print(JSONUtil.parseObj(map));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 通过物流id查询订单物流
	 * @param logisticsId
	 * @return R
	 */
	@ApiOperation(value = "通过物流id查询订单物流")
	@GetMapping("/orderlogistics/{logisticsId}")
	public R getOrderLogistics(@PathVariable("logisticsId") String logisticsId){
		return R.ok(orderLogisticsService.getById(logisticsId));
	}

	/**
	 * 统计各个状态订单计数
	 * @param orderInfo
	 * @return R
	 */
	@ApiOperation(value = "统计各个状态订单计数")
	@GetMapping("/countAll")
	public R count(OrderInfo orderInfo){
		orderInfo.setUserId(ThirdSessionHolder.getMallUserId());
		Map<String, Integer> countAll = new HashMap<>();
		countAll.put(OrderInfoEnum.STATUS_0.getValue(),orderInfoService.count(Wrappers.query(orderInfo).lambda()
				.isNull(OrderInfo::getStatus)
				.eq(OrderInfo::getIsPay,CommonConstants.NO)));

		countAll.put(OrderInfoEnum.STATUS_1.getValue(),orderInfoService.count(Wrappers.query(orderInfo).lambda()
				.eq(OrderInfo::getStatus,OrderInfoEnum.STATUS_1.getValue())
				.eq(OrderInfo::getIsPay,CommonConstants.YES)));

		countAll.put(OrderInfoEnum.STATUS_2.getValue(),orderInfoService.count(Wrappers.query(orderInfo).lambda()
				.eq(OrderInfo::getStatus,OrderInfoEnum.STATUS_2.getValue())
				.eq(OrderInfo::getIsPay,CommonConstants.YES)));

		countAll.put(OrderInfoEnum.STATUS_3.getValue(),orderInfoService.count(Wrappers.query(orderInfo).lambda()
				.eq(OrderInfo::getStatus,OrderInfoEnum.STATUS_3.getValue())
				.eq(OrderInfo::getIsPay,CommonConstants.YES)
				.eq(OrderInfo::getAppraisesStatus,MallConstants.APPRAISES_STATUS_0)));
		return R.ok(countAll);
	}
}

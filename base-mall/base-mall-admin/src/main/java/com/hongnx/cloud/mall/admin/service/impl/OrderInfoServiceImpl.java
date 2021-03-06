package com.hongnx.cloud.mall.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.request.WxPayOrderQueryRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.common.core.util.LocalDateTimeUtils;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.data.tenant.TenantContextHolder;
import com.hongnx.cloud.mall.admin.config.MallConfigProperties;
import com.hongnx.cloud.mall.admin.mapper.*;
import com.hongnx.cloud.mall.admin.service.*;
import com.hongnx.cloud.mall.common.entity.*;
import com.hongnx.cloud.mall.admin.mapper.*;
import com.hongnx.cloud.mall.admin.service.*;
import com.hongnx.cloud.mall.common.constant.MallConstants;
import com.hongnx.cloud.mall.common.dto.PlaceOrderDTO;
import com.hongnx.cloud.mall.common.enums.OrderItemEnum;
import com.hongnx.cloud.mall.common.enums.OrderLogisticsEnum;
import com.hongnx.cloud.mall.common.enums.OrderInfoEnum;
import com.hongnx.cloud.mall.common.feign.FeignWxPayService;
import com.hongnx.cloud.mall.common.feign.FeignWxTemplateMsgService;
import com.hongnx.cloud.mall.common.util.Kuaidi100Utils;
import com.hongnx.cloud.weixin.common.constant.ConfigConstant;
import com.hongnx.cloud.weixin.common.dto.WxTemplateMsgSendDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * ????????????
 *
 * @date 2019-09-10 15:21:22
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

	private final GoodsSkuService goodsSkuService;
	private final GoodsSpuService goodsSpuService;
	private final OrderItemService orderItemService;
	private final GoodsSkuSpecValueMapper goodsSkuSpecValueMapper;
	private final ShoppingCartService shoppingCartService;
	private final OrderLogisticsService orderLogisticsService;
	private final UserAddressService userAddressService;
	private final RedisTemplate<String, String> redisTemplate;
	private final MallConfigProperties mallConfigProperties;
	private final OrderLogisticsDetailService orderLogisticsDetailService;
	private final PointsRecordService pointsRecordService;
	private final UserInfoService userInfoService;
	private final FeignWxPayService feignWxPayService;
	private final CouponUserService couponUserService;
	private final BargainUserMapper bargainUserMapper;
	private final GrouponInfoMapper grouponInfoMapper;
	private final GrouponUserMapper grouponUserMapper;
	private final FeignWxTemplateMsgService feignWxTemplateMsgService;
	private final SeckillInfoService seckillInfoService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateById(OrderInfo entity) {
		if(StrUtil.isNotBlank(entity.getLogistics()) && StrUtil.isNotBlank(entity.getLogisticsNo())){//???????????????????????????
			entity.setDeliveryTime(LocalDateTime.now());
			OrderLogistics orderLogistics = orderLogisticsService.getOne(Wrappers.<OrderLogistics>lambdaQuery()
					.eq(OrderLogistics::getId,entity.getLogisticsId()));
			//????????????????????????????????????
			boolean sendRedis = false;
			if(StrUtil.isBlank(orderLogistics.getLogistics()) && StrUtil.isBlank(orderLogistics.getLogisticsNo())){
				sendRedis = true;
			}
			orderLogistics.setLogistics(entity.getLogistics());
			orderLogistics.setLogisticsNo(entity.getLogisticsNo());
			orderLogistics.setStatus(OrderLogisticsEnum.STATUS_1.getValue());
			orderLogisticsService.updateById(orderLogistics);
			//????????????100
			String key = mallConfigProperties.getLogisticsKey();					//????????????key
			String company = entity.getLogistics();			//??????????????????
			String number = entity.getLogisticsNo();	//????????????
			String phone = orderLogistics.getTelNum();					//?????????
			String callbackurl = StrUtil.format("{}{}{}", mallConfigProperties.getNotifyHost(),
					"/mall/api/ma/orderinfo/notify-logisticsr?tenantId="+orderLogistics.getTenantId()+"&logisticsId=",orderLogistics.getId());//????????????
			String from = "";					//???????????????
			String to = "";						//???????????????
			String salt = "";					//?????????
			int resultv2 = 1;					//??????????????????
			int autoCom = 0;					//??????????????????
			int interCom = 0;					//???????????????
			String departureCountry = "";		//?????????
			String departureCom = "";			//???????????????????????????
			String destinationCountry = "";		//?????????
			String destinationCom = "";			//???????????????????????????

			Kuaidi100Utils kuaidi100Utils = new Kuaidi100Utils(key);
			String result = kuaidi100Utils.subscribeData(company, number, from, to, callbackurl, salt, resultv2, autoCom, interCom, departureCountry, departureCom, destinationCountry, destinationCom, phone);
			JSONObject jSONObject = JSONUtil.parseObj(result);
			if(!(Boolean)jSONObject.get("result")){
				log.error("?????????????????????returnCode???{}???message???{}",jSONObject.get("returnCode"),jSONObject.get("message"));
				throw new RuntimeException(String.valueOf(jSONObject.get("message")));
			}
			if(sendRedis){
				//??????redis???7????????????????????????
				String keyRedis = String.valueOf(StrUtil.format("{}{}:{}",MallConstants.REDIS_ORDER_KEY_STATUS_2, TenantContextHolder.getTenantId(),entity.getId()));
				redisTemplate.opsForValue().set(keyRedis, entity.getOrderNo() , MallConstants.ORDER_TIME_OUT_2, TimeUnit.DAYS);//??????????????????
			}
			//??????????????????
			try {
				OrderInfo orderInfo = baseMapper.selectById(entity.getId());
				WxTemplateMsgSendDTO wxTemplateMsgSendDTO = new WxTemplateMsgSendDTO();
				wxTemplateMsgSendDTO.setMallUserId(orderInfo.getUserId());
				wxTemplateMsgSendDTO.setUseType(ConfigConstant.WX_TMP_USE_TYPE_3);
				wxTemplateMsgSendDTO.setPage("pages/order/order-detail/index?id="+orderInfo.getId());
				HashMap<String, HashMap<String, String>> data = new HashMap<>();
				data.put("character_string1", (HashMap)JSONUtil.toBean("{\"value\": \""+orderInfo.getOrderNo()+"\"}",Map.class));
				String thing2 = orderInfo.getName();
				thing2 = thing2.length() > 20 ? thing2.substring(0,19) : thing2;
				data.put("thing2", (HashMap)JSONUtil.toBean("{\"value\": \""+thing2+"\"}",Map.class));
				data.put("character_string4", (HashMap)JSONUtil.toBean("{\"value\": \""+entity.getLogisticsNo()+"\"}",Map.class));
				data.put("name5", (HashMap)JSONUtil.toBean("{\"value\": \""+orderLogistics.getUserName()+"\"}",Map.class));
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy???MM???dd??? HH:mm");
				data.put("date10", (HashMap)JSONUtil.toBean("{\"value\": \""+entity.getDeliveryTime().format(dtf)+"\"}",Map.class));
				wxTemplateMsgSendDTO.setData(data);
				feignWxTemplateMsgService.sendTemplateMsg(wxTemplateMsgSendDTO, SecurityConstants.FROM_IN);
			}catch (Exception e){
				log.error("?????????????????????????????????"+e.getMessage(), e);
			}
		}
		return super.updateById(entity);
	}

	@Override
	public IPage<OrderInfo> page1(IPage<OrderInfo> page, Wrapper<OrderInfo> queryWrapper) {
		return baseMapper.selectPage1(page,queryWrapper.getEntity());
	}

	@Override
	public IPage<OrderInfo> page2(IPage<OrderInfo> page, OrderInfo orderInfo) {
		return baseMapper.selectPage2(page,orderInfo);
	}

	@Override
	public OrderInfo getById2(Serializable id) {
		OrderInfo orderInfo = baseMapper.selectById2(id);
		if(orderInfo != null){
			String keyRedis = null;
			//???????????????????????????
			if(CommonConstants.NO.equals(orderInfo.getIsPay())){
				keyRedis = String.valueOf(StrUtil.format("{}{}:{}",MallConstants.REDIS_ORDER_KEY_IS_PAY_0, TenantContextHolder.getTenantId(),orderInfo.getId()));
			}
			//???????????????????????????
			if(OrderInfoEnum.STATUS_2.getValue().equals(orderInfo.getStatus())){
				keyRedis = String.valueOf(StrUtil.format("{}{}:{}",MallConstants.REDIS_ORDER_KEY_STATUS_2, TenantContextHolder.getTenantId(),orderInfo.getId()));
			}
			if(keyRedis != null){
				Long outTime = redisTemplate.getExpire(keyRedis);
				if(outTime != null && outTime > 0){
					orderInfo.setOutTime(outTime);
				}
			}
		}
		return orderInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void orderCancel(OrderInfo orderInfo) {
		if(CommonConstants.NO.equals(orderInfo.getIsPay()) && !OrderInfoEnum.STATUS_5.getValue().equals(orderInfo.getStatus())){//??????
			//?????????????????????????????????
			WxPayOrderQueryRequest request = new WxPayOrderQueryRequest();
			request.setAppid(orderInfo.getAppId());
			request.setTransactionId(orderInfo.getTransactionId());
			request.setOutTradeNo(orderInfo.getOrderNo());
			R<WxPayOrderQueryResult> r = feignWxPayService.queryOrder(request, SecurityConstants.FROM_IN);
			if(r.isOk()){
				WxPayOrderQueryResult wxPayOrderQueryResult = r.getData();
				if(!WxPayConstants.WxpayTradeStatus.NOTPAY.equals(wxPayOrderQueryResult.getTradeState())){//?????????????????????????????????
					throw new RuntimeException("?????????????????????????????????");
				}
			}
			orderInfo.setStatus(OrderInfoEnum.STATUS_5.getValue());
			//????????????
			List<OrderItem> listOrderItem = orderItemService.list(Wrappers.<OrderItem>lambdaQuery()
					.eq(OrderItem::getOrderId,orderInfo.getId()));
			listOrderItem.forEach(orderItem -> {
				GoodsSku goodsSku = goodsSkuService.getById(orderItem.getSkuId());
				if(goodsSku != null){
					goodsSku.setStock(goodsSku.getStock() + orderItem.getQuantity());
					if(!goodsSkuService.updateById(goodsSku)){//????????????
						throw new RuntimeException("???????????????");
					}
				}
				//????????????
				PointsRecord pointsRecord = new PointsRecord();
				pointsRecord.setRecordType(MallConstants.POINTS_RECORD_TYPE_4);
				pointsRecord.setOrderItemId(orderItem.getId());
				pointsRecord = pointsRecordService.getOne(Wrappers.query(pointsRecord));
				//??????????????????????????????????????????
				if(pointsRecord !=null && StrUtil.isNotBlank(pointsRecord.getId())){
					//?????????????????????
					pointsRecord.setId(null);
					pointsRecord.setTenantId(null);
					pointsRecord.setCreateTime(null);
					pointsRecord.setUpdateTime(null);
					pointsRecord.setDescription("?????????????????? " + pointsRecord.getDescription());
					pointsRecord.setAmount(- pointsRecord.getAmount());
					pointsRecord.setRecordType(MallConstants.POINTS_RECORD_TYPE_5);
					//??????????????????
					pointsRecordService.save(pointsRecord);
					//??????????????????
					UserInfo userInfo = userInfoService.getById(orderInfo.getUserId());
					userInfo.setPointsCurrent(userInfo.getPointsCurrent() + pointsRecord.getAmount());
					userInfoService.updateById(userInfo);
				}
				//????????????????????????
				if(MallConstants.ORDER_TYPE_3.equals(orderInfo.getOrderType())){
					SeckillInfo seckillInfo = seckillInfoService.getById(orderInfo.getMarketId());
					seckillInfo.setSeckillNum(seckillInfo.getSeckillNum()-1);
					if(!seckillInfoService.updateById(seckillInfo)){//????????????????????????
						throw new RuntimeException("???????????????");
					}
				}
			});
			baseMapper.updateById(orderInfo);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void orderReceive(OrderInfo orderInfo) {
		orderInfo.setStatus(OrderInfoEnum.STATUS_3.getValue());
		orderInfo.setAppraisesStatus(MallConstants.APPRAISES_STATUS_0);
		orderInfo.setReceiverTime(LocalDateTime.now());
		baseMapper.updateById(orderInfo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeById(Serializable id) {
		orderItemService.remove(Wrappers.<OrderItem>lambdaQuery()
				.eq(OrderItem::getOrderId,id));//??????????????????
		return super.removeById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrderInfo orderSub(PlaceOrderDTO placeOrderDTO, SeckillInfo seckillInfo) {
		OrderInfo orderInfo = new OrderInfo();
		BeanUtil.copyProperties(placeOrderDTO,orderInfo);
		orderInfo.setIsPay(CommonConstants.NO);
		orderInfo.setOrderNo(IdUtil.getSnowflake(0,0).nextIdStr());
		orderInfo.setSalesPrice(BigDecimal.ZERO);
		orderInfo.setPaymentPrice(BigDecimal.ZERO);
		orderInfo.setFreightPrice(BigDecimal.ZERO);
		orderInfo.setPaymentPoints(0);
		orderInfo.setPaymentCouponPrice(BigDecimal.ZERO);
		orderInfo.setPaymentPointsPrice(BigDecimal.ZERO);
		orderInfo.setCreateTime(LocalDateTime.now());
		List<OrderItem> listOrderItem = new ArrayList<>();
		List<GoodsSku> listGoodsSku = new ArrayList<>();
		List<CouponUser> listCouponUser = new ArrayList<>();
		List<PointsRecord> listPointsRecord = new ArrayList<>();
		placeOrderDTO.getSkus().forEach(placeOrderSkuDTO -> {//??????
			GoodsSku goodsSku = goodsSkuService.getOne(Wrappers.<GoodsSku>lambdaQuery()
					.eq(GoodsSku::getSpuId,placeOrderSkuDTO.getSpuId())
					.eq(GoodsSku::getId,placeOrderSkuDTO.getSkuId())
					.ge(GoodsSku::getStock,placeOrderSkuDTO.getQuantity())
					.eq(GoodsSku::getEnable,CommonConstants.YES));
			if(goodsSku != null){
				GoodsSpu goodsSpu = goodsSpuService.getOne(Wrappers.<GoodsSpu>lambdaQuery()
						.eq(GoodsSpu::getId,goodsSku.getSpuId())
						.eq(GoodsSpu::getShelf, CommonConstants.YES));
				if(goodsSpu != null){
					OrderItem orderItem = new OrderItem();
					orderItem.setOrderId(orderInfo.getId());
					orderItem.setStatus(OrderItemEnum.STATUS_0.getValue());
					orderItem.setIsRefund(CommonConstants.NO);
					orderItem.setSpuId(goodsSpu.getId());
					orderItem.setSkuId(goodsSku.getId());
					orderItem.setSpuName(goodsSpu.getName());
					orderItem.setPicUrl(StrUtil.isNotBlank(goodsSku.getPicUrl()) ? goodsSku.getPicUrl() : goodsSpu.getPicUrls()[0]);
					orderItem.setQuantity(placeOrderSkuDTO.getQuantity());
					orderItem.setSalesPrice(goodsSku.getSalesPrice());
					if(MallConstants.DELIVERY_WAY_1.equals(orderInfo.getDeliveryWay())){//????????????????????????
						orderItem.setFreightPrice(placeOrderSkuDTO.getFreightPrice());
					}else{//????????????????????????
						orderItem.setFreightPrice(BigDecimal.ZERO);
					}
					orderItem.setPaymentPrice(placeOrderSkuDTO.getPaymentPrice().add(orderItem.getFreightPrice()));
					orderItem.setPaymentPoints(placeOrderSkuDTO.getPaymentPoints());
					orderItem.setPaymentCouponPrice(placeOrderSkuDTO.getPaymentCouponPrice());
					orderItem.setPaymentPointsPrice(placeOrderSkuDTO.getPaymentPointsPrice());
					orderItem.setCouponUserId(placeOrderSkuDTO.getCouponUserId());
					BigDecimal quantity = new BigDecimal(placeOrderSkuDTO.getQuantity());
					if(StrUtil.isNotBlank(orderItem.getCouponUserId())){
						//???????????????
						CouponUser couponUser = couponUserService.getById(orderItem.getCouponUserId());
						if(couponUser == null){
							throw new RuntimeException("???????????????");
						}
						if(!MallConstants.COUPON_USER_STATUS_0.equals(couponUser.getStatus())){
							throw new RuntimeException("?????????????????????");
						}
						if(couponUser.getValidBeginTime().isAfter(LocalDateTime.now())){
							throw new RuntimeException("????????????????????????");
						}
						if(couponUser.getValidEndTime().isBefore(LocalDateTime.now())){
							throw new RuntimeException("??????????????????");
						}
						couponUser.setStatus(MallConstants.COUPON_USER_STATUS_1);
						couponUser.setUsedTime(LocalDateTime.now());
						listCouponUser.add(couponUser);
					}
					List<GoodsSkuSpecValue> listGoodsSkuSpecValue = goodsSkuSpecValueMapper.listGoodsSkuSpecValueBySkuId(goodsSku.getId());
					listGoodsSkuSpecValue.forEach(goodsSkuSpecValue -> {
						String specInfo = orderItem.getSpecInfo();
						specInfo = StrUtil.isNotBlank(specInfo) ? specInfo : "";
						orderItem.setSpecInfo(specInfo
								+ goodsSkuSpecValue.getSpecValueName()
								+  "???" );
					});
					String specInfo = orderItem.getSpecInfo();
					if(StrUtil.isNotBlank(specInfo)){
						orderItem.setSpecInfo(specInfo.substring(0,specInfo.length() - 1));
					}
					listOrderItem.add(orderItem);
					orderInfo.setSalesPrice(orderInfo.getSalesPrice().add(goodsSku.getSalesPrice().multiply(quantity)));
					orderInfo.setFreightPrice(orderInfo.getFreightPrice().add(orderItem.getFreightPrice()));
					orderInfo.setPaymentPrice(orderInfo.getPaymentPrice().add(orderItem.getPaymentPrice()));
					orderInfo.setPaymentPoints(orderInfo.getPaymentPoints() + (orderItem.getPaymentPoints() != null ? orderItem.getPaymentPoints() : 0));
					orderInfo.setPaymentCouponPrice(orderInfo.getPaymentCouponPrice().add((orderItem.getPaymentCouponPrice() != null ? orderItem.getPaymentCouponPrice() : BigDecimal.ZERO)));
					orderInfo.setPaymentPointsPrice(orderInfo.getPaymentPointsPrice().add((orderItem.getPaymentPointsPrice() != null ? orderItem.getPaymentPointsPrice() : BigDecimal.ZERO)));
					goodsSku.setStock(goodsSku.getStock() - orderItem.getQuantity());
					listGoodsSku.add(goodsSku);
					//???????????????
					shoppingCartService.remove(Wrappers.<ShoppingCart>lambdaQuery()
							.eq(ShoppingCart::getSpuId,goodsSpu.getId())
							.eq(ShoppingCart::getSkuId,goodsSku.getId())
							.eq(ShoppingCart::getUserId,placeOrderDTO.getUserId()));
				}
			}
		});
		if(listOrderItem.size() > 0 && listGoodsSku.size()>0){
			if(MallConstants.DELIVERY_WAY_1.equals(orderInfo.getDeliveryWay())){//????????????1?????????????????????????????????????????????
				UserAddress userAddress = userAddressService.getById(placeOrderDTO.getUserAddressId());
				OrderLogistics orderLogistics = new OrderLogistics();
				orderLogistics.setPostalCode(userAddress.getPostalCode());
				orderLogistics.setUserName(userAddress.getUserName());
				orderLogistics.setTelNum(userAddress.getTelNum());
				orderLogistics.setAddress(userAddress.getProvinceName()+userAddress.getCityName()+userAddress.getCountyName()+userAddress.getDetailInfo());
				//??????????????????
				orderLogisticsService.save(orderLogistics);
				orderInfo.setLogisticsId(orderLogistics.getId());
			}
			orderInfo.setName(listOrderItem.get(0).getSpuName());
			super.save(orderInfo);//????????????
			listOrderItem.forEach(orderItem -> orderItem.setOrderId(orderInfo.getId()));
			//??????????????????
			orderItemService.saveBatch(listOrderItem);
			//???????????????????????????
			if(listCouponUser != null && listCouponUser.size() > 0){
				couponUserService.updateBatchById(listCouponUser);
			}
			listOrderItem.forEach(orderItem -> {
				if(orderItem.getPaymentPoints() != null && orderItem.getPaymentPoints() > 0){
					//??????????????????
					PointsRecord pointsRecord = new PointsRecord();
					pointsRecord.setUserId(orderInfo.getUserId());
					pointsRecord.setDescription("???????????????????????????" + orderItem.getSpuName() + "??? * " +orderItem.getQuantity());
					pointsRecord.setSpuId(orderItem.getSpuId());
					pointsRecord.setOrderItemId(orderItem.getId());
					pointsRecord.setRecordType(MallConstants.POINTS_RECORD_TYPE_4);
					pointsRecord.setAmount(- orderItem.getPaymentPoints());
					listPointsRecord.add(pointsRecord);
				}
			});
			listGoodsSku.forEach(goodsSkuItem -> {
				if(!goodsSkuService.updateById(goodsSkuItem)){//????????????
					throw new RuntimeException("???????????????");
				}
			});
			//????????????????????????
			long orderTimeOut = MallConstants.ORDER_TIME_OUT_0;
			//??????????????????
			if(MallConstants.ORDER_TYPE_3.equals(placeOrderDTO.getOrderType())){
				orderTimeOut = MallConstants.ORDER_TIME_OUT_0_SECKILL;
				seckillInfo.setSeckillNum(seckillInfo.getSeckillNum()+1);
				if(!seckillInfoService.updateById(seckillInfo)){//????????????????????????
					throw new RuntimeException("???????????????");
				};
			}
			if(orderInfo.getPaymentPoints() > 0){
				//??????????????????
				pointsRecordService.saveBatch(listPointsRecord);
				//??????????????????
				UserInfo userInfo = userInfoService.getById(orderInfo.getUserId());
				if(userInfo.getPointsCurrent() < orderInfo.getPaymentPoints()){
					throw new RuntimeException("????????????");
				}
				userInfo.setPointsCurrent(userInfo.getPointsCurrent() - orderInfo.getPaymentPoints());
				userInfoService.updateById(userInfo);
			}
			//??????redis???30??????????????????
			String keyRedis = String.valueOf(StrUtil.format("{}{}:{}",MallConstants.REDIS_ORDER_KEY_IS_PAY_0, TenantContextHolder.getTenantId(),orderInfo.getId()));
			redisTemplate.opsForValue().set(keyRedis, orderInfo.getOrderNo() , orderTimeOut, TimeUnit.MINUTES);//??????????????????
		}else{
			return null;
		}
		return orderInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void notifyOrder(OrderInfo orderInfo) {
		if(CommonConstants.NO.equals(orderInfo.getIsPay())){//??????????????????????????????
			orderInfo.setIsPay(CommonConstants.YES);
			orderInfo.setStatus(OrderInfoEnum.STATUS_1.getValue());
			List<OrderItem> listOrderItem = orderItemService.list(Wrappers.<OrderItem>lambdaQuery()
					.eq(OrderItem::getOrderId,orderInfo.getId()));
			Map<String, List<OrderItem>> resultList = listOrderItem.stream().collect(Collectors.groupingBy(OrderItem::getSpuId));
			AtomicReference<Integer> pointsGiveAmount = new AtomicReference<>(0);
			List<PointsRecord> listPointsRecord = new ArrayList<>();
			List<GoodsSpu> listGoodsSpu = goodsSpuService.listByIds(resultList.keySet());
			listGoodsSpu.forEach(goodsSpu -> {
				resultList.get(goodsSpu.getId()).forEach(orderItem -> {
					//????????????
					goodsSpu.setSaleNum(goodsSpu.getSaleNum()+orderItem.getQuantity());
					//??????????????????
					if(CommonConstants.YES.equals(goodsSpu.getPointsGiveSwitch())){
						PointsRecord pointsRecord = new PointsRecord();
						pointsRecord.setUserId(orderInfo.getUserId());
						pointsRecord.setDescription("???????????????????????????" + goodsSpu.getName() + "??? * " +orderItem.getQuantity());
						pointsRecord.setSpuId(goodsSpu.getId());
						pointsRecord.setOrderItemId(orderItem.getId());
						pointsRecord.setRecordType(MallConstants.POINTS_RECORD_TYPE_2);
						pointsRecord.setAmount(orderItem.getQuantity() * goodsSpu.getPointsGiveNum());
						listPointsRecord.add(pointsRecord);
						pointsGiveAmount.updateAndGet(v -> v + orderItem.getQuantity() * goodsSpu.getPointsGiveNum());
					}
				});
				goodsSpuService.updateById(goodsSpu);
			});
			//??????????????????
			pointsRecordService.saveBatch(listPointsRecord);
			//??????????????????
			UserInfo userInfo = userInfoService.getById(orderInfo.getUserId());
			userInfo.setPointsCurrent(userInfo.getPointsCurrent() + pointsGiveAmount.get());
			userInfoService.updateById(userInfo);
			//???????????????????????????????????????
			if(MallConstants.ORDER_TYPE_1.equals(orderInfo.getOrderType())){
				BargainUser bargainUser = bargainUserMapper.selectById(orderInfo.getRelationId());
				bargainUser.setIsBuy(CommonConstants.YES);
				bargainUser.setOrderId(orderInfo.getId());
				bargainUserMapper.updateById(bargainUser);
			}
			//??????????????????
			if(MallConstants.ORDER_TYPE_2.equals(orderInfo.getOrderType())){
				GrouponInfo grouponInfo = grouponInfoMapper.selectById(orderInfo.getMarketId());
				//??????????????????
				GrouponUser grouponUser = new GrouponUser();
				grouponUser.setGrouponId(grouponInfo.getId());
				grouponUser.setGroupId(orderInfo.getMarketId());
				grouponUser.setUserId(orderInfo.getUserId());
				grouponUser.setNickName(userInfo.getNickName());
				grouponUser.setHeadimgUrl(userInfo.getHeadimgUrl());
				grouponUser.setSpuId(grouponInfo.getSpuId());
				grouponUser.setSkuId(grouponInfo.getSkuId());
				grouponUser.setGrouponNum(grouponInfo.getGrouponNum());
				grouponUser.setGrouponPrice(grouponInfo.getGrouponPrice());
				grouponUser.setStatus(MallConstants.GROUPON_USER_STATUS_0);
				grouponUserMapper.insert(grouponUser);
				if(StrUtil.isBlank(orderInfo.getRelationId())){//??????????????????????????????
					grouponUser.setValidBeginTime(orderInfo.getPaymentTime());
					grouponUser.setValidEndTime(orderInfo.getPaymentTime().plusHours(grouponInfo.getDuration()));
					grouponUser.setIsLeader(CommonConstants.YES);
					grouponUser.setGroupId(grouponUser.getId());
					orderInfo.setRelationId(grouponUser.getId());
					orderInfo.setStatus(OrderInfoEnum.STATUS_10.getValue());
				}else{
					grouponUser.setGroupId(orderInfo.getRelationId());
					grouponUser.setIsLeader(CommonConstants.NO);
					//????????????????????????
					GrouponUser grouponUser2 = grouponUserMapper.selectById(orderInfo.getRelationId());
					grouponUser.setValidBeginTime(grouponUser2.getValidBeginTime());
					grouponUser.setValidEndTime(grouponUser2.getValidEndTime());
					Integer count = grouponUserMapper.selectCountGrouponing(orderInfo.getRelationId()) + 1;
					if(count == grouponUser2.getGrouponNum()){//????????????
						GrouponUser grouponUser3 = new GrouponUser();
						grouponUser.setStatus(MallConstants.GROUPON_USER_STATUS_1);
						grouponUser3.setStatus(MallConstants.GROUPON_USER_STATUS_1);
						grouponUserMapper.update(grouponUser3, Wrappers.<GrouponUser>lambdaQuery()
								.eq(GrouponUser::getGroupId, grouponUser2.getId()));
						//??????????????????????????????
						OrderInfo orderInfo1 = new OrderInfo();
						orderInfo1.setStatus(OrderInfoEnum.STATUS_1.getValue());
						baseMapper.update(orderInfo1, Wrappers.<OrderInfo>lambdaQuery()
								.eq(OrderInfo::getRelationId, grouponUser2.getId())
								.eq(OrderInfo::getStatus, OrderInfoEnum.STATUS_10.getValue()));
					}else if(count > grouponUser2.getGrouponNum()){//???????????????
						grouponUser.setValidBeginTime(orderInfo.getPaymentTime());
						grouponUser.setValidEndTime(orderInfo.getPaymentTime().plusHours(grouponInfo.getDuration()));
						grouponUser.setIsLeader(CommonConstants.YES);
						grouponUser.setGroupId(grouponUser.getId());
						orderInfo.setRelationId(grouponUser.getId());
						orderInfo.setStatus(OrderInfoEnum.STATUS_10.getValue());
					}else{//????????????
						orderInfo.setStatus(OrderInfoEnum.STATUS_10.getValue());
					}
				}
				grouponUser.setOrderId(orderInfo.getId());
				grouponUserMapper.updateById(grouponUser);
				grouponInfo.setLaunchNum(grouponInfo.getLaunchNum()+1);//????????????+1
				grouponInfoMapper.updateById(grouponInfo);
			}

			Map<String, List<GoodsSpu>> resultGoodsSpu = listGoodsSpu.stream().collect(Collectors.groupingBy(GoodsSpu::getDeliveryPlaceId));
			if(resultGoodsSpu.size() <= 1){//????????????????????????
				if(MallConstants.DELIVERY_WAY_2.equals(orderInfo.getDeliveryWay())){//????????????2????????????????????????????????????
					orderInfo.setLogisticsId(listGoodsSpu.get(0).getDeliveryPlaceId());
				}
				baseMapper.updateById(orderInfo);//????????????
			}else{//???????????????????????????????????????????????????????????????????????????,????????????????????????????????????
				OrderLogistics orderLogistics = orderLogisticsService.getById(orderInfo.getLogisticsId());
				resultGoodsSpu.forEach((key, value) -> {
					List<OrderItem> listOrderItem1 = new ArrayList<>();
					value.forEach(item ->{
						listOrderItem1.addAll(resultList.get(item.getId()));
					});
					OrderInfo orderInfo1 = new OrderInfo();
					BeanUtil.copyProperties(orderInfo,orderInfo1);
					orderInfo1.setOrderNo(IdUtil.getSnowflake(0,0).nextIdStr());
					orderInfo1.setTenantId(null);
					orderInfo1.setId(null);
					orderInfo1.setSalesPrice(BigDecimal.ZERO);
					orderInfo1.setPaymentPrice(BigDecimal.ZERO);
					orderInfo1.setFreightPrice(BigDecimal.ZERO);
					orderInfo1.setPaymentPoints(0);
					orderInfo1.setPaymentCouponPrice(BigDecimal.ZERO);
					orderInfo1.setPaymentPointsPrice(BigDecimal.ZERO);
					listOrderItem1.forEach(item -> {
						GoodsSku goodsSku = goodsSkuService.getById(item.getSkuId());
						orderInfo1.setSalesPrice(orderInfo1.getSalesPrice().add(goodsSku.getSalesPrice().multiply(new BigDecimal(item.getQuantity()))));
						orderInfo1.setFreightPrice(orderInfo1.getFreightPrice().add(item.getFreightPrice()));
						orderInfo1.setPaymentPrice(orderInfo1.getPaymentPrice().add(item.getPaymentPrice()));
						orderInfo1.setPaymentPoints(orderInfo1.getPaymentPoints() + (item.getPaymentPoints() != null ? item.getPaymentPoints() : 0));
						orderInfo1.setPaymentCouponPrice(orderInfo1.getPaymentCouponPrice().add((item.getPaymentCouponPrice() != null ? item.getPaymentCouponPrice() : BigDecimal.ZERO)));
						orderInfo1.setPaymentPointsPrice(orderInfo1.getPaymentPointsPrice().add((item.getPaymentPointsPrice() != null ? item.getPaymentPointsPrice() : BigDecimal.ZERO)));
					});
					if(MallConstants.DELIVERY_WAY_2.equals(orderInfo1.getDeliveryWay())){//????????????2????????????????????????????????????
						orderInfo1.setLogisticsId(key);
					}else{
						orderLogistics.setId(null);
						orderLogistics.setTenantId(null);
						orderLogisticsService.save(orderLogistics);
						orderInfo1.setLogisticsId(orderLogistics.getId());
					}
					//????????????
					baseMapper.insert(orderInfo1);
					//??????????????????
					listOrderItem1.forEach(item -> item.setOrderId(orderInfo1.getId()));
					orderItemService.updateBatchById(listOrderItem1);
				});
				//?????????????????????????????????
				baseMapper.deleteById(orderInfo.getId());
				orderLogisticsService.removeById(orderInfo.getLogisticsId());
			}
			//??????????????????
			try {
				WxTemplateMsgSendDTO wxTemplateMsgSendDTO = new WxTemplateMsgSendDTO();
				wxTemplateMsgSendDTO.setMallUserId(orderInfo.getUserId());
				wxTemplateMsgSendDTO.setUseType(ConfigConstant.WX_TMP_USE_TYPE_2);
				wxTemplateMsgSendDTO.setPage("pages/order/order-detail/index?id="+orderInfo.getId());
				HashMap<String, HashMap<String, String>> data = new HashMap<>();
				data.put("character_string1", (HashMap)JSONUtil.toBean("{\"value\": \""+orderInfo.getOrderNo()+"\"}",Map.class));
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy???MM???dd??? HH:mm");
				data.put("time4", (HashMap)JSONUtil.toBean("{\"value\": \""+orderInfo.getCreateTime().format(dtf)+"\"}",Map.class));
				String thing3 = orderInfo.getName();
				thing3 = thing3.length() > 20 ? thing3.substring(0,19) : thing3;
				data.put("thing3", (HashMap)JSONUtil.toBean("{\"value\": \""+thing3+"\"}",Map.class));
				data.put("amount2", (HashMap)JSONUtil.toBean("{\"value\": \""+orderInfo.getPaymentPrice()+"\"}",Map.class));
				data.put("amount5", (HashMap)JSONUtil.toBean("{\"value\": \""+orderInfo.getPaymentPrice()+"\"}",Map.class));
				wxTemplateMsgSendDTO.setData(data);
				feignWxTemplateMsgService.sendTemplateMsg(wxTemplateMsgSendDTO, SecurityConstants.FROM_IN);
			}catch (Exception e){
				log.error("?????????????????????????????????"+e.getMessage(), e);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void notifyLogisticsr(String logisticsId, JSONObject jsonObject) {
		OrderLogistics orderLogistics = orderLogisticsService.getById(logisticsId);
		if(orderLogistics != null){
			String status = jsonObject.getStr("status");
			if("abort".equals(status)){//??????
				orderLogistics.setStatus(OrderLogisticsEnum.STATUS_ER.getValue());
				orderLogistics.setMessage(jsonObject.getStr("message"));
			}else{
				orderLogisticsDetailService.remove(Wrappers.<OrderLogisticsDetail>lambdaQuery()
						.eq(OrderLogisticsDetail::getLogisticsId,logisticsId));//?????????
				JSONObject jsonResult =(JSONObject) jsonObject.get("lastResult");
				orderLogistics.setStatus(jsonResult.getStr("state"));
				orderLogistics.setIsCheck(jsonResult.getStr("ischeck"));
				JSONArray jSONArray = jsonResult.getJSONArray("data");
				List<OrderLogisticsDetail> listOrderLogisticsDetail = new ArrayList<>();
				jSONArray.forEach(object -> {
					JSONObject jsonData = JSONUtil.parseObj(object);
					OrderLogisticsDetail orderLogisticsDetail = new OrderLogisticsDetail();
					orderLogisticsDetail.setLogisticsId(logisticsId);
					orderLogisticsDetail.setLogisticsTime(LocalDateTimeUtils.parse(jsonData.getStr("time")));
					orderLogisticsDetail.setLogisticsInformation(jsonData.getStr("context"));
					listOrderLogisticsDetail.add(orderLogisticsDetail);
				});
				orderLogisticsDetailService.saveBatch(listOrderLogisticsDetail);
				//??????????????????????????????
				Optional<OrderLogisticsDetail> orderLogisticsDetail = listOrderLogisticsDetail.stream().collect(Collectors.maxBy(Comparator.comparing(OrderLogisticsDetail::getLogisticsTime)));
				orderLogistics.setMessage(orderLogisticsDetail.get().getLogisticsInformation());
			}
			orderLogisticsService.updateById(orderLogistics);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void editPrice(OrderItem orderItem) {
		BigDecimal editPrice = orderItem.getPaymentPrice();
		orderItem = orderItemService.getById(orderItem.getId());
		BigDecimal oldPrice = orderItem.getPaymentPrice();
		orderItem.setPaymentPrice(editPrice);
		OrderInfo orderInfo = baseMapper.selectById(orderItem.getOrderId());
		orderInfo.setPaymentPrice(orderInfo.getPaymentPrice().add(editPrice.subtract(oldPrice)));
		orderItemService.updateById(orderItem);
		orderInfo.setOrderNo(IdUtil.getSnowflake(0,0).nextIdStr());
		baseMapper.updateById(orderInfo);
	}

}

package com.hongnx.cloud.mall.common.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hongnx.cloud.mall.common.entity.ShoppingCart;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 下单参数
 *
 * @date 2019-08-13 10:18:34
 */
@Data
@ApiModel(description = "下单参数")
public class PlaceOrderDTO extends Model<ShoppingCart> {
	private static final long serialVersionUID = 1L;

    /**
   * sku
   */
	@ApiModelProperty(value = "sku")
    private List<PlaceOrderSkuDTO> skus;
	/**
	 * 支付方式1、货到付款；2、在线支付
	 */
	@ApiModelProperty(value = "支付方式1、货到付款；2、在线支付")
	private String paymentWay;
	/**
	 * 配送方式1、普通快递；2、上门自提
	 */
	@ApiModelProperty(value = "配送方式")
	private String deliveryWay;
	/**
	 * 付款方式1、微信支付
	 */
	@ApiModelProperty(value = "付款方式")
	private String paymentType;
	/**
	 * 买家留言
	 */
	@ApiModelProperty(value = "买家留言")
	private String userMessage;
	/**
	 * 应用类型1、小程序
	 */
	@ApiModelProperty(value = "应用类型")
	private String appType;
	/**
	 * 应用ID
	 */
	@ApiModelProperty(value = "应用ID")
	private String appId;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private String userId;
	/**
	 * 用户收货地址ID
	 */
	@ApiModelProperty(value = "用户收货地址ID")
	private String userAddressId;
	/**
	 * 订单类型（0、普通订单；1、砍价订单；2、拼团订单；3、秒杀订单）
	 */
	@ApiModelProperty(value = "订单类型")
	private String orderType;
	/**
	 * 营销ID（砍价ID、拼团ID、秒杀ID）
	 */
	@ApiModelProperty(value = "营销ID（砍价ID、拼团ID、秒杀ID）")
	private String marketId;
	/**
	 * 营销记录ID（砍价记录ID、拼团记录组ID（团长的拼团记录ID））
	 */
	@ApiModelProperty(value = "营销记录ID（砍价记录ID、拼团记录组ID（团长的拼团记录ID）、秒杀会场ID）")
	private String relationId;
}

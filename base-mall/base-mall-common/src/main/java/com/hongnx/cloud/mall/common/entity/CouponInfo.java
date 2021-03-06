package com.hongnx.cloud.mall.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;

/**
 * 电子券
 *
 * @date 2019-12-14 11:30:58
 */
@Data
@TableName("coupon_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "电子券")
public class CouponInfo extends Model<CouponInfo> {
	private static final long serialVersionUID = 1L;

	/**
	 * PK
	 */
	@ApiModelProperty(value = "PK")
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 所属租户
	 */
	@ApiModelProperty(value = "所属租户")
	private String tenantId;
	/**
	 * 逻辑删除标记（0：显示；1：隐藏）
	 */
	@ApiModelProperty(value = "逻辑删除标记")
	private String delFlag;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
	/**
	 * 最后更新时间
	 */
	@ApiModelProperty(value = "最后更新时间")
	private LocalDateTime updateTime;
	/**
	 * 创建者ID
	 */
	@ApiModelProperty(value = "创建者ID")
	private String createId;
	/**
	 * 排序字段
	 */
	@ApiModelProperty(value = "排序字段")
	private Integer sort;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;
	/**
	 * 类型1、代金券；2：折扣券
	 */
	@ApiModelProperty(value = "类型1、代金券；2：折扣券")
	private String type;
	/**
	 * 订单金额满多少可使用
	 */
	@ApiModelProperty(value = "订单金额满多少可使用")
	private BigDecimal premiseAmount;
	/**
	 * 到期类型1、领券后生效；2：固定时间段
	 */
	@ApiModelProperty(value = "到期类型1、领券后生效；2：固定时间段")
	private String expireType;
	/**
	 * 库存
	 */
	@ApiModelProperty(value = "库存")
	private Integer stock;
	/**
	 * 减免金额（代金券特有）
	 */
	@ApiModelProperty(value = "减免金额")
	private BigDecimal reduceAmount;
	/**
	 * 折扣率（折扣券特有）
	 */
	@ApiModelProperty(value = "折扣率")
	private BigDecimal discount;
	/**
	 * 有效天数（领券后生效特有）
	 */
	@ApiModelProperty(value = "有效天数")
	private Integer validDays;
	/**
	 * 有效开始时间（固定时间段特有）
	 */
	@ApiModelProperty(value = "有效开始时间")
	private LocalDateTime validBeginTime;
	/**
	 * 有效结束时间（固定时间段特有）
	 */
	@ApiModelProperty(value = "有效结束时间")
	private LocalDateTime validEndTime;
	/**
	 * 适用类型1、全部商品；2、指定商品可用
	 */
	@ApiModelProperty(value = "适用类型1、全部商品；2、指定商品可用")
	private String suitType;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remarks;
	/**
	 * （1：开启；0：关闭）
	 */
	@ApiModelProperty(value = "1：开启；0：关闭")
	private String enable;
	/**
	 * 版本号
	 */
	@Version
	private Integer version;

	@TableField(exist = false)
	private CouponUser couponUser;

	@TableField(exist = false)
	private List<GoodsSpu> listGoodsSpu;

}

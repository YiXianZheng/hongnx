package com.hongnx.cloud.mall.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hongnx.cloud.mall.common.enums.OrderRefundsEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;

/**
 * 退款详情
 *
 * @date 2019-11-14 16:35:25
 */
@Data
@TableName("order_refunds")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "退款详情")
public class OrderRefunds extends Model<OrderRefunds> {
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
	 * 订单ID
	 */
	@ApiModelProperty(value = "订单ID")
	private String orderId;
	/**
	 * 订单详情ID
	 */
	@ApiModelProperty(value = "订单详情ID")
	private String orderItemId;
	/**
	 * 退款状态0：撤销退换申请；1：退款申请中；11、同意退款；12、拒绝退款；2：退货退款申请中；21：同意退货退款/退货中；22：拒绝退货退款；211：收到退货同意退款；212：收到退货拒绝退款
	 */
	@ApiModelProperty(value = "退款状态")
	private String status;
	@TableField(exist = false)
	private String statusDesc;

	public String getStatusDesc() {
		if(this.status == null){
			return null;
		}
		return OrderRefundsEnum.valueOf(OrderRefundsEnum.STATUS_PREFIX + "_" + this.status).getDesc();
	}
	/**
	 * 退款金额
	 */
	private BigDecimal refundAmount;
	/**
	 * 退款流水号
	 */
	private String refundTradeNo;
	/**
	 * 退款原因
	 */
	private String refundReson;
	/**
	 * 拒绝退款原因
	 */
	private String refuseRefundReson;

	@TableField(exist = false)
	private OrderInfo orderInfo;

	@TableField(exist = false)
	private OrderItem orderItem;
}

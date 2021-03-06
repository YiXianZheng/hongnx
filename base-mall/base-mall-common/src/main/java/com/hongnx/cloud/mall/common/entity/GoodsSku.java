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
 * 商品sku
 *
 * @date 2019-08-13 10:18:34
 */
@Data
@TableName("goods_sku")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "商品sku")
public class GoodsSku extends Model<GoodsSku> {
private static final long serialVersionUID = 1L;

    /**
   * PK
   */
	@ApiModelProperty(value = "PK")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
   * 创建时间
   */
	@ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
   * 最后更新时间
   */
	@ApiModelProperty(value = "最后更新时间PK")
    private LocalDateTime updateTime;
    /**
   * 所属租户
   */
	@ApiModelProperty(value = "所属租户")
    private String tenantId;
    /**
   * sku编码
   */
	@ApiModelProperty(value = "sku编码")
    private String skuCode;
    /**
   * 商品Id
   */
	@ApiModelProperty(value = "商品Id")
    private String spuId;
    /**
   * 图片
   */
	@ApiModelProperty(value = "图片")
    private String picUrl;
    /**
   * 销售价格
   */
	@ApiModelProperty(value = "销售价格")
    private BigDecimal salesPrice;
    /**
   * 市场价
   */
	@ApiModelProperty(value = "市场价")
    private BigDecimal marketPrice;
    /**
   * 成本价
   */
	@ApiModelProperty(value = "成本价")
    private BigDecimal costPrice;
    /**
   * 库存
   */
	@ApiModelProperty(value = "库存")
    private Integer stock;
    /**
   * 重量
   */
	@ApiModelProperty(value = "重量")
    private BigDecimal weight;
    /**
   * 体积
   */
	@ApiModelProperty(value = "体积")
    private BigDecimal volume;
	/**
	 * 是否启用1、是；0否
	 */
	@ApiModelProperty(value = "是否启用1、是；0否")
	private String enable;
    /**
   * 逻辑删除标记（0：显示；1：隐藏）
   */
	@ApiModelProperty(value = "逻辑删除标记")
    private String delFlag;
	/**
	 * 版本号
	 */
	@Version
	private Integer version;
	@TableField(exist = false)
	private List<GoodsSkuSpecValue> specs;

	@TableField(exist = false)
	private String name;
}

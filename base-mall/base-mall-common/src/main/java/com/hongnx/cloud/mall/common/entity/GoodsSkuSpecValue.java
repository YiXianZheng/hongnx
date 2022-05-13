package com.hongnx.cloud.mall.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;

/**
 * 商品sku规格值
 *
 * @date 2019-08-13 10:19:09
 */
@Data
@TableName("goods_sku_spec_value")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "商品sku规格值")
public class GoodsSkuSpecValue extends Model<GoodsSkuSpecValue> {
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
	 * spu_id
	 */
	@ApiModelProperty(value = "spu_id")
	private String spuId;
    /**
   * sku_id
   */
	@ApiModelProperty(value = "sku_id")
    private String skuId;
    /**
   * 规格值id
   */
	@ApiModelProperty(value = "规格值id")
    private String specValueId;
    /**
   * 创建时间
   */
	@ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
   * 更新时间
   */
	@ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
	/**
	 * 规格id
	 */
	@ApiModelProperty(value = "规格id")
	@TableField(exist = false)
	private String specId;
	/**
	 * 规格值名
	 */
	@ApiModelProperty(value = "规格值名")
	@TableField(exist = false)
	private String specValueName;
	/**
	 * 排序字段
	 */
	@ApiModelProperty(value = "排序字段")
	private Integer sort;
  
}

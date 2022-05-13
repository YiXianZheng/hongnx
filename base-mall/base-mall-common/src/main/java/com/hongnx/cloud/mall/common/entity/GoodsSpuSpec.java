package com.hongnx.cloud.mall.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;

/**
 * spu规格
 *
 * @date 2019-08-13 16:56:46
 */
@Data
@TableName("goods_spu_spec")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "spu规格")
public class GoodsSpuSpec extends Model<GoodsSpuSpec> {
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
   * spec_id
   */
	@ApiModelProperty(value = "spec_id")
    private String specId;
	/**
	 * 规格名
	 */
	@ApiModelProperty(value = "规格名")
	private String specName;
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
	 * 排序字段
	 */
	@ApiModelProperty(value = "排序字段")
	private Integer sort;
  
}

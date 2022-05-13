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
 * 规格值
 *
 * @date 2019-08-13 16:11:05
 */
@Data
@TableName("goods_spec_value")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "规格值")
public class GoodsSpecValue extends Model<GoodsSpecValue> {
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
   * 规格Id
   */
	@ApiModelProperty(value = "规格Id")
    private String specId;
    /**
   * 名称
   */
	@ApiModelProperty(value = "名称")
    private String name;
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
  
}

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
 * 物流详情
 *
 * @date 2019-09-21 12:39:00
 */
@Data
@TableName("order_logistics_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "物流详情")
public class OrderLogisticsDetail extends Model<OrderLogisticsDetail> {
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
   * 物流id
   */
	@ApiModelProperty(value = "物流id")
    private String logisticsId;
    /**
   * 物流时间
   */
	@ApiModelProperty(value = "物流时间")
    private LocalDateTime logisticsTime;
    /**
   * 物流信息
   */
	@ApiModelProperty(value = "物流信息")
    private String logisticsInformation;
  
}

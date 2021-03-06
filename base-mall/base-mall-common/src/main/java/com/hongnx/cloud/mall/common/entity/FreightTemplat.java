package com.hongnx.cloud.mall.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;

/**
 * 运费模板
 *
 * @date 2019-12-24 16:09:31
 */
@Data
@TableName("freight_templat")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "运费模板")
public class FreightTemplat extends Model<FreightTemplat> {
    private static final long serialVersionUID=1L;

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
     * 模板类型1、买家承担运费；2、卖家包邮
     */
	@ApiModelProperty(value = "模板类型1、买家承担运费；2、卖家包邮")
    private String type;
    /**
     * 计费方式1、按件数；2、按重量；3、按体积
     */
	@ApiModelProperty(value = "计费方式1、按件数；2、按重量；3、按体积")
    private String chargeType;
    /**
     * 首件、首体积、首重量
     */
	@ApiModelProperty(value = "首件、首体积、首重量")
    private BigDecimal firstNum;
    /**
     * 首运费
     */
	@ApiModelProperty(value = "首运费")
    private BigDecimal firstFreight;
    /**
     * 续件、续体积、续重量
     */
	@ApiModelProperty(value = "续件、续体积、续重量")
    private BigDecimal continueNum;
    /**
     * 续运费
     */
	@ApiModelProperty(value = "续运费")
    private BigDecimal continueFreight;

}

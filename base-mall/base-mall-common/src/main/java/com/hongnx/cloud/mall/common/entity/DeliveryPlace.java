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
 * 发货地
 *
 * @date 2020-02-09 22:23:53
 */
@Data
@TableName("delivery_place")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "发货地")
public class DeliveryPlace extends Model<DeliveryPlace> {
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
     * 创建者ID
     */
	@ApiModelProperty(value = "创建者ID")
    private String createId;
    /**
     * 地方
     */
	@ApiModelProperty(value = "地方")
    private String place;
	/**
	 * 详细地址
	 */
	@ApiModelProperty(value = "详细地址")
	private String address;
	/**
	 * 电话号码
	 */
	@ApiModelProperty(value = "电话号码")
	private String telNum;

}

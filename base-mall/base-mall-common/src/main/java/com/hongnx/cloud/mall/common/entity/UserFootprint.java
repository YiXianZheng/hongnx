package com.hongnx.cloud.mall.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 用户足迹
 *
 * @date 2020-12-24 22:15:45
 */
@Data
@TableName("user_footprint")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户足迹")
public class UserFootprint extends Model<UserFootprint> {
    private static final long serialVersionUID=1L;

    /**
     * PK
     */
    @TableId(type = IdType.ASSIGN_ID)
    @NotNull(message = "PK不能为空")
    @ApiModelProperty(value = "PK")
    private String id;
    /**
     * 所属租户
     */
    @NotNull(message = "所属租户不能为空")
    @ApiModelProperty(value = "所属租户")
    private String tenantId;
    /**
     * 逻辑删除标记（0：显示；1：隐藏）
     */
    @NotNull(message = "逻辑删除标记（0：显示；1：隐藏）不能为空")
    @ApiModelProperty(value = "逻辑删除标记（0：显示；1：隐藏）")
    private String delFlag;
    /**
     * 创建时间
     */
    @NotNull(message = "创建时间不能为空")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    @NotNull(message = "最后更新时间不能为空")
    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updateTime;
    /**
     * 用户编号
     */
    @NotNull(message = "用户编号不能为空")
    @ApiModelProperty(value = "用户编号")
    private String userId;
    /**
     * 关联ID：商品ID
     */
    @NotNull(message = "关联ID：商品ID不能为空")
    @ApiModelProperty(value = "关联ID：商品ID")
    private String relationId;

	@TableField(exist = false)
	private GoodsSpu goodsSpu;

	@TableField(exist = false)
	private UserInfo userInfo;
}

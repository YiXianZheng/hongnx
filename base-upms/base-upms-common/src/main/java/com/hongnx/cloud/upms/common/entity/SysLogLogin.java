package com.hongnx.cloud.upms.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
    import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

/**
 * 登录日志表
 *
 * @date 2020-03-28 16:39:04
 */
@Data
@TableName("sys_log_login")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "登录日志表")
public class SysLogLogin extends Model<SysLogLogin> {
    private static final long serialVersionUID=1L;

    /**
     * PK
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "PK")
    private String id;
    /**
     * 创建者ID
     */
    @ApiModelProperty(value = "创建者ID")
    private String createId;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;
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
     * IP地址
     */
    @ApiModelProperty(value = "IP地址")
    private String remoteAddr;
    /**
     * 用户代理
     */
    @ApiModelProperty(value = "用户代理")
    private String userAgent;
    /**
     * 请求URI
     */
    @ApiModelProperty(value = "请求URI")
    private String requestUri;
    /**
     * 操作提交的数据
     */
    @ApiModelProperty(value = "操作提交的数据")
    private String params;
    /**
     * 逻辑删除标记（0：显示；1：隐藏）
     */
    @ApiModelProperty(value = "逻辑删除标记（0：显示；1：隐藏）")
    private String delFlag;
    /**
     * 地址信息
     */
    @ApiModelProperty(value = "地址信息")
    private String address;
    /**
     * 所属租户
     */
    @ApiModelProperty(value = "所属租户")
    private String tenantId;
	/**
	 * 日志类型
	 */
	@ApiModelProperty(value = "日志类型")
	private String type;
	/**
	 * 异常信息
	 */
	@ApiModelProperty(value = "异常信息")
	private String exception;
}

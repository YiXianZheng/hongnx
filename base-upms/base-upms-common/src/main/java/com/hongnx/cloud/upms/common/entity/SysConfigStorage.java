package com.hongnx.cloud.upms.common.entity;

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
 * 存储配置
 *
 * @date 2020-02-03 20:07:38
 */
@Data
@TableName("sys_config_storage")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "存储配置")
public class SysConfigStorage extends Model<SysConfigStorage> {
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
     * 存储类型1、阿里OSS；2、七牛云；3、本地
     */
	@ApiModelProperty(value = "存储类型1、阿里OSS；2、七牛云；3、本地")
    private String storageType;
    /**
     * 地域节点
     */
	@ApiModelProperty(value = "地域节点")
    private String endpoint;
    /**
     * accessKeyId
     */
	@ApiModelProperty(value = "accessKeyId")
    private String accessKeyId;
    /**
     * 密钥
     */
	@ApiModelProperty(value = "密钥")
    private String accessKeySecret;
    /**
     * 域名
     */
	@ApiModelProperty(value = "域名")
    private String bucket;
	/**
	 * 图片水印内容
	 */
	@ApiModelProperty(value = "图片水印内容")
    private String waterMarkContent;
}

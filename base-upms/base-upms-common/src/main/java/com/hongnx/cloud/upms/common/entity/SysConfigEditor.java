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
 * 编辑器配置
 *
 * @date 2020-02-10 14:23:30
 */
@Data
@TableName("sys_config_editor")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "编辑器配置")
public class SysConfigEditor extends Model<SysConfigEditor> {
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
     * 编辑器类型1、quill-editor；2、froala
     */
	@ApiModelProperty(value = "编辑器类型")
    private String editorType;

}

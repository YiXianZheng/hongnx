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
 * 素材
 *
 * @date 2019-10-26 19:23:45
 */
@Data
@TableName("material")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "素材")
public class Material extends Model<Material> {
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
	@ApiModelProperty(value = "逻辑删除标记（0：显示；1：隐藏）")
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
   * 类型1、图片；2、视频
   */
	@ApiModelProperty(value = "类型1、图片；2、视频")
    private String type;
    /**
   * 分组ID
   */
	@ApiModelProperty(value = "分组ID")
    private String groupId;
    /**
   * 素材名
   */
	@ApiModelProperty(value = "素材名")
    private String name;
    /**
   * 素材链接
   */
	@ApiModelProperty(value = "素材链接")
    private String url;
  
}

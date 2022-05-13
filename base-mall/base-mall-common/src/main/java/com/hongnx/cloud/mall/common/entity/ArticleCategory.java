package com.hongnx.cloud.mall.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 文章分类
 *
 * @date 2020-11-24 14:24:30
 */
@Data
@TableName("article_category")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "文章分类")
public class ArticleCategory extends Model<ArticleCategory> {
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
     * （1：开启；0：关闭）
     */
    @NotNull(message = "（1：开启；0：关闭）不能为空")
    @ApiModelProperty(value = "（1：开启；0：关闭）")
    private String enable;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;
    /**
     * 分类名
     */
    @NotNull(message = "分类名不能为空")
    @ApiModelProperty(value = "分类名")
    private String name;

}

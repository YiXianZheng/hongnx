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
 * 文章
 *
 * @date 2020-11-24 14:44:15
 */
@Data
@TableName("article_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "文章")
public class ArticleInfo extends Model<ArticleInfo> {
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
     * 文章图片
     */
    @NotNull(message = "文章图片不能为空")
    @ApiModelProperty(value = "文章图片")
    private String picUrl;
    /**
     * 文章分类ID
     */
    @NotNull(message = "文章分类ID不能为空")
    @ApiModelProperty(value = "文章分类ID")
    private String categoryId;
    /**
     * 作者
     */
    @NotNull(message = "作者不能为空")
    @ApiModelProperty(value = "作者")
    private String authorName;
    /**
     * 文章标题
     */
    @NotNull(message = "文章标题不能为空")
    @ApiModelProperty(value = "文章标题")
    private String articleTitle;
    /**
     * 文章简介
     */
    @NotNull(message = "文章简介不能为空")
    @ApiModelProperty(value = "文章简介")
    private String articleIntroduction;
    /**
     * 文章内容
     */
    @ApiModelProperty(value = "文章内容")
    private String articleContent;
    /**
     * 原文地址
     */
    @ApiModelProperty(value = "原文地址")
    private String originalUrl;
    /**
     * 是否banner显示（1：是；0：否）
     */
    @NotNull(message = "是否banner显示（1：是；0：否）不能为空")
    @ApiModelProperty(value = "是否banner显示（1：是；0：否）")
    private String isBanner;
    /**
     * 是否热门文章（1：是；0：否）
     */
    @NotNull(message = "是否热门文章（1：是；0：否）不能为空")
    @ApiModelProperty(value = "是否热门文章（1：是；0：否）")
    private String isHot;

}

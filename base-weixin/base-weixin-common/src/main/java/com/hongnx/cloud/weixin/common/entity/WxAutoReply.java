package com.hongnx.cloud.weixin.common.entity;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hongnx.cloud.common.data.mybatis.typehandler.JsonTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 消息自动回复
 *
 * @date 2019-04-18 15:40:39
 */
@Data
@ApiModel(description = "消息自动回复")
@TableName("wx_auto_reply")
@EqualsAndHashCode(callSuper = true)
public class WxAutoReply extends Model<WxAutoReply> {
private static final long serialVersionUID = 1L;

    /**
   * 主键
   */
	@ApiModelProperty(value = "PK")
	@TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
   * 创建者
   */
	@ApiModelProperty(value = "创建者")
    private String createId;
    /**
   * 创建时间
   */
	@ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
   * 更新者
   */
	@ApiModelProperty(value = "更新者")
    private String updateId;
    /**
   * 更新时间
   */
	@ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    /**
   * 备注
   */
	@ApiModelProperty(value = "备注")
    private String remark;
    /**
   * 逻辑删除标记（0：显示；1：隐藏）
   */
	@ApiModelProperty(value = "逻辑删除标记（0：显示；1：隐藏）")
    private String delFlag;
    /**
   * 所属租户
   */
	@ApiModelProperty(value = "所属租户")
    private String tenantId;
    /**
   * 公众号配置ID、小程序AppID
   */
	@ApiModelProperty(value = "公众号配置ID、小程序AppID")
	@NotNull(message = "公众号不能为空")
    private String appId;
    /**
   * 类型（1、关注时回复；2、消息回复；3、关键词回复）
   */
	@ApiModelProperty(value = "类型（1、关注时回复；2、消息回复；3、关键词回复）")
	@NotNull(message = "类型不能为空")
    private String type;
    /**
   * 关键词
   */
	@ApiModelProperty(value = "关键词")
    private String reqKey;
    /**
   * 请求消息类型（text：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置）
   */
	@ApiModelProperty(value = "请求消息类型（text：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置）")
    private String reqType;
    /**
   * 回复消息类型（text：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文）
   */
	@ApiModelProperty(value = "回复消息类型（text：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文）")
	@NotNull(message = "回复消息类型不能为空")
    private String repType;
    /**
   * 回复类型文本匹配类型（1、全匹配，2、半匹配）
   */
	@ApiModelProperty(value = "回复类型文本匹配类型（1、全匹配，2、半匹配）")
    private String repMate;
    /**
   * 回复类型文本保存文字
   */
	@ApiModelProperty(value = "回复类型文本保存文字")
    private String repContent;
	/**
	 * 回复的素材名、视频和音乐的标题
	 */
	@ApiModelProperty(value = "回复的素材名、视频和音乐的标题")
	private String repName;
    /**
   * 回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id
   */
	@ApiModelProperty(value = "回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id")
    private String repMediaId;
    /**
   * 视频和音乐的描述
   */
	@ApiModelProperty(value = "视频和音乐的描述")
    private String repDesc;
    /**
   * 链接
   */
	@ApiModelProperty(value = "链接")
    private String repUrl;
    /**
   * 高质量链接
   */
	@ApiModelProperty(value = "高质量链接")
    private String repHqUrl;
	/**
	 * 缩略图的媒体id
	 */
	@ApiModelProperty(value = "缩略图的媒体id")
	private String repThumbMediaId;
	/**
	 * 缩略图url
	 */
	@ApiModelProperty(value = "缩略图url")
	private String repThumbUrl;

	/**
	 * 图文消息的内容
	 */
	@ApiModelProperty(value = "图文消息的内容")
	@TableField(typeHandler = JsonTypeHandler.class, jdbcType= JdbcType.VARCHAR)
	private JSONObject content;

}

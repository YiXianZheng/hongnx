package com.hongnx.cloud.weixin.common.entity;

import cn.binarywang.wx.miniapp.bean.live.WxMaLiveResult;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信用户
 *
 * @date 2019-03-25 15:39:39
 */
@Data
@ApiModel(description = "小程序直播")
public class WxMaLive extends WxMaLiveResult.RoomInfo {
private static final long serialVersionUID = 1L;

    /**
   * 公众号配置ID、小程序AppID
   */
	@ApiModelProperty(value = "公众号配置ID、小程序AppID")
    private String appId;
	@SerializedName("feeds_img")
	private String feedsImg;
}

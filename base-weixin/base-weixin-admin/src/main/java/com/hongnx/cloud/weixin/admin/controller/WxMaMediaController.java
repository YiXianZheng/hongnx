package com.hongnx.cloud.weixin.admin.controller;

import cn.binarywang.wx.miniapp.api.WxMaMediaService;
import com.hongnx.cloud.common.core.util.FileUtils;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.weixin.admin.config.ma.WxMaConfiguration;
import com.hongnx.cloud.weixin.common.constant.WxReturnCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 小程序直播
 *
 * @date 2019-03-23 21:26:35
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxmamedia")
@Api(value = "wxmamedia", tags = "小程序临时素材接口")
public class WxMaMediaController {

	/**
	 * 素材上传
	 * @param mulFile
	 * @return
	 */
	@ApiOperation(value = "素材上传")
	@PostMapping("/upload")
	public R imageUploadV3(@RequestParam("file") MultipartFile mulFile, @RequestParam("appId") String appId, @RequestParam("mediaType") String mediaType) throws IOException {
		WxMaMediaService wxMaMediaService = WxMaConfiguration.getMaService(appId).getMediaService();
		File file = FileUtils.multipartFileToFile(mulFile);
		try {
			WxMediaUploadResult wxMediaUploadResult = wxMaMediaService.uploadMedia(mediaType, file);
			return R.ok(wxMediaUploadResult);
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("素材上传失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}
}

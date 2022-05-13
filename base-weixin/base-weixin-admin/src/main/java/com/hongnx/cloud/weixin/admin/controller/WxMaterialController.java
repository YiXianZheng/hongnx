package com.hongnx.cloud.weixin.admin.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.weixin.common.constant.WxReturnCode;
import com.hongnx.cloud.weixin.common.entity.ImageManager;
import com.hongnx.cloud.weixin.admin.config.mp.WxMpConfiguration;
import com.hongnx.cloud.common.core.util.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.bean.material.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信素材
 *
 * @date 2019-03-23 21:26:35
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxmaterial")
@Api(value = "wxmaterial", tags = "微信素材管理")
public class WxMaterialController {

	/**
	 * 上传非图文微信素材
	 * @param mulFile
	 * @param appId
	 * @param mediaType
	 * @return
	 */
	@ApiOperation(value = "上传非图文微信素材")
	@PostMapping("/materialFileUpload")
	//	@PreAuthorize("@ato.hasAuthority('wxmp:wxmaterial:add')")
	public R materialFileUpload(@RequestParam("file") MultipartFile mulFile,
								@RequestParam("title") String title,
								@RequestParam("introduction") String introduction,
								@RequestParam("appId") String appId,
								@RequestParam("mediaType") String mediaType) {
		try {
			WxMpMaterial material = new WxMpMaterial();
			material.setName(mulFile.getOriginalFilename());
			if(WxConsts.MediaFileType.VIDEO.equals(mediaType)){
				material.setVideoTitle(title);
				material.setVideoIntroduction(introduction);
			}
			File file = FileUtils.multipartFileToFile(mulFile);
			material.setFile(file);
			WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
			WxMpMaterialUploadResult wxMpMaterialUploadResult = wxMpMaterialService.materialFileUpload(mediaType,material);
			WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem wxMpMaterialFileBatchGetResult = new WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem();
			wxMpMaterialFileBatchGetResult.setName(file.getName());
			wxMpMaterialFileBatchGetResult.setMediaId(wxMpMaterialUploadResult.getMediaId());
			wxMpMaterialFileBatchGetResult.setUrl(wxMpMaterialUploadResult.getUrl());
			return R.ok(wxMpMaterialFileBatchGetResult);
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("上传非图文微信素材失败" + e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("上传失败", e);
			return R.failed(e.getLocalizedMessage());
		}
	}

	/**
	 * 上传图文消息内的图片获取URL
	 * @param mulFile
	 * @return
	 */
	@ApiOperation(value = "上传图文消息内的图片获取URL")
	@PostMapping("/newsImgUpload")
	//	@PreAuthorize("@ato.hasAuthority('wxmp:wxmaterial:add')")
	public String newsImgUpload(@RequestParam("file") MultipartFile mulFile,@RequestParam("appId") String appId) throws Exception {
		File file = FileUtils.multipartFileToFile(mulFile);
		WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
		WxMediaImgUploadResult wxMediaImgUploadResult = wxMpMaterialService.mediaImgUpload(file);
		Map<Object, Object> responseData = new HashMap<>();
		responseData.put("link", wxMediaImgUploadResult.getUrl());
		return JSONUtil.toJsonStr(responseData);
	}

	/**
	 * 通过id删除微信素材
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "通过id删除微信素材")
	@SysLog("删除微信素材")
	@DeleteMapping
	@PreAuthorize("@ato.hasAuthority('wxmp:wxmaterial:del')")
	public R materialDel(String id,String appId){
		WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
		try {
			return  R.ok(wxMpMaterialService.materialDelete(id));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("删除微信素材失败", e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	* 分页查询
	* @param page 分页对象
	* @param type
	* @return
	*/
	@ApiOperation(value = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('wxmp:wxmaterial:index')")
	public R getWxMaterialPage(Page page,String appId, String type) {
		try {
		  WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
		  int count = (int)page.getSize();
		  int offset = (int)page.getCurrent()*count-count;
		  if(WxConsts.MaterialType.NEWS.equals(type)){
			  return  R.ok(wxMpMaterialService.materialNewsBatchGet(offset,count));
		  }else{
			  return  R.ok(wxMpMaterialService.materialFileBatchGet(type,offset,count));
		  }
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("查询素材失败", e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 分页查询2
	 * @param type
	 * @return
	 */
	@ApiOperation(value = "分页查询2")
	@GetMapping("/page-manager")
//	@PreAuthorize("@ato.hasAuthority('wxmp:wxmaterial:index')")
	public String getWxMaterialPageManager(Integer count, Integer offset, String appId, String type) throws WxErrorException {
		List<ImageManager> listImageManager = new ArrayList<>();
		WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
		List<WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem> list = wxMpMaterialService.materialFileBatchGet(type,offset,count).getItems();
		list.forEach(wxMaterialFileBatchGetNewsItem -> {
			ImageManager imageManager = new ImageManager();
			imageManager.setName(wxMaterialFileBatchGetNewsItem.getMediaId());
			imageManager.setUrl(wxMaterialFileBatchGetNewsItem.getUrl());
			imageManager.setThumb(wxMaterialFileBatchGetNewsItem.getUrl());
			listImageManager.add(imageManager);
		});
		return JSONUtil.toJsonStr(listImageManager);
	}

	/**
	* 获取微信视频素材
	* @param
	* @return R
	*/
	@ApiOperation(value = "获取微信视频素材")
	@GetMapping("/materialVideo")
	@PreAuthorize("@ato.hasAuthority('wxmp:wxmaterial:get')")
	public R getMaterialVideo(String appId,String mediaId){
	  WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
	  try {
		  return  R.ok(wxMpMaterialService.materialVideoInfo(mediaId));
	  } catch (WxErrorException e) {
		  e.printStackTrace();
		  log.error("获取微信视频素材失败", e);
		  return WxReturnCode.wxErrorExceptionHandler(e);
	  }
	}

	/**
	 * 获取微信素材直接文件
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "获取微信素材直接文件")
	@GetMapping("/materialOther")
	@PreAuthorize("@ato.hasAuthority('wxmp:wxmaterial:get')")
	public ResponseEntity<byte[]> getMaterialOther(String appId, String mediaId,String fileName) throws Exception {
		try {
			WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
			//获取文件
			InputStream is = wxMpMaterialService.materialImageOrVoiceDownload(mediaId);
			byte[] body = new byte[is.available()];
			is.read(body);
			HttpHeaders headers = new HttpHeaders();
			//设置文件类型
			headers.add("Content-Disposition", "attchement;filename=" +  URLEncoder.encode(fileName, "UTF-8"));
			headers.add("Content-Type", "application/octet-stream");
			HttpStatus statusCode = HttpStatus.OK;
			//返回数据
			ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
			return entity;
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("获取微信素材直接文件失败", e);
			return null;
		}
	}

	/**
	 * 获取微信临时素材直接文件
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "获取微信临时素材直接文件")
	@GetMapping("/tempMaterialOther")
	@PreAuthorize("@ato.hasAuthority('wxmp:wxmsg:index')")
	public ResponseEntity<byte[]> getTempMaterialOther(String appId, String mediaId,String fileName) throws Exception {
		try {
			WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
			//获取文件
			InputStream is = new FileInputStream(wxMpMaterialService.mediaDownload(mediaId));
			byte[] body = new byte[is.available()];
			is.read(body);
			HttpHeaders headers = new HttpHeaders();
			//设置文件类型
			headers.add("Content-Disposition", "attchement;filename=" +  URLEncoder.encode(fileName, "UTF-8"));
			headers.add("Content-Type", "application/octet-stream");
			HttpStatus statusCode = HttpStatus.OK;
			//返回数据
			ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
			return entity;
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("获取微信素材直接文件失败", e);
			return null;
		}
	}
}

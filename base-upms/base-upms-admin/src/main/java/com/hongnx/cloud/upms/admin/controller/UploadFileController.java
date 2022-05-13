package com.hongnx.cloud.upms.admin.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.util.FileUtils;
import com.hongnx.cloud.common.core.util.WaterMarkUtils;
import com.hongnx.cloud.common.data.tenant.TenantContextHolder;
import com.hongnx.cloud.common.storage.util.UploadFileUtils;
import com.hongnx.cloud.upms.admin.service.SysConfigStorageService;
import com.hongnx.cloud.upms.common.entity.SysConfigStorage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2019/07/14
 * 文件上传
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/file")
@Api(value = "file", tags = "文件上传")
public class UploadFileController {

	private final SysConfigStorageService sysConfigStorageService;

	/**
	 * 上传文件
	 * @param mulFile
	 * @param dir 文件存放目录
	 * @param fileType 文件类型 image:图片
	 * @return
	 */
	@ApiOperation(value = "上传文件")
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile mulFile,
							 @RequestParam("dir") String dir,
							 @RequestParam("fileType") String fileType) throws Exception {
		File file = FileUtils.multipartFileToFile(mulFile);
		Map<Object, Object> responseData = new HashMap<>();
		dir = StrUtil.format("{}/{}",TenantContextHolder.getTenantId(),  dir);
		SysConfigStorage sysConfigStorage = sysConfigStorageService.getOne(Wrappers.emptyWrapper());
		if(sysConfigStorage == null){
			throw new RuntimeException("请先配置文件存储信息");
		}
		if(CommonConstants.FILE_TYPE_IMG.equals(fileType) &&
				StrUtil.isNotBlank(sysConfigStorage.getWaterMarkContent())){//图片添加水印
			//添加水印
			file = WaterMarkUtils.markStr(file, Color.GRAY, sysConfigStorage.getWaterMarkContent());
		}
		responseData.put("link", UploadFileUtils.uploadFile(file,dir,sysConfigStorage));
		return JSONUtil.toJsonStr(responseData);
	}
}

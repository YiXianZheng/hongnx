package com.hongnx.cloud.mall.admin.api.ma;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.common.core.util.FileUtils;
import com.hongnx.cloud.common.data.tenant.TenantContextHolder;
import com.hongnx.cloud.common.storage.util.UploadFileUtils;
import com.hongnx.cloud.upms.common.entity.SysConfigStorage;
import com.hongnx.cloud.upms.common.feign.FeignConfigStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/api/ma/file")
@Api(value = "file", tags = "文件上传")
public class UploadFileController {

	private final FeignConfigStorageService feignConfigStorageService;

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
		dir = StrUtil.format("{}/{}", TenantContextHolder.getTenantId(),  dir);
		SysConfigStorage sysConfigStorage = feignConfigStorageService.getSysConfigStorage(SecurityConstants.FROM_IN).getData();
		if(sysConfigStorage == null){
			throw new RuntimeException("请先配置文件存储信息");
		}
		responseData.put("link", UploadFileUtils.uploadFile(file,dir,sysConfigStorage));
		return JSONUtil.toJsonStr(responseData);
	}
}

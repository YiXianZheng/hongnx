package com.hongnx.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.NoticeService;
import com.hongnx.cloud.mall.common.entity.Notice;
import com.hongnx.cloud.weixin.common.util.WxMaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 商城通知
 *
 * @date 2019-11-09 19:37:56
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/notice")
@Api(value = "notice", tags = "商城通知API")
public class NoticeApi {

    private final NoticeService noticeService;

	/**
	 * 查询商城通知
	 * @param notice
	 * @return R
	 */
	@ApiOperation(value = "查询商城通知")
	@GetMapping
	public R getOne(HttpServletRequest request, Notice notice){
		notice.setAppId(WxMaUtil.getAppId(request));
		notice = noticeService.getOne(Wrappers.query(notice));
		return R.ok(notice);
	}

}

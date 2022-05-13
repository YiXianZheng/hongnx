package com.hongnx.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.SeckillHallInfoService;
import com.hongnx.cloud.mall.admin.service.SeckillHallService;
import com.hongnx.cloud.mall.admin.service.SeckillInfoService;
import com.hongnx.cloud.mall.common.entity.SeckillHall;
import com.hongnx.cloud.mall.common.entity.SeckillHallInfo;
import com.hongnx.cloud.mall.common.entity.SeckillInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀商品
 *
 * @date 2020-08-12 16:16:45
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/seckillinfo")
@Api(value = "seckillinfo", tags = "秒杀商品API")
public class SeckillInfoApi {

    private final SeckillInfoService seckillInfoService;

	private final SeckillHallInfoService seckillHallInfoService;

	private final SeckillHallService seckillHallService;

	/**
	 * 分页列表
	 * @param page 分页对象
	 * @param seckillInfo
	 * @return
	 */
	@ApiOperation(value = "分页列表")
	@GetMapping("/page")
	public R getPage(Page page, SeckillInfo seckillInfo, SeckillHallInfo seckillHallInfo) {
		seckillInfo.setEnable(CommonConstants.YES);
		return R.ok(seckillInfoService.page2(page, seckillInfo, seckillHallInfo));
	}

	/**
	 * 秒杀商品查询
	 * @param seckillHallInfoId
	 * @return R
	 */
	@ApiOperation(value = "秒杀商品查询")
	@GetMapping("/{seckillHallInfoId}")
	public R getById(@PathVariable("seckillHallInfoId") String seckillHallInfoId) {
		SeckillHallInfo seckillHallInfo = seckillHallInfoService.getById(seckillHallInfoId);
		//查询秒杀商品
		SeckillInfo seckillInfo = seckillInfoService.getById2(seckillHallInfo.getSeckillInfoId());
		//查询会场信息
		SeckillHall seckillHall  = seckillHallService.getById(seckillHallInfo.getSeckillHallId());
		seckillInfo.setSeckillHall(seckillHall);
		return R.ok(seckillInfo);
	}
}

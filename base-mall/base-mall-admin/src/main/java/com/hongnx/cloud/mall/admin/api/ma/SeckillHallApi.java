package com.hongnx.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.SeckillHallService;
import com.hongnx.cloud.mall.common.entity.SeckillHall;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀会场
 *
 * @date 2020-08-12 16:12:35
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/seckillhall")
@Api(value = "seckillhall", tags = "秒杀会场API")
public class SeckillHallApi {

    private final SeckillHallService seckillHallService;

	/**
	 * list列表
	 * @param seckillHall
	 * @return
	 */
	@ApiOperation(value = "list列表")
	@GetMapping("/list")
	public R getList(SeckillHall seckillHall) {
		seckillHall.setEnable(CommonConstants.YES);
		return R.ok(seckillHallService.list(Wrappers.query(seckillHall).orderByAsc("hall_time")));
	}
}

package com.hongnx.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.PointsConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 积分设置
 *
 * @date 2019-12-06 16:15:01
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/pointsconfig")
@Api(value = "pointsconfig", tags = "积分设置API")
public class PointsConfigApi {

    private final PointsConfigService pointsConfigService;

	/**
	 * 查询积分设置
	 * @return R
	 */
	@ApiOperation(value = "查询积分设置")
	@GetMapping()
	public R get() {
		return R.ok(pointsConfigService.getOne(Wrappers.emptyWrapper()));
	}
}

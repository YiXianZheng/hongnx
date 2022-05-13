package com.hongnx.cloud.mall.admin.api.ma;

import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.GoodsSpuSpecService;
import com.hongnx.cloud.mall.common.entity.GoodsSpuSpec;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * spu规格
 *
 * @date 2019-08-13 16:56:46
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/goodsspuspec")
@Api(value = "goodsspuspec", tags = "spu规格接口API")
public class GoodsSpuSpecApi {

    private final GoodsSpuSpecService goodsSpuSpecService;


	/**
	 * 获取商品规格
	 * @param goodsSpuSpec
	 * @return
	 */
	@ApiOperation(value = "获取商品规格")
	@GetMapping("/tree")
	public R getGoodsSpuSpecTree(GoodsSpuSpec goodsSpuSpec) {
		return R.ok(goodsSpuSpecService.tree(goodsSpuSpec));
	}

}

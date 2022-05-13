package com.hongnx.cloud.mall.admin.api.ma;

import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.GoodsCategoryService;
import com.hongnx.cloud.mall.common.entity.GoodsCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 商品类目
 *
 * @date 2019-08-12 11:46:28
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/goodscategory")
@Api(value = "goodscategory", tags = "商品类目API")
public class GoodsCategoryApi {

    private final GoodsCategoryService goodsCategoryService;

    /**
    * 返回树形集合
    */
	@ApiOperation(value = "返回树形集合")
    @GetMapping("/tree")
    public R goodsCategoryTree(GoodsCategory goodsCategory) {
		goodsCategory.setEnable(CommonConstants.YES);
		return R.ok(goodsCategoryService.selectTree(goodsCategory));
    }
}

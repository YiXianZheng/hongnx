package com.hongnx.cloud.mall.admin.api.ma;

import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.EnsureGoodsService;
import com.hongnx.cloud.mall.common.entity.Ensure;
import com.hongnx.cloud.mall.common.entity.EnsureGoods;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品保障
 *
 * @date 2020-02-10 00:02:09
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/ensuregoods")
@Api(value = "ensuregoods", tags = "商品保障API")
public class EnsureGoodsApi {

    private final EnsureGoodsService ensureGoodsService;

    /**
     * 通过spuID，查询商品保障
     * @param ensureGoods 商品保障
     * @return
     */
	@ApiOperation(value = "通过spuID，查询商品保障")
    @GetMapping("/listEnsureBySpuId")
    public R listEnsureBySpuId(EnsureGoods ensureGoods) {
		List<Ensure> listEnsure = ensureGoodsService.listEnsureBySpuId(ensureGoods.getSpuId());
        return R.ok(listEnsure);
    }
}

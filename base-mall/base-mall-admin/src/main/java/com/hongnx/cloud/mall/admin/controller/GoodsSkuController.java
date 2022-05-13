package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.GoodsSkuService;
import com.hongnx.cloud.mall.admin.service.GoodsSpuService;
import com.hongnx.cloud.mall.common.constant.MallConstants;
import com.hongnx.cloud.mall.common.entity.GoodsSku;
import com.hongnx.cloud.mall.common.entity.GoodsSpu;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 商品sku
 *
 * @date 2019-08-13 10:18:34
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/goodssku")
@Api(value = "goodssku", tags = "商品sku管理")
public class GoodsSkuController {

    private final GoodsSkuService goodsSkuService;
	private final GoodsSpuService goodsSpuService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param goodsSku 商品sku
    * @return
    */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:goodssku:index')")
    public R getGoodsSkuPage(Page page, GoodsSku goodsSku) {
        return R.ok(goodsSkuService.page(page,Wrappers.query(goodsSku)));
    }

	/**
	 * list查询
	 * @param spuId
	 * @return
	 */
	@ApiOperation(value = "list查询")
	@GetMapping("/list/{spuId}")
	@PreAuthorize("@ato.hasAuthority('mall:goodsspu:index')")
	public List<GoodsSku> getList(@PathVariable("spuId") String spuId) {
		GoodsSpu goodsSpu = goodsSpuService.getById(spuId);
		List<GoodsSku> listRs = goodsSkuService.listGoodsSkuBySpuId(spuId).stream()
				.map(goodsSku -> {
					if(MallConstants.SPU_SPEC_TYPE_0.equals(goodsSpu.getSpecType())){
						goodsSku.setName("统一规格");
					}else{
						AtomicReference<String> name = new AtomicReference<>("");
						goodsSku.getSpecs().forEach(goodsSkuSpecValue -> {
							name.set(name +goodsSkuSpecValue.getSpecValueName() + "|");
						});
						String nameStr = name.get();
						goodsSku.setName(nameStr.substring(0,nameStr.length()-1));
					}
					goodsSku.setName(goodsSku.getName() + "  销售价：¥" + goodsSku.getSalesPrice());
					return goodsSku;
				}).collect(Collectors.toList());
		return listRs;
	}

    /**
    * 通过id查询商品sku
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询商品sku")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:goodssku:get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(goodsSkuService.getById(id));
    }

    /**
    * 新增商品sku
    * @param goodsSku 商品sku
    * @return R
    */
	@ApiOperation(value = "新增商品sku")
    @SysLog("新增商品sku")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:goodssku:add')")
    public R save(@RequestBody GoodsSku goodsSku){
        return R.ok(goodsSkuService.save(goodsSku));
    }

    /**
    * 修改商品sku
    * @param goodsSku 商品sku
    * @return R
    */
	@ApiOperation(value = "修改商品sku")
    @SysLog("修改商品sku")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:goodssku:edit')")
    public R updateById(@RequestBody GoodsSku goodsSku){
		if(!goodsSkuService.updateById(goodsSku)){
			return R.failed("请重新提交");
		}
        return R.ok(Boolean.TRUE);
    }

    /**
    * 通过id删除商品sku
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id删除商品sku")
    @SysLog("删除商品sku")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:goodssku:del')")
    public R removeById(@PathVariable String id){
        return R.ok(goodsSkuService.removeById(id));
    }

}

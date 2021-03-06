package com.hongnx.cloud.mall.admin.api.ma;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.mapper.UserCollectMapper;
import com.hongnx.cloud.mall.admin.service.CouponUserService;
import com.hongnx.cloud.mall.admin.service.GoodsSpuService;
import com.hongnx.cloud.mall.admin.service.UserFootprintService;
import com.hongnx.cloud.mall.common.constant.MallConstants;
import com.hongnx.cloud.mall.common.constant.MyReturnCode;
import com.hongnx.cloud.mall.common.entity.CouponUser;
import com.hongnx.cloud.mall.common.entity.GoodsSpu;
import com.hongnx.cloud.mall.common.entity.UserCollect;
import com.hongnx.cloud.mall.common.entity.UserFootprint;
import com.hongnx.cloud.weixin.common.util.ThirdSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品api
 *
 * @date 2019-08-12 16:25:10
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/goodsspu")
@Api(value = "goodsspu", tags = "商品接口API")
public class GoodsSpuApi {

    private final GoodsSpuService goodsSpuService;
	private final UserCollectMapper userCollectMapper;
	private final CouponUserService couponUserService;
	private final UserFootprintService userFootprintService;

	/**
	* 分页查询
	* @param page 分页对象
	* @param goodsSpu spu商品
	* @return
	*/
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public R getGoodsSpuPage(Page page, GoodsSpu goodsSpu, String couponUserId) {
		goodsSpu.setShelf(CommonConstants.YES);
		CouponUser couponUser = null;
		if(StrUtil.isNotBlank(couponUserId)){
			couponUser = couponUserService.getById(couponUserId);
		}
        return R.ok(goodsSpuService.page2(page, goodsSpu, couponUser));
    }

    /**
    * 通过id查询spu商品
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询spu商品")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") String id){
		UserCollect userCollect = new UserCollect();
		userCollect.setUserId(ThirdSessionHolder.getMallUserId());
		GoodsSpu goodsSpu = goodsSpuService.getById2(id);
		if(goodsSpu == null){
			return R.failed(MyReturnCode.ERR_80004.getCode(), MyReturnCode.ERR_80004.getMsg());
		}
		//查询用户是否收藏
		userCollect.setType(MallConstants.COLLECT_TYPE_1);
		userCollect.setRelationId(id);
		goodsSpu.setCollectId(userCollectMapper.selectCollectId(userCollect));
		//加入足迹
		UserFootprint userFootprint = new UserFootprint();
		userFootprint.setRelationId(id);
		userFootprint.setUserId(ThirdSessionHolder.getMallUserId());
		userFootprintService.save(userFootprint);
        return R.ok(goodsSpu);
    }

}

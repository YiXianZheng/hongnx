package com.hongnx.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.CouponInfoService;
import com.hongnx.cloud.mall.common.entity.CouponGoods;
import com.hongnx.cloud.mall.common.entity.CouponInfo;
import com.hongnx.cloud.mall.common.entity.CouponUser;
import com.hongnx.cloud.weixin.common.util.ThirdSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 电子券
 *
 * @date 2019-12-14 11:30:58
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/couponinfo")
@Api(value = "couponinfo", tags = "电子券API")
public class CouponInfoApi {

    private final CouponInfoService couponInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param couponInfo 电子券
     * @return
     */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public R getPage(Page page, CouponInfo couponInfo, CouponGoods cuponGoods) {
		CouponUser couponUser = new CouponUser();
		couponUser.setUserId(ThirdSessionHolder.getMallUserId());
        return R.ok(couponInfoService.page2(page, couponInfo, cuponGoods, couponUser));
    }

    /**
     * 通过id查询电子券
     * @param id
     * @return R
     */
	@ApiOperation(value = "通过id查询电子券")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") String id) {
        return R.ok(couponInfoService.getById2(id));
    }

}

package com.hongnx.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.CouponInfoService;
import com.hongnx.cloud.mall.admin.service.CouponUserService;
import com.hongnx.cloud.mall.common.constant.MallConstants;
import com.hongnx.cloud.mall.common.constant.MyReturnCode;
import com.hongnx.cloud.mall.common.entity.CouponInfo;
import com.hongnx.cloud.mall.common.entity.CouponUser;
import com.hongnx.cloud.weixin.common.util.ThirdSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 电子券用户记录
 *
 * @date 2019-12-17 10:43:53
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/couponuser")
@Api(value = "couponuser", tags = "电子券用户记录API")
public class CouponUserApi {

    private final CouponUserService couponUserService;
	private final CouponInfoService couponInfoService;
    /**
     * 分页列表
     * @param page 分页对象
     * @param couponUser 电子券用户记录
     * @return
     */
	@ApiOperation(value = "分页列表")
    @GetMapping("/page")
    public R getCouponUserPage(Page page, CouponUser couponUser) {
		couponUser.setUserId(ThirdSessionHolder.getMallUserId());
		IPage<CouponUser> iPage = couponUserService.page2(page, couponUser);
		//去重
		List<CouponUser> list = iPage.getRecords().stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(CouponUser :: getId))), ArrayList::new));;
		iPage.setRecords(list);
        return R.ok(iPage);
    }

    /**
     * 电子券用户记录查询
     * @param id
     * @return R
     */
	@ApiOperation(value = "电子券用户记录查询")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") String id) {
		return R.ok(couponUserService.getById(id));
    }

    /**
     * 电子券用户记录新增
     * @param couponUser 电子券用户记录
     * @return R
     */
	@ApiOperation(value = "电子券用户记录新增")
    @PostMapping
    public R save(@RequestBody CouponUser couponUser) {
		couponUser.setUserId(ThirdSessionHolder.getMallUserId());
		//核验用户是否领取过该券，并未使用
		int couponNum = couponUserService.count(Wrappers.<CouponUser>lambdaQuery()
				.eq(CouponUser :: getUserId, couponUser.getUserId())
				.eq(CouponUser :: getCouponId, couponUser.getCouponId())
				.eq(CouponUser :: getStatus, MallConstants.COUPON_USER_STATUS_0)
				.gt(CouponUser :: getValidEndTime, LocalDateTime.now()));
		if(couponNum > 0){
			return R.failed(MyReturnCode.ERR_80001.getCode(), MyReturnCode.ERR_80001.getMsg());
		}
		CouponInfo cuponInfo = couponInfoService.getById(couponUser.getCouponId());
		if(cuponInfo == null){
			return R.failed(MyReturnCode.ERR_80002.getCode(), MyReturnCode.ERR_80002.getMsg());
		}
		//核验库存
		if(cuponInfo.getStock() <= 0){
			return R.failed(MyReturnCode.ERR_80003.getCode(), MyReturnCode.ERR_80003.getMsg());
		}
		couponUser.setStatus(CommonConstants.NO);
		//计数有效期
		if(MallConstants.COUPON_EXPIRE_TYPE_1.equals(cuponInfo.getExpireType())){
			couponUser.setValidBeginTime(LocalDateTime.now());
			couponUser.setValidEndTime(LocalDateTime.now().plusDays(cuponInfo.getValidDays()));
		}
		if(MallConstants.COUPON_EXPIRE_TYPE_2.equals(cuponInfo.getExpireType())){
			couponUser.setValidBeginTime(cuponInfo.getValidBeginTime());
			couponUser.setValidEndTime(cuponInfo.getValidEndTime());
		}
		couponUserService.receiveCoupon(couponUser, cuponInfo);
        return R.ok(couponUser);
    }

}

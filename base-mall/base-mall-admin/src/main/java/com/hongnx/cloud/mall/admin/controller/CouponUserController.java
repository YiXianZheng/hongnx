package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.CouponUserService;
import com.hongnx.cloud.mall.common.entity.CouponUser;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 电子券用户记录
 *
 * @date 2019-12-17 10:43:53
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/couponuser")
@Api(value = "couponuser", tags = "电子券用户记录管理")
public class CouponUserController {

    private final CouponUserService couponUserService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param couponUser 电子券用户记录
     * @return
     */
	@ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:couponuser:index')")
    public R getCouponUserPage(Page page, CouponUser couponUser) {
        return R.ok(couponUserService.page1(page, couponUser));
    }

    /**
     * 电子券用户记录查询
     * @param id
     * @return R
     */
	@ApiOperation(value = "电子券用户记录查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:couponuser:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(couponUserService.getById(id));
    }

    /**
     * 电子券用户记录新增
     * @param couponUser 电子券用户记录
     * @return R
     */
	@ApiOperation(value = "电子券用户记录新增")
    @SysLog("新增电子券用户记录")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:couponuser:add')")
    public R save(@RequestBody CouponUser couponUser) {
        return R.ok(couponUserService.save(couponUser));
    }

    /**
     * 电子券用户记录修改
     * @param couponUser 电子券用户记录
     * @return R
     */
	@ApiOperation(value = "电子券用户记录修改")
    @SysLog("修改电子券用户记录")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:couponuser:edit')")
    public R updateById(@RequestBody CouponUser couponUser) {
        return R.ok(couponUserService.updateById(couponUser));
    }

    /**
     * 电子券用户记录删除
     * @param id
     * @return R
     */
	@ApiOperation(value = "电子券用户记录删除")
    @SysLog("删除电子券用户记录")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:couponuser:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(couponUserService.removeById(id));
    }

}

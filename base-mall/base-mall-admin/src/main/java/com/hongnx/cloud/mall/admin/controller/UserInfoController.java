package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.common.security.annotation.Inside;
import com.hongnx.cloud.mall.admin.service.UserInfoService;
import com.hongnx.cloud.mall.common.entity.UserInfo;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 商城用户
 *
 * @date 2019-12-04 11:09:55
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/userinfo")
@Api(value = "userinfo", tags = "商城用户管理")
public class UserInfoController {

    private final UserInfoService userInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param userInfo 商城用户
     * @return
     */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:userinfo:index')")
    public R getUserinfoPage(Page page, UserInfo userInfo) {
        return R.ok(userInfoService.page(page, Wrappers.query(userInfo)));
    }

	/**
	 * 查询数量
	 * @param userInfo
	 * @return
	 */
	@ApiOperation(value = "查询数量")
	@GetMapping("/count")
	public R getCount(UserInfo userInfo) {
		return R.ok(userInfoService.count(Wrappers.query(userInfo)));
	}

    /**
     * 通过id查询商城用户
     * @param id
     * @return R
     */
	@ApiOperation(value = "通过id查询商城用户")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:userinfo:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(userInfoService.getById(id));
    }

    /**
     * 新增商城用户
     * @param userInfo 商城用户
     * @return R
     */
	@ApiOperation(value = "新增商城用户")
    @SysLog("新增商城用户")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:userinfo:add')")
    public R save(@RequestBody UserInfo userInfo) {
        return R.ok(userInfoService.save(userInfo));
    }

    /**
     * 修改商城用户
     * @param userInfo 商城用户
     * @return R
     */
	@ApiOperation(value = "修改商城用户")
    @SysLog("修改商城用户")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:userinfo:edit')")
    public R updateById(@RequestBody UserInfo userInfo) {
        return R.ok(userInfoService.updateById(userInfo));
    }

    /**
     * 通过id删除商城用户
     * @param id
     * @return R
     */
	@ApiOperation(value = "通过id删除商城用户")
    @SysLog("删除商城用户")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:userinfo:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(userInfoService.removeById(id));
    }

	/**
	 * 新增商城用户（供服务间调用）
	 * @param userInfo 商城用户
	 * @return R
	 */
	@ApiOperation(value = "新增商城用户")
	@Inside
	@PostMapping("/inside")
	public R saveInside(@RequestBody UserInfo userInfo) {
		userInfoService.saveInside(userInfo);
		return R.ok(userInfo);
	}
}

package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.BargainInfoService;
import com.hongnx.cloud.mall.admin.service.BargainUserService;
import com.hongnx.cloud.mall.common.constant.MyReturnCode;
import com.hongnx.cloud.mall.common.entity.BargainInfo;
import com.hongnx.cloud.mall.common.entity.BargainUser;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 砍价
 *
 * @date 2019-12-28 18:07:51
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/bargaininfo")
@Api(value = "bargaininfo", tags = "砍价管理")
public class BargainInfoController {

    private final BargainInfoService bargainInfoService;
	private final BargainUserService bargainUserService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param bargainInfo 砍价
     * @return
     */
	@ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:bargaininfo:index')")
    public R getPage(Page page, BargainInfo bargainInfo) {
        return R.ok(bargainInfoService.page(page, Wrappers.query(bargainInfo)));
    }

	/**
	 * list列表
	 * @param bargainInfo 砍价
	 * @return
	 */
	@ApiOperation(value = "list列表")
	@GetMapping("/list")
	public R getList(BargainInfo bargainInfo) {
		return R.ok(bargainInfoService.list(Wrappers.query(bargainInfo)));
	}

    /**
     * 砍价查询
     * @param id
     * @return R
     */
	@ApiOperation(value = "砍价查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:bargaininfo:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(bargainInfoService.getById(id));
    }

    /**
     * 砍价新增
     * @param bargainInfo 砍价
     * @return R
     */
	@ApiOperation(value = "砍价新增")
    @SysLog("新增砍价")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:bargaininfo:add')")
    public R save(@RequestBody BargainInfo bargainInfo) {
        return R.ok(bargainInfoService.save(bargainInfo));
    }

    /**
     * 砍价修改
     * @param bargainInfo 砍价
     * @return R
     */
	@ApiOperation(value = "砍价修改")
    @SysLog("修改砍价")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:bargaininfo:edit')")
    public R updateById(@RequestBody BargainInfo bargainInfo) {
        return R.ok(bargainInfoService.updateById(bargainInfo));
    }

    /**
     * 砍价删除
     * @param id
     * @return R
     */
	@ApiOperation(value = "砍价删除")
    @SysLog("删除砍价")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:bargaininfo:del')")
    public R removeById(@PathVariable String id) {
		int count = bargainUserService.count(Wrappers.<BargainUser>lambdaQuery()
				.eq(BargainUser::getBargainId,id));
		if(count > 0){
			return R.failed(MyReturnCode.ERR_80020.getMsg());
		}
        return R.ok(bargainInfoService.removeById(id));
    }

}

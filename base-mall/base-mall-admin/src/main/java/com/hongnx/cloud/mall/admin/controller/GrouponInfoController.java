package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.GrouponInfoService;
import com.hongnx.cloud.mall.admin.service.GrouponUserService;
import com.hongnx.cloud.mall.common.constant.MyReturnCode;
import com.hongnx.cloud.mall.common.entity.GrouponInfo;
import com.hongnx.cloud.mall.common.entity.GrouponUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 拼团
 *
 * @date 2020-03-17 11:55:32
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/grouponinfo")
@Api(value = "grouponinfo", tags = "拼团管理")
public class GrouponInfoController {

    private final GrouponInfoService grouponInfoService;
	private final GrouponUserService grouponUserService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param grouponInfo 拼团
     * @return
     */
    @ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:grouponinfo:index')")
    public R getPage(Page page, GrouponInfo grouponInfo) {
        return R.ok(grouponInfoService.page(page, Wrappers.query(grouponInfo)));
    }

	/**
	 * list列表
	 * @param grouponInfo 拼团
	 * @return
	 */
	@ApiOperation(value = "list列表")
	@GetMapping("/list")
	public R getList(GrouponInfo grouponInfo) {
		return R.ok(grouponInfoService.list(Wrappers.query(grouponInfo)));
	}


	/**
     * 拼团查询
     * @param id
     * @return R
     */
    @ApiOperation(value = "拼团查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:grouponinfo:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(grouponInfoService.getById(id));
    }

    /**
     * 拼团新增
     * @param grouponInfo 拼团
     * @return R
     */
    @ApiOperation(value = "拼团新增")
    @SysLog("新增拼团")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:grouponinfo:add')")
    public R save(@RequestBody GrouponInfo grouponInfo) {
        return R.ok(grouponInfoService.save(grouponInfo));
    }

    /**
     * 拼团修改
     * @param grouponInfo 拼团
     * @return R
     */
    @ApiOperation(value = "拼团修改")
    @SysLog("修改拼团")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:grouponinfo:edit')")
    public R updateById(@RequestBody GrouponInfo grouponInfo) {
        return R.ok(grouponInfoService.updateById(grouponInfo));
    }

    /**
     * 拼团删除
     * @param id
     * @return R
     */
    @ApiOperation(value = "拼团删除")
    @SysLog("删除拼团")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:grouponinfo:del')")
    public R removeById(@PathVariable String id) {
		int count = grouponUserService.count(Wrappers.<GrouponUser>lambdaQuery()
				.eq(GrouponUser::getGrouponId,id));
		if(count > 0){
			return R.failed(MyReturnCode.ERR_80020.getMsg());
		}
        return R.ok(grouponInfoService.removeById(id));
    }

}

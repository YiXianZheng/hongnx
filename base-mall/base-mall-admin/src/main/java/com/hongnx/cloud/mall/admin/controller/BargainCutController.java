package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.BargainCutService;
import com.hongnx.cloud.mall.common.entity.BargainCut;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 砍价帮砍记录
 *
 * @date 2019-12-31 12:34:28
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/bargaincut")
@Api(value = "bargaincut", tags = "砍价帮砍记录管理")
public class BargainCutController {

    private final BargainCutService bargainCutService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param bargainCut 砍价帮砍记录
     * @return
     */
	@ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:bargainuser:index')")
    public R getPage(Page page, BargainCut bargainCut) {
        return R.ok(bargainCutService.page(page, Wrappers.query(bargainCut)));
    }

    /**
     * 砍价帮砍记录查询
     * @param id
     * @return R
     */
	@ApiOperation(value = "砍价帮砍记录查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:bargaincut:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(bargainCutService.getById(id));
    }

    /**
     * 砍价帮砍记录新增
     * @param bargainCut 砍价帮砍记录
     * @return R
     */
	@ApiOperation(value = "砍价帮砍记录新增")
    @SysLog("新增砍价帮砍记录")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:bargaincut:add')")
    public R save(@RequestBody BargainCut bargainCut) {
        return R.ok(bargainCutService.save(bargainCut));
    }

    /**
     * 砍价帮砍记录修改
     * @param bargainCut 砍价帮砍记录
     * @return R
     */
	@ApiOperation(value = "砍价帮砍记录修改")
    @SysLog("修改砍价帮砍记录")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:bargaincut:edit')")
    public R updateById(@RequestBody BargainCut bargainCut) {
        return R.ok(bargainCutService.updateById(bargainCut));
    }

    /**
     * 砍价帮砍记录删除
     * @param id
     * @return R
     */
	@ApiOperation(value = "砍价帮砍记录删除")
    @SysLog("删除砍价帮砍记录")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:bargaincut:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(bargainCutService.removeById(id));
    }

}

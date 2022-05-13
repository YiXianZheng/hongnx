package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.ArticleInfoService;
import com.hongnx.cloud.mall.common.entity.ArticleInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 文章
 *
 * @date 2020-11-24 14:44:15
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/articleinfo")
@Api(value = "articleinfo", tags = "文章管理")
public class ArticleInfoController {

    private final ArticleInfoService articleInfoService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param articleInfo 文章
     * @return
     */
    @ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:articleinfo:index')")
    public R getPage(Page page, ArticleInfo articleInfo) {
        return R.ok(articleInfoService.page(page, Wrappers.query(articleInfo)));
    }

    /**
     * 文章查询
     * @param id
     * @return R
     */
    @ApiOperation(value = "文章查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:articleinfo:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(articleInfoService.getById(id));
    }

    /**
     * 文章新增
     * @param articleInfo 文章
     * @return R
     */
    @ApiOperation(value = "文章新增")
    @SysLog("新增文章")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:articleinfo:add')")
    public R save(@RequestBody ArticleInfo articleInfo) {
        return R.ok(articleInfoService.save(articleInfo));
    }

    /**
     * 文章修改
     * @param articleInfo 文章
     * @return R
     */
    @ApiOperation(value = "文章修改")
    @SysLog("修改文章")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:articleinfo:edit')")
    public R updateById(@RequestBody ArticleInfo articleInfo) {
        return R.ok(articleInfoService.updateById(articleInfo));
    }

    /**
     * 文章删除
     * @param id
     * @return R
     */
    @ApiOperation(value = "文章删除")
    @SysLog("删除文章")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:articleinfo:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(articleInfoService.removeById(id));
    }

}

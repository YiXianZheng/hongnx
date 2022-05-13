package com.hongnx.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.log.annotation.SysLog;
import com.hongnx.cloud.mall.admin.service.ArticleCategoryService;
import com.hongnx.cloud.mall.common.entity.ArticleCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 文章分类
 *
 * @date 2020-11-24 14:24:30
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/articlecategory")
@Api(value = "articlecategory", tags = "文章分类管理")
public class ArticleCategoryController {

    private final ArticleCategoryService articleCategoryService;

	/**
	 * list查询
	 * @param articleCategory 文章分类
	 * @return
	 */
	@ApiOperation(value = "list查询")
	@GetMapping("/list")
	public R getUserinfoList(ArticleCategory articleCategory) {
		return R.ok(articleCategoryService.list(Wrappers.query(articleCategory).lambda()
				.select(ArticleCategory::getId,
						ArticleCategory::getName)));
	}

    /**
     * 分页列表
     * @param page 分页对象
     * @param articleCategory 文章分类
     * @return
     */
    @ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall:articlecategory:index')")
    public R getPage(Page page, ArticleCategory articleCategory) {
        return R.ok(articleCategoryService.page(page, Wrappers.query(articleCategory)));
    }

    /**
     * 文章分类查询
     * @param id
     * @return R
     */
    @ApiOperation(value = "文章分类查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:articlecategory:get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(articleCategoryService.getById(id));
    }

    /**
     * 文章分类新增
     * @param articleCategory 文章分类
     * @return R
     */
    @ApiOperation(value = "文章分类新增")
    @SysLog("新增文章分类")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall:articlecategory:add')")
    public R save(@RequestBody ArticleCategory articleCategory) {
        return R.ok(articleCategoryService.save(articleCategory));
    }

    /**
     * 文章分类修改
     * @param articleCategory 文章分类
     * @return R
     */
    @ApiOperation(value = "文章分类修改")
    @SysLog("修改文章分类")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall:articlecategory:edit')")
    public R updateById(@RequestBody ArticleCategory articleCategory) {
        return R.ok(articleCategoryService.updateById(articleCategory));
    }

    /**
     * 文章分类删除
     * @param id
     * @return R
     */
    @ApiOperation(value = "文章分类删除")
    @SysLog("删除文章分类")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall:articlecategory:del')")
    public R removeById(@PathVariable String id) {
        return R.ok(articleCategoryService.removeById(id));
    }

}

package com.hongnx.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.ArticleCategoryService;
import com.hongnx.cloud.mall.common.entity.ArticleCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章分类
 *
 * @date 2020-11-24 16:25:35
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/articlecategory")
@Api(value = "articlecategory", tags = "文章分类Api")
public class ArticleCategoryApi {

    private final ArticleCategoryService articleCategoryService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param articleCategory 文章分类
     * @return
     */
    @ApiOperation(value = "分页列表")
    @GetMapping("/page")
    public R getPage(Page page, ArticleCategory articleCategory) {
		articleCategory.setEnable(CommonConstants.YES);
        return R.ok(articleCategoryService.page(page, Wrappers.query(articleCategory)));
    }

    /**
     * 文章分类查询
     * @param id
     * @return R
     */
    @ApiOperation(value = "文章分类查询")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") String id) {
        return R.ok(articleCategoryService.getById(id));
    }

}

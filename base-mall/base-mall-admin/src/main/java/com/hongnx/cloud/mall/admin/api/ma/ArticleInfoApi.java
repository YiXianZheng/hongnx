package com.hongnx.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.ArticleInfoService;
import com.hongnx.cloud.mall.common.entity.ArticleInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章
 *
 * @date 2020-11-24 16:29:09
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/articleinfo")
@Api(value = "articleinfo", tags = "文章Api")
public class ArticleInfoApi {

    private final ArticleInfoService articleInfoService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param articleInfo 文章
     * @return
     */
    @ApiOperation(value = "分页列表")
    @GetMapping("/page")
    public R getPage(Page page, ArticleInfo articleInfo) {
		articleInfo.setEnable(CommonConstants.YES);
        return R.ok(articleInfoService.page(page, Wrappers.query(articleInfo)));
    }

    /**
     * 文章查询
     * @param id
     * @return R
     */
    @ApiOperation(value = "文章查询")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") String id) {
        return R.ok(articleInfoService.getById(id));
    }

}

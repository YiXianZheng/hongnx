package com.hongnx.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.BargainCutService;
import com.hongnx.cloud.mall.common.entity.BargainCut;
import com.hongnx.cloud.weixin.common.util.ThirdSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 砍价帮砍记录
 *
 * @date 2019-12-31 12:34:28
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/bargaincut")
@Api(value = "bargaincut", tags = "砍价帮砍记录API")
public class BargainCutApi {

    private final BargainCutService bargainCutService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param bargainCut 砍价帮砍记录
     * @return
     */
	@ApiOperation(value = "分页列表")
    @GetMapping("/page")
    public R getPage(Page page, BargainCut bargainCut) {
        return R.ok(bargainCutService.page(page, Wrappers.query(bargainCut)));
    }

    /**
     * 砍价帮砍记录新增
     * @param bargainCut 砍价帮砍记录
     * @return R
     */
	@ApiOperation(value = "砍价帮砍记录新增")
    @PostMapping
    public R save(@RequestBody BargainCut bargainCut) {
		bargainCut.setUserId(ThirdSessionHolder.getMallUserId());
		bargainCutService.save2(bargainCut);
        return R.ok(bargainCut);
    }
}

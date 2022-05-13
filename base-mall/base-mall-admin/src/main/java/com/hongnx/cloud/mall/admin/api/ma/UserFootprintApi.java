package com.hongnx.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.UserFootprintService;
import com.hongnx.cloud.mall.common.entity.UserFootprint;
import com.hongnx.cloud.weixin.common.util.ThirdSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户足迹
 *
 * @date 2020-12-24 22:09:03
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/userfootprint")
@Api(value = "userfootprint", tags = "用户足迹api")
public class UserFootprintApi {

    private final UserFootprintService userFootprintService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param userFootprint 用户足迹
     * @return
     */
    @ApiOperation(value = "分页列表")
    @GetMapping("/page")
    public R getPage(Page page, UserFootprint userFootprint) {
		userFootprint.setUserId(ThirdSessionHolder.getMallUserId());
        return R.ok(userFootprintService.page2(page, userFootprint));
    }

}

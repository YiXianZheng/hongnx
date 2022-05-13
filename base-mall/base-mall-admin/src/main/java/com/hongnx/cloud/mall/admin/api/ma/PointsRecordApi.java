package com.hongnx.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.PointsRecordService;
import com.hongnx.cloud.mall.common.entity.PointsRecord;
import com.hongnx.cloud.weixin.common.util.ThirdSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 积分变动记录
 *
 * @date 2019-12-05 19:47:22
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/pointsrecord")
@Api(value = "pointsrecord", tags = "积分变动记录API")
public class PointsRecordApi {

    private final PointsRecordService pointsRecordService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param pointsRecord 积分变动记录
     * @return
     */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public R getPointsRecordPage(Page page, PointsRecord pointsRecord) {
		pointsRecord.setUserId(ThirdSessionHolder.getMallUserId());
        return R.ok(pointsRecordService.page(page, Wrappers.query(pointsRecord)));
    }

}

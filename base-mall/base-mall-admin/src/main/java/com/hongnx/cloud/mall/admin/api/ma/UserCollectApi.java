package com.hongnx.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.UserCollectService;
import com.hongnx.cloud.mall.common.dto.UserCollectAddDTO;
import com.hongnx.cloud.mall.common.entity.UserCollect;
import com.hongnx.cloud.weixin.common.util.ThirdSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户收藏
 *
 * @date 2019-09-22 20:45:45
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/usercollect")
@Api(value = "usercollect", tags = "用户收藏API")
public class UserCollectApi {

    private final UserCollectService userCollectService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param userCollect 用户收藏
    * @return
    */
	@ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public R getUserCollectPage(Page page, UserCollect userCollect) {
		userCollect.setUserId(ThirdSessionHolder.getMallUserId());
        return R.ok(userCollectService.page2(page,Wrappers.query(userCollect)));
    }

    /**
    * 新增用户收藏
    * @param userCollectAddDTO 用户收藏
    * @return R
    */
	@ApiOperation(value = "新增用户收藏")
    @PostMapping
    public R save(@RequestBody UserCollectAddDTO userCollectAddDTO){
		UserCollect userCollect = new UserCollect();
		userCollect.setUserId(ThirdSessionHolder.getMallUserId());
		List<UserCollect> listUserCollect = new ArrayList<>();
		List<String> relationIds = userCollectAddDTO.getRelationIds()
				.stream()
				.distinct()
				.collect(Collectors.toList());
		relationIds.forEach(relationId ->{
			UserCollect userCollect2 = new UserCollect();
			userCollect2.setUserId(userCollect.getUserId());
			userCollect2.setType(userCollectAddDTO.getType());
			userCollect2.setRelationId(relationId);
			List list = userCollectService.list(Wrappers.query(userCollect2));
			if(list == null || list.size() <= 0){
				listUserCollect.add(userCollect2);
			}
		});
		userCollectService.saveBatch(listUserCollect);
        return R.ok(listUserCollect);
    }

    /**
    * 通过id删除用户收藏
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id删除用户收藏")
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable String id){
		return R.ok(userCollectService.removeById(id));
    }

}

package com.hongnx.cloud.mall.admin.api.ma;

import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.mall.admin.service.OrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商城订单详情
 *
 * @date 2019-09-10 15:31:40
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/orderitem")
@Api(value = "orderitem", tags = "商城订单详情API")
public class OrderItemApi {

    private final OrderItemService orderItemService;

    /**
    * 通过id查询商城订单详情
    * @param id
    * @return R
    */
	@ApiOperation(value = "通过id查询商城订单详情")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") String id){
        return R.ok(orderItemService.getById2(id));
    }

}

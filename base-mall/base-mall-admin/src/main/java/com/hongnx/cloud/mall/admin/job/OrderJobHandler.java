
package com.hongnx.cloud.mall.admin.job;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.common.data.tenant.TenantContextHolder;
import com.hongnx.cloud.mall.admin.service.OrderInfoService;
import com.hongnx.cloud.mall.common.constant.MallConstants;
import com.hongnx.cloud.mall.common.entity.OrderInfo;
import com.hongnx.cloud.upms.common.entity.SysTenant;
import com.hongnx.cloud.upms.common.feign.FeignTenantService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

import static com.xxl.job.core.biz.model.ReturnT.SUCCESS;

/**
 * 订单相关定时任务
 * @date 2020-09-18
 */
@Slf4j
@Component
@AllArgsConstructor
public class OrderJobHandler {

	private final OrderInfoService orderInfoService;
	private final FeignTenantService feignTenantService;
	/**
	 * 定时清理未正常自动取消的订单
	 * @param s
	 * @return
	 */
	@XxlJob("orderCancelJobHandler")
	public ReturnT<String> orderCancelJob(String s) {
		//查出所有租户
		List<SysTenant> listSysTenant = feignTenantService.list(SecurityConstants.FROM_IN).getData();
		listSysTenant.forEach(item -> {
			TenantContextHolder.setTenantId(item.getId());
			//查询出各租户下未正常自动取消的订单
			List<OrderInfo> listOrderInfo = orderInfoService.list(Wrappers.<OrderInfo>query().lambda()
					.isNull(OrderInfo::getStatus)
					.lt(OrderInfo::getCreateTime, LocalDateTime.now().minusMinutes(MallConstants.ORDER_TIME_OUT_0)));
			if(listOrderInfo != null && listOrderInfo.size() > 0){
				listOrderInfo.forEach(orderInfo -> {
					orderInfoService.orderCancel(orderInfo);
				});
			}
			TenantContextHolder.clear();
		});
		return SUCCESS;
	}

}
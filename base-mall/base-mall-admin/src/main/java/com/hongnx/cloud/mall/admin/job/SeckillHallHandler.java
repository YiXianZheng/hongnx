
package com.hongnx.cloud.mall.admin.job;

import com.hongnx.cloud.common.data.tenant.TenantContextHolder;
import com.hongnx.cloud.mall.admin.service.SeckillHallService;
import com.hongnx.cloud.mall.common.entity.SeckillHall;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static com.xxl.job.core.biz.model.ReturnT.SUCCESS;

/**
 * 秒杀会场相关定时任务
 * @date 2020-09-18
 */
@Slf4j
@Component
@AllArgsConstructor
public class SeckillHallHandler {

	private final SeckillHallService seckillHallService;

	/**
     * 定时更新指定会场的会场日期
	 * @param
     * @return
     */
	@XxlJob("seckillHallHallDateJobHandler")
	public ReturnT<String> seckillHallHallDateJobHandler(String s) {
		TenantContextHolder.setTenantId("1");
		String[] seckillHallIds = new String[]{"1309369447020277762","1309370014757072898","1309370340788711426","1309370430819446785","1309370863642259458","1309370926384852993","1307310509386936322"};
		SeckillHall seckillHall;
		LocalDateTime time=LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String strDate = dtf.format(time);
		for(int i = 0; i<seckillHallIds.length; i++){
			seckillHall = new SeckillHall();
			seckillHall.setId(seckillHallIds[i]);
			seckillHall.setHallDate(strDate);
			seckillHallService.updateById(seckillHall);
		}
		TenantContextHolder.clear();
		return SUCCESS;
	}

}
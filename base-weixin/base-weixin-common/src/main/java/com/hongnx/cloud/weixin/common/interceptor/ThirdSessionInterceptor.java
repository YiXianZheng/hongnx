package com.hongnx.cloud.weixin.common.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.common.data.tenant.TenantContextHolder;
import com.hongnx.cloud.weixin.common.constant.ConfigConstant;
import com.hongnx.cloud.weixin.common.constant.MyReturnCode;
import com.hongnx.cloud.weixin.common.constant.WxMaConstants;
import com.hongnx.cloud.weixin.common.entity.ThirdSession;
import com.hongnx.cloud.weixin.common.util.ThirdSessionHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ThirdSession拦截器，校验每个请求的ThirdSession
 * @author
 */
@Slf4j
@Component
@AllArgsConstructor
public class ThirdSessionInterceptor extends HandlerInterceptorAdapter {

	private final RedisTemplate redisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//获取header中的thirdSession
		String thirdSessionHeader = request.getHeader(ConfigConstant.HEADER_THIRDSESSION);
		if(StrUtil.isNotBlank(thirdSessionHeader)){
			//获取缓存中的ThirdSession
			String key = WxMaConstants.THIRD_SESSION_BEGIN  + ":" + thirdSessionHeader;
			Object thirdSessionObj = redisTemplate.opsForValue().get(key);
			if(thirdSessionObj == null) {//session过期
				R r = R.failed(MyReturnCode.ERR_60001.getCode(), MyReturnCode.ERR_60001.getMsg());
				this.writerPrint(response, r);
				return Boolean.FALSE;
			}else {
				String thirdSessionStr = String.valueOf(thirdSessionObj);
				ThirdSession thirdSession = JSONUtil.toBean(thirdSessionStr, ThirdSession.class);
				TenantContextHolder.setTenantId(thirdSession.getTenantId());//设置租户ID
				ThirdSessionHolder.setThirdSession(thirdSession);//设置thirdSession
			}
		}else{
			R r = R.failed(MyReturnCode.ERR_60002.getCode(), MyReturnCode.ERR_60002.getMsg());
			this.writerPrint(response, r);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	private void writerPrint(HttpServletResponse response, R r) throws IOException {
		//返回超时错误码，触发小程序重新登录
		response.setCharacterEncoding(CommonConstants.UTF8);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		PrintWriter writer = response.getWriter();
		writer.print(JSONUtil.parseObj(r));
		if(writer != null){
			writer.close();
		}
	}
}

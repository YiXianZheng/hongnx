package com.hongnx.cloud.auth.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import com.hongnx.cloud.common.core.constant.SecurityConstants;
import com.hongnx.cloud.common.data.tenant.TenantContextHolder;
import com.hongnx.cloud.common.security.handler.AbstractAuthenticationFailureEvenHandler;
import com.hongnx.cloud.upms.common.entity.SysLogLogin;
import com.hongnx.cloud.upms.common.feign.FeignLogLoginService;
import com.hongnx.cloud.upms.common.util.IpUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author
 */
@Slf4j
@Component
@AllArgsConstructor
public class BaseAuthenticationFailureEvenHandler extends AbstractAuthenticationFailureEvenHandler {

	private final FeignLogLoginService feignLogLoginService;
	/**
	 * 处理登录失败方法
	 * <p>
	 *
	 * @param authenticationException 登录的authentication 对象
	 * @param authentication          登录的authenticationException 对象
	 */
	@Override
	public void handle(AuthenticationException authenticationException, Authentication authentication) {
		log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());
		//登录失败日志统一存在租户1
		TenantContextHolder.setTenantId(CommonConstants.TENANT_ID_1);
		HttpServletRequest request = ((ServletRequestAttributes) Objects
				.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		//异步处理登录日志
		CompletableFuture.runAsync(() -> {
			SysLogLogin sysLogLogin = new SysLogLogin();
			sysLogLogin.setType(CommonConstants.LOG_TYPE_9);
			sysLogLogin.setRemoteAddr(ServletUtil.getClientIP(request));
			sysLogLogin.setRequestUri(URLUtil.getPath(request.getRequestURI()));
			sysLogLogin.setUserAgent(request.getHeader("user-agent"));
//			sysLogLogin.setParams(HttpUtil.toParams(request.getParameterMap()));
			sysLogLogin.setException(StrUtil.format("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage()));
			String address = IpUtils.getAddresses(sysLogLogin.getRemoteAddr());
			sysLogLogin.setAddress(address);
			feignLogLoginService.saveLogLogin(sysLogLogin, SecurityConstants.FROM_IN);
		});
	}
}

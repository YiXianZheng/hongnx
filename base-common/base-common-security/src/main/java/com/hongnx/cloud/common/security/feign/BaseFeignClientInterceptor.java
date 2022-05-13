package com.hongnx.cloud.common.security.feign;

import cn.hutool.core.collection.CollUtil;
import com.hongnx.cloud.common.core.constant.SecurityConstants;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.commons.security.AccessTokenContextRelay;
import org.springframework.cloud.openfeign.security.OAuth2FeignRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import java.util.Collection;

/**
 * @author
 * OAuth2FeignRequestInterceptor扩展
 */
@Slf4j
public class BaseFeignClientInterceptor extends OAuth2FeignRequestInterceptor {
	private final OAuth2ClientContext oAuth2ClientContext;
	private final AccessTokenContextRelay accessTokenContextRelay;

	/**
	 * Default constructor which uses the provided OAuth2ClientContext and Bearer tokens
	 * within Authorization header
	 *
	 * @param oAuth2ClientContext     provided context
	 * @param resource                type of resource to be accessed
	 * @param accessTokenContextRelay
	 */
	public BaseFeignClientInterceptor(OAuth2ClientContext oAuth2ClientContext
			, OAuth2ProtectedResourceDetails resource, AccessTokenContextRelay accessTokenContextRelay) {
		super(oAuth2ClientContext, resource);
		this.oAuth2ClientContext = oAuth2ClientContext;
		this.accessTokenContextRelay = accessTokenContextRelay;
	}


	/**
	 * fein拦截器将本服务的token,通过copyToken的形式传递给下游服务
	 * @param requestTemplate
	 */
	@Override
	public void apply(RequestTemplate requestTemplate) {
		Collection<String> fromHeader = requestTemplate.headers().get(SecurityConstants.FROM);
		if (CollUtil.isNotEmpty(fromHeader) && fromHeader.contains(SecurityConstants.FROM_IN)) {
			return;
		}
		accessTokenContextRelay.copyToken();
		if (oAuth2ClientContext != null
				&& oAuth2ClientContext.getAccessToken() != null) {
			super.apply(requestTemplate);
		}
	}
}

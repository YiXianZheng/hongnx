package com.hongnx.cloud.weixin.admin.service.impl;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongnx.cloud.common.core.constant.CacheConstants;
import com.hongnx.cloud.weixin.admin.service.WxAppService;
import com.hongnx.cloud.weixin.admin.mapper.WxUserMapper;
import com.hongnx.cloud.weixin.common.entity.WxApp;
import com.hongnx.cloud.weixin.admin.mapper.WxAppMapper;
import com.hongnx.cloud.weixin.common.entity.WxUser;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import java.io.Serializable;
import java.util.List;

/**
 * 微信应用
 *
 * @date 2019-03-15 10:26:44
 */
@Service
@AllArgsConstructor
public class WxAppServiceImpl extends ServiceImpl<WxAppMapper, WxApp> implements WxAppService {

	private final WxUserMapper wxUserMapper;

	/**
	 * 微信原始标识查找
	 * @param weixinSign
	 * @return
	 */
	@Override
	@Cacheable(value = CacheConstants.WXAPP_WEIXIN_SIGN_CACHE, key = "#weixinSign", unless = "#result == null")
	public WxApp findByWeixinSign(String weixinSign){
		WxApp wxApp = null;
		List<WxApp> listWxApp = baseMapper.findByWeixinSign(weixinSign);
		if(CollectionUtil.isNotEmpty(listWxApp)){
			wxApp = listWxApp.get(0);
		}
		return wxApp;
	}

	@Override
	@Cacheable(value = CacheConstants.WXAPP_APP_ID_CACHE, key = "#appId", unless = "#result == null")
	public WxApp findByAppId(String appId) {
		WxApp wxApp = null;
		List<WxApp> listWxApp = baseMapper.findByAppId(appId);
		if(CollectionUtil.isNotEmpty(listWxApp)){
			wxApp = listWxApp.get(0);
		}
		return wxApp;
	}

	@Override
	@Cacheable(value = CacheConstants.WXAPP_APP_ID_CACHE, key = "#id", unless = "#result == null")
	public WxApp getById(Serializable id) {
		return baseMapper.selectById(id);
	}

	@Override
	@Caching(evict={
			@CacheEvict(value = CacheConstants.WXAPP_WEIXIN_SIGN_CACHE, allEntries = true),
			@CacheEvict(value = CacheConstants.WXAPP_APP_ID_CACHE, allEntries = true)
	})
	public boolean updateById(WxApp entity) {
		baseMapper.updateById(entity);
		return Boolean.TRUE;
	}

	@Override
	@Caching(evict={
			@CacheEvict(value = CacheConstants.WXAPP_WEIXIN_SIGN_CACHE, allEntries = true),
			@CacheEvict(value = CacheConstants.WXAPP_APP_ID_CACHE, allEntries = true)
	})
	public boolean removeById(Serializable id) {
		baseMapper.deleteById(id);
		//删除该appId下的微信用户
		if(id != null){
			wxUserMapper.delete(Wrappers.<WxUser>lambdaQuery()
					.eq(WxUser::getAppId, id));
		}
		return Boolean.TRUE;
	}
}

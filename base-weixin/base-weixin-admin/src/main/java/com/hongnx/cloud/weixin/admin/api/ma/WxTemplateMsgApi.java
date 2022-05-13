package com.hongnx.cloud.weixin.admin.api.ma;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hongnx.cloud.common.core.util.R;
import com.hongnx.cloud.weixin.admin.service.WxTemplateMsgService;
import com.hongnx.cloud.weixin.common.entity.WxTemplateMsg;
import com.hongnx.cloud.weixin.common.util.WxMaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信模板/订阅消息
 *
 * @date 2020-04-16 17:30:03
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/wxtemplatemsg")
@Api(value = "wxtemplatemsg", tags = "微信模板/订阅消息管理API")
public class WxTemplateMsgApi {

    private final WxTemplateMsgService wxTemplateMsgService;

    /**
     * list列表，可指定useType
     * @param wxTemplateMsg 微信模板/订阅消息
     * @return
     */
    @ApiOperation(value = "list列表")
    @PostMapping("/list")
    public R getList(HttpServletRequest request, @RequestBody WxTemplateMsg wxTemplateMsg) {
		String wxAppId = WxMaUtil.getAppId(request);
		wxTemplateMsg.setAppId(wxAppId);
        return R.ok(wxTemplateMsgService.list(Wrappers.query(wxTemplateMsg).lambda()
				.in(WxTemplateMsg::getUseType,wxTemplateMsg.getUseTypeList())));
    }

}

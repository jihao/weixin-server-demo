package com.superhao.config.wx.handlers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
/**
 * 关注事件
 * 
 * 返回 一个文本消息，提示用户继续完善个人信息，完成绑定流程
 */
public class SubscribeHandler implements WxMpMessageHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(SubscribeHandler.class);

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
            WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
		String eventKey = wxMessage.getEventKey(); // qrscene_quancheng-ec
		String companyId = eventKey;
		LOGGER.info("company id: " + companyId);

		WxMpUser user = wxMpService.getUserService().userInfo(wxMessage.getFromUserName(), "zh_CN");
		String content = "Hi " + user.getNickname() + ", 还差一步<a href=\"#?user=" + user.getOpenId() + "&company="
                + companyId + "\">完善个人信息</a> 就可以开始申请购票了";

		WxMpXmlOutTextMessage m = WxMpXmlOutTextMessage.TEXT().content(content)
                .fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();

		LOGGER.error("outMessage" + m.toXml());
		return m;
	}

}

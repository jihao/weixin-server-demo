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
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.result.WxMpUser;


/**
 * 邀请关注, 生成用户所属公司的二维码，假设先前关注过的用户，已经设置了companyId
 * 
 * 返回 一个图文消息，包含邀请二维码
 */
public class InviteHandler implements WxMpMessageHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(InviteHandler.class);
	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
            WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
		LOGGER.info("wxMpService==null ? " + (wxMpService == null));
		WxMpUser user = wxMpService.getUserService().userInfo(wxMessage.getFromUserName(), "zh_CN");
		// 后台逻辑，获得此用户的公司id，这里是 hard code
		String companyId = "quancheng-ec";

		WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(companyId);
		// "gQFF8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL3Ewd2Y5U2JscUMtUlJ2OVFjMlFMAAIEez6pVwMEAAAAAA==";
		String qrCodePictureUrl = wxMpService.getQrcodeService().qrCodePictureUrl(ticket.getTicket());
		LOGGER.info("qrCodePictureUrl: " + qrCodePictureUrl);
		WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
		item.setDescription(user.getNickname() + " 邀请您加入公司：" + companyId);
		item.setPicUrl(qrCodePictureUrl);
		item.setTitle("邀请加入");
		item.setUrl(qrCodePictureUrl);

		WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS().fromUser(wxMessage.getToUserName())
                .toUser(wxMessage.getFromUserName()).addArticle(item).build();

		LOGGER.error("outMessage=" + m.toXml());
		return m;
	}
}

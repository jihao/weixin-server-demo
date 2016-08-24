package com.superhao.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Controller
@RequestMapping("/weixin")
public class WeiXinController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WeiXinController.class);
	@Autowired
	WxMpConfigStorage wxMpConfigStorage;
	
	@Autowired
	WxMpService wxMpService;

	@Autowired
	WxMpMessageRouter wxMpMessageRouter;
	
	@Value("${spring.wechatmp.app-id}")
	String appid;
	
	@Value("${wexin.menu.redirect_uri}")
	String redirect_url;
	
	@RequestMapping(value = "/connect", method = { RequestMethod.GET })
	@ResponseBody
	public String connect(@RequestParam String signature, @RequestParam String nonce, @RequestParam String timestamp,
	        @RequestParam String echostr) {
		/**
		 * 开发者通过检验signature对请求进行校验（下面有校验方式）。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，
		 * 则接入生效，成为开发者成功，否则接入失败。
		 * 
		 * 加密/校验流程如下： 1. 将token、timestamp、nonce三个参数进行字典序排序 2.
		 * 将三个参数字符串拼接成一个字符串进行sha1加密 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		 */
		System.out.println(wxMpService == null);
		if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
			// 消息签名不正确，说明不是公众平台发过来的消息
			return "非法请求";
		}

		return echostr;
	}

	/**
	 * 微信服务器POST过来的数据
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/connect", method = { RequestMethod.POST }, produces = { MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public String processRequest(HttpServletRequest request, @RequestParam String nonce, @RequestParam String timestamp,
	        @RequestParam(defaultValue = "raw") String encrypt_type) throws IOException {

		WxMpXmlMessage inMessage = null;

		if ("raw".equals(encrypt_type)) {
			// 明文传输的消息
			inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
		} else if ("aes".equals(encrypt_type)) {
			// 是aes加密的消息
			String msgSignature = request.getParameter("msg_signature");
			inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), wxMpConfigStorage, timestamp, nonce,
			        msgSignature);
		} else {
			return "<response>不可识别的加密类型</response>";
		}

		WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);

		if (outMessage != null) {
			if ("raw".equals(encrypt_type)) {
				return outMessage.toXml();
			} else if ("aes".equals(encrypt_type)) {
				return outMessage.toEncryptedXml(wxMpConfigStorage);
			}
		} else {
			return "";
		}
		return "<response>服务号不可用</response>";
	}

	/**
	 * 网页授权获取用户基本信息之后跳转的页面
	 * 
	 * http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
	 * @param code
	 * @param state
	 * @param model
	 * @return
	 * @throws WxErrorException 
	 */
	@RequestMapping(value="/user-info", method={RequestMethod.GET} )
	public String showUserInfo(@RequestParam String code, @RequestParam(required=false) String state, Model model) throws WxErrorException {
//		1 第一步：用户同意授权，获取code
//		2 第二步：通过code换取网页授权access_token
//		3 第三步：刷新access_token（如果需要）
//		4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
//		5 附：检验授权凭证（access_token）是否有效
		
		//获取code后，请求以下链接获取access_token： 
		//https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
		WxMpOAuth2AccessToken oAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
		if(!wxMpService.oauth2validateAccessToken(oAuth2AccessToken)){
			wxMpService.oauth2refreshAccessToken(oAuth2AccessToken.getRefreshToken());
		}
		WxMpUser user = wxMpService.oauth2getUserInfo(oAuth2AccessToken, "zh_CN");
		LOGGER.info(user.toString());
		model.addAttribute("user", user);
		return "user-info";
	}
	
	/**
	 * 演示创建自定义订单
	 * @return
	 * @throws WxErrorException
	 * @throws IOException
	 */
	@RequestMapping(value="/menu/create", method={RequestMethod.PUT}, produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public String createMenu() throws WxErrorException, IOException {
		wxMpService.getMenuService().menuDelete();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("weixin/menu.json");
		String json = IOUtils.toString(is, StandardCharsets.UTF_8);
		json = json.replaceAll("#\\{appid}", this.appid);
		json = json.replaceAll("#\\{redirect_uri}", URLEncoder.encode(this.redirect_url, "utf8"));
		LOGGER.info(json);
		
		wxMpService.getMenuService().menuCreate(WxMenu.fromJson(json));
		return "{\"status\":0,\"message\":\"succeed\"}";
	}
}

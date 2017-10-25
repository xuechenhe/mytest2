package com.xuechenhe.ssm.controller;

import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuechenhe.ssm.common.RequestUtils;
import com.xuechenhe.ssm.pojo.user.Buyer;
import com.xuechenhe.ssm.service.LoginService;

@Controller
@RequestMapping("shopping")
public class LoginController {
		@Autowired
		private LoginService loginService;
	/**
	 * 跳转到登录页面
	 * @param returnUrl
	 * @param model
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String toLogin(String returnUrl,Model model) {
		model.addAttribute("returnUrl", returnUrl);
		return "login";
	}
	/**
	 * 验证用户名密码是否正确
	 * @param username
	 * @param password
	 * @param returnUrl
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="login",method=RequestMethod.POST)
	public String login(String username,String password,String returnUrl,HttpServletRequest request,HttpServletResponse response,Model model) throws Exception {
		if(username!=null) {
			if(password!=null) {
				//根据用户名获取用户对象
				Buyer buyer = loginService.findBuyerByUserName(username);
				if(buyer!=null) {
					if(encodePassword(password).equals(buyer.getPassword())) {
						String token = RequestUtils.getToken(request, response);
						loginService.setUserNameToRedis(token, username);
						return "redirect:"+returnUrl;
					}else {
						model.addAttribute("error", "密码输入错误!");
					}
				}else {
					model.addAttribute("error", "用户名输入错误!");
				}
			}else {
				model.addAttribute("error", "密码不能为空!");
			}
		}else {
			model.addAttribute("error", "用户名不能为空!");
		}
		
		
		model.addAttribute("returnUrl", returnUrl);
		return "login";
		
	}
	
	private String encodePassword(String password)throws Exception {
		//实例化md5
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] md5 = digest.digest(password.getBytes());
		//使用16进制再次加密
		Hex hex = new  Hex();
		byte[] encode = hex.encode(md5);
		return new String(encode);

	}
	/**
	 * 验证用户是否登录
	 */
	//由于浏览器为安全考虑,不允许跨域请求(跨服务器多个tomcat), jquery可以设置dataType为jsonp
	//这样jquery会自动生成一个令牌, springMvc可以用jquery指定的callback接收令牌

	@RequestMapping("/isLogin")
	@ResponseBody
	public MappingJacksonValue isLogin(String callback, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取令牌
		String token = RequestUtils.getToken(request, response);
		//获取用户登录信息
		String userName = loginService.getUserNameFromRedis(token);
		//用户登录状态, 默认0, 没有登录
		Integer flag = 0;
		//判断用户是否登录
		if(userName != null && !"".equals(userName)){
			//用户登录, 将状态设置为1
			flag = 1;
		}
		
		MappingJacksonValue mjv = new MappingJacksonValue(flag);
		//将jquery产生的令牌返回回去
		mjv.setJsonpFunction(callback);
		return mjv;
	}
}

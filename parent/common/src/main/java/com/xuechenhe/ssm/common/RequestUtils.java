package com.xuechenhe.ssm.common;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestUtils {
	
	public static String getToken(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for (Cookie cookie : cookies) {
				if(Constants.TOKEN_KEY.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		Cookie cookie = new Cookie(Constants.TOKEN_KEY,token);
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		response.addCookie(cookie);
		return token;
		
	}
}

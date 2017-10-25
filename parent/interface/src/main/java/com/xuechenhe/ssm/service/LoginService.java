package com.xuechenhe.ssm.service;

import com.xuechenhe.ssm.pojo.user.Buyer;

public interface LoginService {
	public Buyer findBuyerByUserName(String userName);
	public void setUserNameToRedis(String token,String userName);
	public String getUserNameFromRedis(String token);
}

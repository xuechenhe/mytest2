package com.xuechenhe.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuechenhe.ssm.common.Constants;
import com.xuechenhe.ssm.dao.user.BuyerDao;
import com.xuechenhe.ssm.pojo.user.Buyer;
import com.xuechenhe.ssm.pojo.user.BuyerQuery;
import com.xuechenhe.ssm.pojo.user.BuyerQuery.Criteria;

import redis.clients.jedis.Jedis;
@Service("loginServiceImpl")
@Transactional
public class LoginServiceImpl implements LoginService {
	@Autowired 
	private BuyerDao buyerDao;
	@Autowired
	private Jedis jedis;
	@Override
	public Buyer findBuyerByUserName(String userName) {
		BuyerQuery buyerQuery=new BuyerQuery();
		Criteria createCriteria = buyerQuery.createCriteria();
		createCriteria.andUsernameEqualTo(userName);
		List<Buyer> buyerList = buyerDao.selectByExample(buyerQuery);
		if(buyerList!=null && buyerList.size()>0) {
			return buyerList.get(0);
			
		}
		return null;
	}

	@Override
	public void setUserNameToRedis(String token, String userName) {
		jedis.set(Constants.USER_NAME_KEY+token, userName);
		jedis.expire(Constants.USER_NAME_KEY+token, 30*60);//设置用户等登录在redis中的生存时间为30分钟
		
	}

	@Override
	public String getUserNameFromRedis(String token) {
		String userName = jedis.get(Constants.USER_NAME_KEY+token);
		//如果用户已经登录过,那么需要重置超时时间，防止客户使用期间登录超时
		if(userName!=null && !"".equals(userName)) {
			jedis.expire(Constants.USER_NAME_KEY+token ,60*30);
			
		}
		return userName;
	}

}

package com.xuechenhe.ssm.service;

import java.util.List;

import com.xuechenhe.ssm.pojo.ad.Ad;

public interface AdService {

	List<Ad> findAdListByParentId(Long parentId);
	void insertAd(Ad ad);
	String findAdListFromRedis()throws Exception;
}

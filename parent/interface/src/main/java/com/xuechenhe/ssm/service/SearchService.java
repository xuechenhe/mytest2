package com.xuechenhe.ssm.service;

import cn.itcast.common.page.Pagination;

public interface SearchService {
	
	public abstract Pagination searchProductPage(String keyword,Integer pageNo)throws Exception;
} 

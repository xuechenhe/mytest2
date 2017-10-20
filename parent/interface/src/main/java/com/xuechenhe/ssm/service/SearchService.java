package com.xuechenhe.ssm.service;

import cn.itcast.common.page.Pagination;

public interface SearchService {
	

	Pagination searchProductPage(String keyword, String price, Long brandId, Integer pageNo) throws Exception;
} 

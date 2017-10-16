package com.xuechenhe.ssm.service;

import cn.itcast.common.page.Pagination;

public interface ProductService {
	
	public abstract Pagination findProductPage(String name ,Long brandId, Boolean isShow,Integer pageNo);
}

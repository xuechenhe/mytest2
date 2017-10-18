package com.xuechenhe.ssm.service;

import com.xuechenhe.ssm.pojo.product.Product;

import cn.itcast.common.page.Pagination;

public interface ProductService {
	
	public abstract Pagination findProductPage(String name ,Long brandId, Boolean isShow,Integer pageNo);
	
	public abstract void insertProduct(Product product);

	public abstract void isShow(Long[] ids) throws Exception;
}

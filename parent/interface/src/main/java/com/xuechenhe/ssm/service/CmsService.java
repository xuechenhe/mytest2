package com.xuechenhe.ssm.service;

import java.util.List;

import com.xuechenhe.ssm.pojo.product.Product;
import com.xuechenhe.ssm.pojo.product.Sku;

public interface CmsService {
	
	public Product findProductById(Long id);
	
	public List<Sku> findSkuListByProductId(Long productId);
}

package com.xuechenhe.ssm.service;

import java.util.List;

import com.xuechenhe.ssm.pojo.product.Sku;

public interface SkuService {
	
	 public abstract List<Sku> findSkuList(Long productId);
	 
	 public abstract void updateSku(Sku sku);
}

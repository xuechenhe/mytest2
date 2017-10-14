package com.xuechenhe.ssm.service;

import java.util.List;

import com.xuechenhe.ssm.pojo.Brand;

import cn.itcast.common.page.Pagination;

public interface BrandService {
	
	public abstract List<Brand> queryBrandByNameAndIsDisplay(String name,Integer isDisplay);
	
	public abstract Pagination findBrandPage(String name,Integer isDisplay,Integer pageNo);
}

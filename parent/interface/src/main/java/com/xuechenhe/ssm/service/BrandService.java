package com.xuechenhe.ssm.service;

import java.util.List;

import com.xuechenhe.ssm.pojo.Brand;

import cn.itcast.common.page.Pagination;

public interface BrandService {
	
	public abstract List<Brand> queryBrandByNameAndIsDisplay(String name,Integer isDisplay);
	
	public abstract Pagination findBrandPage(String name,Integer isDisplay,Integer pageNo);

	public abstract Brand findBrandById(Long id);

	public abstract void update(Brand brand);

	public abstract void addBrand(Brand brand);

	public abstract void deleteBrand(Long id);

	public abstract void deleteAll(long[] ids);
}

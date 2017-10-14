package com.xuechenhe.ssm.dao;

import java.util.List;

import com.xuechenhe.ssm.pojo.Brand;
import com.xuechenhe.ssm.pojo.BrandQuery;

public interface BrandDao {
	
	public  abstract List<Brand> queryBrandByNameAndIsDisplay(BrandQuery brandQuery);
	
	public  abstract List<Brand>  queryBrandPageByQuery(BrandQuery brandQuery);
	
	public  abstract int queryBrandPageCountByQuery(BrandQuery brandQuery);

	public abstract Brand findBrandById(Long id);
}

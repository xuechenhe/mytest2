package com.xuechenhe.ssm.dao.product;

import com.xuechenhe.ssm.pojo.product.Brand;
import com.xuechenhe.ssm.pojo.product.BrandQuery;
import java.util.List;

public interface BrandDao {
	public  abstract List<Brand> queryBrandByNameAndIsDisplay(BrandQuery brandQuery);
	
	public  abstract List<Brand>  queryBrandPageByQuery(BrandQuery brandQuery);
	
	public  abstract int queryBrandPageCountByQuery(BrandQuery brandQuery);

	public abstract Brand findBrandById(Long id);

	public abstract void update(Brand brand);

	public abstract void addBrand(Brand brand);

	public abstract void deleteBrand(Long id);


	public abstract void deleteAll(long[] ids);
}
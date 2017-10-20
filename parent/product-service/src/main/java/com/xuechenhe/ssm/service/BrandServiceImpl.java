package com.xuechenhe.ssm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuechenhe.ssm.common.Constants;
import com.xuechenhe.ssm.dao.product.BrandDao;
import com.xuechenhe.ssm.pojo.product.Brand;
import com.xuechenhe.ssm.pojo.product.BrandQuery;

import cn.itcast.common.page.Pagination;
import redis.clients.jedis.Jedis;
@Service("brandServiceImpl")
@Transactional
public class BrandServiceImpl implements BrandService {
	@Autowired
	private BrandDao brandDao;
	
	@Autowired
	private Jedis jedis;
	@Override
	public List<Brand> queryBrandByNameAndIsDisplay(String name, Integer isDisplay) {
		BrandQuery b = new BrandQuery();
		b.setName(name);
		b.setIsDisplay(isDisplay);
		
		List<Brand> brandList = brandDao.queryBrandByNameAndIsDisplay(b);
		return brandList;
	}
	@Override
	public Pagination findBrandPage(String name, Integer isDisplay, Integer pageNo) {
		StringBuilder params=new StringBuilder();
		BrandQuery bq = new BrandQuery();
		bq.setPageNo(Pagination.cpn(pageNo));
		bq.setPageSize(10);
		if(name!=null) {
			bq.setName(name);
			params.append("&name=").append(name);
		}
		if(isDisplay!=null) {
			bq.setIsDisplay(isDisplay);
			params.append("&isDisplay").append(isDisplay);
		}
		List<Brand> list = brandDao.queryBrandPageByQuery(bq);
		int count = brandDao.queryBrandPageCountByQuery(bq);
		Pagination page=new Pagination(bq.getPageNo(), bq.getPageSize(), count, list);
		
		page.pageView("/brand/list.action",params.toString());
		return page;
	}
	@Override
	public Brand findBrandById(Long id) {
		Brand brand = brandDao.findBrandById(id);
		return brand;
	}
	@Override
	public void update(Brand brand) {
		
		brandDao.update(brand);
		jedis.hset(Constants.BRAND_KEY, String.valueOf(brand.getId()), brand.getName());
	}
	@Override
	public void addBrand(Brand brand) {
		brandDao.addBrand(brand);
		
	}
	@Override
	public void deleteBrand(Long id) {
		brandDao.deleteBrand(id);
		
	}
	@Override
	public void deleteAll(long[] ids) {
	 
			brandDao.deleteAll(ids);
		
	}
	@Override
	public List<Brand> findBrandListFromRedis() {
		List<Brand> brandList=new ArrayList<>();
		
		Map<String, String> map = jedis.hgetAll(Constants.BRAND_KEY);
		if(map!=null && map.size()>0) {
			Set<Entry<String, String>> entrySet = map.entrySet();
			for (Entry<String, String> entry : entrySet) {
				Brand brand = new Brand();
				brand.setId(Long.parseLong(entry.getKey()));
				brand.setName(entry.getValue());
				brandList.add(brand);
			}
		}
		return brandList;
	}
	

 

}

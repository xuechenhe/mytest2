package com.xuechenhe.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuechenhe.ssm.dao.BrandDao;
import com.xuechenhe.ssm.pojo.Brand;
import com.xuechenhe.ssm.pojo.BrandQuery;

import cn.itcast.common.page.Pagination;
@Service("brandServiceImpl")
@Transactional
public class BrandServiceImpl implements BrandService {
	@Autowired
	private BrandDao brandDao;
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
		List<Brand> list = brandDao.queryBrandByNameAndIsDisplay(bq);
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
 

}

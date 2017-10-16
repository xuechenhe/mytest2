package com.xuechenhe.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuechenhe.ssm.dao.product.ProductDao;
import com.xuechenhe.ssm.pojo.product.Product;
import com.xuechenhe.ssm.pojo.product.ProductQuery;
import com.xuechenhe.ssm.pojo.product.ProductQuery.Criteria;

import cn.itcast.common.page.Pagination;


@Service("productServiceImpl")
@Transactional
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDao productDao;

	@Override
	public Pagination findProductPage(String name, Long brandId, Boolean isShow, Integer pageNo) {
		
		StringBuilder params = new StringBuilder();
		ProductQuery pq=new ProductQuery();
		Criteria criteria = pq.createCriteria();
		if(name!=null){
			criteria.andNameLike("%"+name+"%");
			params.append("&name=").append(name);
		}
		if(brandId!=null) {
			criteria.andBrandIdEqualTo(brandId);
			params.append("&brandId=").append(brandId);
		}
		if(isShow!=null) {
			criteria.andIsShowEqualTo(isShow);
			params.append("&isShow=").append(isShow);
		}
		pq.setPageSize(10);
		pq.setPageNo(Pagination.cpn(pageNo));
		
		pq.setOrderByClause("id desc");
		
		
		int totalCount=productDao.countByExample(pq);
		List<Product> list=productDao.selectByExample(pq);
		Pagination page = new Pagination(pq.getPageNo(), pq.getPageSize(), totalCount, list);
		page.pageView("/product/list.action",params.toString());
		return page;
	}
	 
}

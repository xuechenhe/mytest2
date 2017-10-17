package com.xuechenhe.ssm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuechenhe.ssm.dao.product.ProductDao;
import com.xuechenhe.ssm.dao.product.SkuDao;
import com.xuechenhe.ssm.pojo.product.Product;
import com.xuechenhe.ssm.pojo.product.ProductQuery;
import com.xuechenhe.ssm.pojo.product.ProductQuery.Criteria;
import com.xuechenhe.ssm.pojo.product.Sku;

import cn.itcast.common.page.Pagination;
import redis.clients.jedis.Jedis;


@Service("productServiceImpl")
@Transactional
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDao productDao;
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private Jedis jedis;

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
		//未逻辑删除的
		criteria.andIsDelEqualTo(true);
		pq.setPageSize(10);
		pq.setPageNo(Pagination.cpn(pageNo));
		
		pq.setOrderByClause("id desc");
		
		int totalCount=productDao.countByExample(pq);
		List<Product> list=productDao.selectByExample(pq);
		Pagination page = new Pagination(pq.getPageNo(), pq.getPageSize(), totalCount, list);
		page.pageView("/product/list.action",params.toString());
		return page;
	}

	@Override
	public void insertProduct(Product product) {
		
		
		Long productId = jedis.incr("pno");
		product.setId(productId);
		
		
		product.setCreateTime(new Date());
		product.setIsDel(true);
		product.setIsShow(false);
		
		productDao.insertSelective(product);
		
		for(String colorId : product.getColors().split(",")) {
			for (String size : product.getSizes().split(",")) {
				Sku sku = new Sku();
				sku.setColorId(Long.valueOf(colorId));
				sku.setSize(size);
				sku.setCreateTime(new Date());
				sku.setDeliveFee(10f);
				sku.setMarketPrice(0f);
				sku.setPrice(0f);
				sku.setProductId(product.getId());
				sku.setStock(0);
				sku.setUpperLimit(200);
				skuDao.insertSelective(sku);
			}
		}

		
	}
	 
}

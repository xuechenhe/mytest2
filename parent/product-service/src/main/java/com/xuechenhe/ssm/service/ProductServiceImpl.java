package com.xuechenhe.ssm.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuechenhe.ssm.dao.product.ProductDao;
import com.xuechenhe.ssm.dao.product.SkuDao;
import com.xuechenhe.ssm.pojo.product.Product;
import com.xuechenhe.ssm.pojo.product.ProductQuery;
import com.xuechenhe.ssm.pojo.product.ProductQuery.Criteria;
import com.xuechenhe.ssm.pojo.product.Sku;
import com.xuechenhe.ssm.pojo.product.SkuQuery;

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
	@Autowired
	private SolrServer solrServer;

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

	@Override
	public void isShow(Long[] ids) throws Exception  {
		if(ids!=null) {
			for (Long id : ids) {
				//1.根据id改变数据库中商品的上架状态
				Product product = new Product();
				product.setId(id);
				product.setIsShow(true);
				productDao.updateByPrimaryKeySelective(product);
				
				//2.根据id获取商品的详细数据
				product= productDao.selectByPrimaryKey(id);
				//获取同款商品最便宜的价格
				SkuQuery skuQuery = new SkuQuery();
				skuQuery.setOrderByClause("price asc");
				skuQuery.setPageNo(1);
				skuQuery.setPageSize(1);
				skuQuery.setFields("price");
				
				com.xuechenhe.ssm.pojo.product.SkuQuery.Criteria criteria = skuQuery.createCriteria();
				criteria.andProductIdEqualTo(id);
				
				List<Sku> skuList = skuDao.selectByExample(skuQuery);
				//3.将商品放入solr索引库
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", product.getId());
				doc.addField("name_ik", product.getName());
				doc.addField("url", product.getImgUrl());
				doc.addField("brandId", product.getBrandId());
				if(skuList!=null && skuList.size()>0) {
					doc.addField("price", skuList.get(0).getPrice());
				}else {
					doc.addField("price", 0);
				}
				 //将文档加入到solr服务中
				solrServer.add(doc);
				solrServer.commit();
				
			}
		}
		
	}
	 
}

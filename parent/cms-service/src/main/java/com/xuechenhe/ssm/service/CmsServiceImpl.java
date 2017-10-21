package com.xuechenhe.ssm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuechenhe.ssm.dao.product.ColorDao;
import com.xuechenhe.ssm.dao.product.ProductDao;
import com.xuechenhe.ssm.dao.product.SkuDao;
import com.xuechenhe.ssm.pojo.product.Color;
import com.xuechenhe.ssm.pojo.product.Product;
import com.xuechenhe.ssm.pojo.product.Sku;
import com.xuechenhe.ssm.pojo.product.SkuQuery;
import com.xuechenhe.ssm.pojo.product.SkuQuery.Criteria;
@Service("cmsServiceImpl")
@Transactional
public class CmsServiceImpl implements CmsService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private ColorDao colorDao;
	@Override
	public Product findProductById(Long id) {

		Product product = productDao.selectByPrimaryKey(id);
		return product;
	}
	@Override
	public List<Sku> findSkuListByProductId(Long productId) {
		List<Sku> skuList=new ArrayList<>();
		
		SkuQuery sq=new SkuQuery();
		Criteria criteria = sq.createCriteria();
		criteria.andProductIdEqualTo(productId);
		
		skuList = skuDao.selectByExample(sq);
		if(skuList!=null) {
			for (Sku sku : skuList) {
				Long colorId = sku.getColorId();
				Color color = colorDao.selectByPrimaryKey(colorId);
				sku.setColor(color);
				
			}
		}
		return skuList;
	}

}

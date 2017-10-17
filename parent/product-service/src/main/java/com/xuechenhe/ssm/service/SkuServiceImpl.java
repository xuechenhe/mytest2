package com.xuechenhe.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuechenhe.ssm.dao.product.ColorDao;
import com.xuechenhe.ssm.dao.product.SkuDao;
import com.xuechenhe.ssm.pojo.product.Color;
import com.xuechenhe.ssm.pojo.product.Sku;
import com.xuechenhe.ssm.pojo.product.SkuQuery;
import com.xuechenhe.ssm.pojo.product.SkuQuery.Criteria;
@Service("skuServiceImpl")
@Transactional
public class SkuServiceImpl implements SkuService {
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private ColorDao colorDao;
	@Override
	public List<Sku> findSkuList(Long productId) {
		SkuQuery sq = new SkuQuery();
		Criteria criteria = sq.createCriteria();
		criteria.andProductIdEqualTo(productId);
		List<Sku> skuList = skuDao.selectByExample(sq);
		
		for (Sku sku : skuList) {
			Color color = colorDao.selectByPrimaryKey(sku.getColorId());
			
			sku.setColor(color);
		}
		return skuList;
	}
	@Override
	public void updateSku(Sku sku) {
		skuDao.updateByPrimaryKeySelective(sku);
		
	}

}

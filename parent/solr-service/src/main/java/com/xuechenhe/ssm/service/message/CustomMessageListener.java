package com.xuechenhe.ssm.service.message;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuechenhe.ssm.dao.product.ProductDao;
import com.xuechenhe.ssm.dao.product.SkuDao;
import com.xuechenhe.ssm.pojo.product.Product;
import com.xuechenhe.ssm.pojo.product.Sku;
import com.xuechenhe.ssm.pojo.product.SkuQuery;

public class CustomMessageListener implements MessageListener{
	@Autowired
	private ProductDao productDao;
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private SolrServer solrServer;
	@Override
	public void onMessage(Message message) {
		ActiveMQTextMessage atm=(ActiveMQTextMessage)message;
		try {
			String productId = atm.getText();
			
			//2.根据id获取商品的详细数据
			Product product= productDao.selectByPrimaryKey(Long.valueOf(productId));
			//获取同款商品最便宜的价格
			SkuQuery skuQuery = new SkuQuery();
			skuQuery.setOrderByClause("price asc");
			skuQuery.setPageNo(1);
			skuQuery.setPageSize(1);
			skuQuery.setFields("price");
			
			com.xuechenhe.ssm.pojo.product.SkuQuery.Criteria criteria = skuQuery.createCriteria();
			criteria.andProductIdEqualTo(Long.valueOf(productId));
			
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

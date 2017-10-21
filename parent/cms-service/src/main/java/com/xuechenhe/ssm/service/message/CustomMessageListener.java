package com.xuechenhe.ssm.service.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuechenhe.ssm.pojo.product.Color;
import com.xuechenhe.ssm.pojo.product.Product;
import com.xuechenhe.ssm.pojo.product.Sku;
import com.xuechenhe.ssm.service.CmsService;
import com.xuechenhe.ssm.service.StaticPageService;

/**
 * 自定义监听器
 * 接收来自于消息服务器发送过来的商品id, 
 * 根据商品id获取商品详细数据, 生成静态化页面
 * @author ZJ
 *
 */
public class CustomMessageListener implements MessageListener {
	
	@Autowired
	private CmsService cmsService;
	
	@Autowired
	private StaticPageService staticPageService;

	@Override
	public void onMessage(Message message) {
		
		//System.err.println("sdfsdkgopfjgorepgore");
		ActiveMQTextMessage atm = (ActiveMQTextMessage)message;
		try {
			//1. 获取商品id
			String productId = atm.getText();
			//2. 根据id获取商品详情页面需要的数据
			//根据id获取商品数据
			Product product = cmsService.findProductById(Long.parseLong(productId));
			//根据商品id获取库存集合数据
			List<Sku> skuList = cmsService.findSkuListByProductId(Long.parseLong(productId));
			
			//使用set过滤颜色重复数据
			Set<Color> colors = new HashSet<>();
			if(skuList != null){
				for(Sku sku : skuList){
					colors.add(sku.getColor());
				}
			}
			
			//封装模板中需要的数据
			Map<String, Object> rootMap = new HashMap<>();
			rootMap.put("product", product);
			rootMap.put("skuList", skuList);
			rootMap.put("colors", colors);
			
			//3. 将数据传入模板中生成静态化页面
			staticPageService.createStaticPage(rootMap, productId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

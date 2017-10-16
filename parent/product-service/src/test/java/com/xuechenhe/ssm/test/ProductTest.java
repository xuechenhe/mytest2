package com.xuechenhe.ssm.test;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xuechenhe.ssm.dao.product.ProductDao;
import com.xuechenhe.ssm.pojo.product.Product;
import com.xuechenhe.ssm.pojo.product.ProductQuery;
import com.xuechenhe.ssm.pojo.product.ProductQuery.Criteria;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:application-context.xml"})
public class ProductTest {
	@Autowired
	private ProductDao productDao;
	@Test
	public void testProductQuery() throws Exception {
		ProductQuery pq = new ProductQuery();
		/*List<Product> list = productDao.selectByExample(null);
		for (Product product : list) {
			System.out.println(product);
		}*/
		pq.setFields("id,name");
		pq.setOrderByClause("id desc");
		pq.setPageNo(1);
		pq.setPageSize(10);
		Criteria criteria = pq.createCriteria();
		criteria.andNameLike("%"+2016+"%");
		List<Product> list = productDao.selectByExample(pq);
		for (Product product : list) {
			System.err.println(product);
		}
		
	}
}

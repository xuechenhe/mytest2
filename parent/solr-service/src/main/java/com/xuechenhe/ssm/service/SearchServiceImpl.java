package com.xuechenhe.ssm.service;

import java.util.ArrayList;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuechenhe.ssm.pojo.product.Product;
import com.xuechenhe.ssm.pojo.product.ProductQuery;

import cn.itcast.common.page.Pagination;
@Service("searchServiceImpl")
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SolrServer solrService;
	@Override
	public Pagination searchProductPage(String keyword,Integer pageNo) throws Exception {
		StringBuilder params=new StringBuilder();
		ProductQuery pq = new ProductQuery();
		pq.setPageSize(8);
		pq.setPageNo(Pagination.cpn(pageNo));
		ArrayList<Product> productList = new ArrayList<Product>();
		SolrQuery sq=new SolrQuery();
		if(keyword!=null) {
			
			sq.setQuery("name_ik:"+keyword);
			params.append("&keyword=").append(keyword);
		}else {
			sq.setQuery("*:*");
		}
		
		sq.setStart(pq.getStartRow());
		sq.setRows(pq.getPageSize());
		sq.setSort("price",ORDER.asc);
		
		int totalCount = 0;
		
		
		QueryResponse queryResponse = solrService.query(sq);
		SolrDocumentList results = queryResponse.getResults();
		if(results!=null) {
			totalCount= (int) results.getNumFound();
			for (SolrDocument doc : results) {
				Product p = new Product();
				p.setId(Long.parseLong(String.valueOf(doc.get("id"))));
				p.setName(String.valueOf(doc.get("name_ik")));
				p.setBrandId(Long.parseLong(String.valueOf(doc.get("brandId"))));
				
				if(    String.valueOf(doc.get("price")) !=null     &&  !"".equals( String.valueOf(doc.get("price")))                        ) {
					p.setPrice(Float.valueOf(String.valueOf(doc.get("price"))));
				}else {
					p.setPrice(0f);
				}
				
				productList.add(p);
			}
		}
		
		
		
		Pagination page = new Pagination(pq.getPageNo(), pq.getPageSize(), totalCount, productList);
		
		page.pageView("/product/list", params.toString());
		return page;
	}

}

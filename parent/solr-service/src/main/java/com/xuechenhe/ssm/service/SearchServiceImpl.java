package com.xuechenhe.ssm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
	public Pagination searchProductPage(String keyword,String price,Long brandId,Integer pageNo) throws Exception {
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
		
		
		
		
		
		
		
		if(brandId!=null) {
			sq.addFilterQuery("brandId:"+brandId);
			params.append("&brandId=").append(brandId);
		}
		if(price!=null) {
			String[] split = price.split("-");
			if(split.length==2) {
				sq.addFilterQuery("price:[ "+split[0]+"  TO "+split[1]+"]");
				
			}else {
				sq.addFilterQuery("price:[ "+split[0]+" TO *]");
			}
			
			params.append("&price=").append(price);
		}
		
		sq.setHighlight(true);
		sq.setHighlightSimplePre("<span style=\"color:red\">");
		sq.setHighlightSimplePost("</span>");
		sq.addHighlightField("name_ik");
		
		
		
		
		
		
		
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
				Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
				if(highlighting!=null && highlighting.size()>0) {
					List<String> list = highlighting.get(doc.get("id")).get("name_ik");
					if(list!=null && list.size()>0) {
						p.setName(list.get(0));
					}else {
						p.setName(String.valueOf(doc.get("name_ik")));
					}
				}else {
					p.setName(String.valueOf(doc.get("name_ik")));
				}
				
				p.setId(Long.parseLong(String.valueOf(doc.get("id"))));
				
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

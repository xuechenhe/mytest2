package com.xuechenhe.ssm.test;


import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {
	
	@Test
	public void testSolr() throws Exception {
		String baseURL="http://192.168.200.128:8080/solr";
		HttpSolrServer solrServer = new HttpSolrServer(baseURL);
		
		//保存
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", "002");
		doc.setField("name", "夔牛");
		solrServer.add(doc);
		
		solrServer.commit();
	}
}

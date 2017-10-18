package com.xuechenhe.ssm.test;


import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:application-context.xml"})
public class SpringSolrTest {
	@Autowired
	private SolrServer solrServer;
	@Test
	public void testSolr() throws Exception {
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", "003");
		doc.setField("name", "玄武");
		solrServer.add(doc);
		solrServer.commit();
	}
}

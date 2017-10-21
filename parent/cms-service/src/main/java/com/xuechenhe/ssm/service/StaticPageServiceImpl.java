package com.xuechenhe.ssm.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 生成静态化页面service
 * 这里面省略的@Service注解是因为在freemarker.xml中已经配置了
 * 因为需要一些额外参数的配置所以使用配置文件配置
 * @author ZJ
 *
 */
public class StaticPageServiceImpl implements StaticPageService , ServletContextAware{
	
	private Configuration configration;
	
	//此属性为了获取当前项目的ip和端口
	private ServletContext servletContext;
	
	//根据freemark.xml中的配置初始化configration对象
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) throws Exception {
		configration = freeMarkerConfigurer.createConfiguration();
	}

	@Override
	public void createStaticPage(Map<String, Object> rootMap, String productId) throws Exception {
		//设置静态页面生成的路径
		String path = "/html/" + productId + ".html";
		//根据指定目录的位置获取带ip和端口的位置, 例如:http://localhost:8086/html/1.html
		String url = getRealPath(path);
		
		//将字符串的url转换成对象
		File file = new File(url);
		//获取父级目录
		File parentFile = file.getParentFile();
		//判断父级目录是否存在
		if(!parentFile.exists()){
			//如果不存在则自动创建目录
			parentFile.mkdirs();
		}
		
		
		//获取模板对象, 模板所在位置已经在freemarker.xml中配置了
		Template template = configration.getTemplate("product.html");
		//指定静态页面的输入位置和名称
		Writer out = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
		//生成静态化页面
		template.process(rootMap, out);
		
	}
	
	//获取当前项目的ip和端口号
	private String getRealPath(String path) {
		return servletContext.getRealPath(path);
	}

	//通过spring中的ServletContextAware对象来初始化ServletContext对象
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}

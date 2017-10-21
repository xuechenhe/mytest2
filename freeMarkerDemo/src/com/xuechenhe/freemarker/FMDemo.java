package com.xuechenhe.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FMDemo {
	
	public static void main(String[] args)throws Exception {
		//1. 初始化freemarker
		Configuration conf = new Configuration();
		//2. 设置加载模板的目录位置, 目录要写成在硬盘上的位置
		conf.setDirectoryForTemplateLoading(new File("C:\\Users\\Administrator\\git\\mytest2\\freeMarkerDemo\\ftl"));
		//3. 加载模板
		Template template = conf.getTemplate("freemarker.html");
		//放入模板中的数据对象
		Map<String ,Object> rootMap=new HashMap<>();
		//向页面传入String类型数据
		rootMap.put("hello", "hello world");
		//设置一个输出流, 指定文件的输出名称和位置
		FileWriter out = new FileWriter(new File("hello.html"));
		//4. 生成静态化页面, 第一个参数:传入模板中的数据, 第二个参数:是一个io流, 指定将生成的静态化页面放到硬盘的哪个目录中
		template.process(rootMap, out);
	} 
}

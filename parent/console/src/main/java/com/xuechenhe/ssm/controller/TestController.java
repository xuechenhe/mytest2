package com.xuechenhe.ssm.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xuechenhe.ssm.pojo.TbTest;
import com.xuechenhe.ssm.service.TbTestService;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TbTestService tbTestService;
	@RequestMapping("/list")
	public String test() {
		TbTest tbTest = new TbTest();
		tbTest.setName("凤凰");
		tbTest.setBirthday(new Date());
		tbTestService.insertTest(tbTest);
		
		return "list";
		
	}
}

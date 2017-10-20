package com.xuechenhe.ssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xuechenhe.ssm.service.SearchService;

import cn.itcast.common.page.Pagination;

@Controller
public class ProductController {
	@Autowired
	private SearchService searchService;
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/product/list")
	public String list(String keyword,Integer pageNo,Model model) throws Exception {
		Pagination pagination = searchService.searchProductPage(keyword, pageNo);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pagination", pagination);
		return "search";
	}
}

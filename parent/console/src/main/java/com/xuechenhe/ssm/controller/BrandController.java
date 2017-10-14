package com.xuechenhe.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xuechenhe.ssm.service.BrandService;

import cn.itcast.common.page.Pagination;

@Controller
@RequestMapping("brand")
public class BrandController {
	@Autowired
	private BrandService brandService;
	@RequestMapping("list")
	public String list(String name,Integer isDisplay,Integer pageNo,Model model) {
		model.addAttribute("name",name);
		model.addAttribute("isDisplay",isDisplay);
		//List<Brand> list = brandService.queryBrandByNameAndIsDisplay(name, isDisplay);
		//model.addAttribute("list", list);
		Pagination page = brandService.findBrandPage(name, isDisplay, pageNo);
		model.addAttribute("page", page);
		return "brand/list";
		
	}
}

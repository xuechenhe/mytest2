package com.xuechenhe.ssm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xuechenhe.ssm.pojo.Brand;
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
	
	@RequestMapping("toUpdate")
	public String toUpdate(Long id,Model model) {
		Brand brand = brandService.findBrandById(id);
		model.addAttribute("brand", brand);
		return "brand/edit";
	}
	
	@RequestMapping("update")
	public String update(Brand brand) {
		brandService.update(brand);
		return "redirect:/brand/list.action";
	}
	
	@RequestMapping("toAdd")
	public String toAdd(Brand brand) {
		return "brand/add";
		
	}
	@RequestMapping("add")
	public String add(Brand brand) {
		brandService.addBrand(brand);
		return "redirect:list.action";
		
	}
	
	@RequestMapping("deleteBrand")
	public String deleteBrand(Long id) {
		brandService.deleteBrand(id);
		return "redirect:/brand/list.action";
		
	}
	
	@RequestMapping("deleteAll")
	public String deleteAll(String ids) {
		brandService.deleteAll(ids);
		return "redirect:/brand/list.action";
		
	}
}

package com.xuechenhe.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xuechenhe.ssm.pojo.product.Brand;
import com.xuechenhe.ssm.service.BrandService;
import com.xuechenhe.ssm.service.ProductService;

import cn.itcast.common.page.Pagination;

@Controller
@RequestMapping("product")
public class ProductController {
	@Autowired
	private ProductService  productService;
	
	@Autowired
	
	private BrandService  brandService;
	
	@RequestMapping("list")
	public String list(String name,Long brandId,boolean isShow,Integer pageNo,Model model) {
		model.addAttribute("name", name);
		model.addAttribute("brandId", brandId);
		model.addAttribute("isShow", isShow);
	  
		List<Brand> brandList = brandService.queryBrandByNameAndIsDisplay(null, null);
		model.addAttribute("brandList", brandList);
		
		Pagination page = productService.findProductPage(name, brandId, isShow, pageNo);
		model.addAttribute("page", page);
		return "product/list";
		
	}
}

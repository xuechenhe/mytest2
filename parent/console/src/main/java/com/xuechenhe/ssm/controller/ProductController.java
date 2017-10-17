package com.xuechenhe.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xuechenhe.ssm.pojo.product.Brand;
import com.xuechenhe.ssm.pojo.product.Color;
import com.xuechenhe.ssm.pojo.product.Product;
import com.xuechenhe.ssm.service.BrandService;
import com.xuechenhe.ssm.service.ColorService;
import com.xuechenhe.ssm.service.ProductService;

import cn.itcast.common.page.Pagination;

@Controller
@RequestMapping("product")
public class ProductController {
	@Autowired
	private ProductService  productService;
	
	@Autowired
	
	private BrandService  brandService;
	@Autowired
	
	private ColorService  colorService;
	
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
	@RequestMapping("toAdd")
	public String toAdd(Model model) {
		List<Color> colorList = colorService.findColorList();
		List<Brand> brandList = brandService.queryBrandByNameAndIsDisplay(null, null);
		model.addAttribute("colorList", colorList);
		model.addAttribute("brandList", brandList);
		return "product/add";
		
	}
	@RequestMapping("add")
	public String  add(Product product) {
		productService.insertProduct(product);
		return "redirect:list.action";
		
	}
}

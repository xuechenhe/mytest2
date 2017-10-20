package com.xuechenhe.ssm.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xuechenhe.ssm.pojo.product.Brand;
import com.xuechenhe.ssm.service.BrandService;
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
	@Autowired
	private BrandService brandService;
	@RequestMapping("/product/list")
	public String list(String keyword,Integer pageNo,Long brandId,String price,Model model) throws Exception {
		//List<Brand> brands = brandService.queryBrandByNameAndIsDisplay(null, null);
		
		List<Brand> brands = brandService.findBrandListFromRedis();
		Pagination pagination = searchService.searchProductPage(keyword, price, brandId, pageNo);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pagination", pagination);
		model.addAttribute("brands", brands);
		model.addAttribute("brandId", brandId);
		model.addAttribute("price", price);
		
		HashMap<String,String> map=new HashMap<>();
		if(brandId!=null) {
			for (Brand brand : brands) {
				brandId=brand.getId();
				map.put("品牌为:", brand.getName());
			}
		}
		if(price!=null) {
			map.put("价格为", price);
		}
		model.addAttribute("map", map);
		return "search";
	}
}

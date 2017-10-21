package com.xuechenhe.ssm.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xuechenhe.ssm.pojo.product.Brand;
import com.xuechenhe.ssm.pojo.product.Color;
import com.xuechenhe.ssm.pojo.product.Product;
import com.xuechenhe.ssm.pojo.product.Sku;
import com.xuechenhe.ssm.service.BrandService;
import com.xuechenhe.ssm.service.CmsService;
import com.xuechenhe.ssm.service.SearchService;

import cn.itcast.common.page.Pagination;

@Controller
public class ProductController {
	@Autowired
	private SearchService searchService;
	@Autowired
	private CmsService cmsService;
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
	@RequestMapping("/product/detail")
	public String detail(Long id,Model model) {
		Product product = cmsService.findProductById(id);
		List<Sku> skuList = cmsService.findSkuListByProductId(id);
		model.addAttribute("product", product);
		model.addAttribute("skuList", skuList);
		
		Set<Color> colors=new HashSet<>();
		if(skuList!=null) {
			for (Sku sku : skuList) {
				Color color = sku.getColor();
				colors.add(color);
			}
		}
		model.addAttribute("colors", colors);
		
		return "product";
		
	}
}

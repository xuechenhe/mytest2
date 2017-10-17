package com.xuechenhe.ssm.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xuechenhe.ssm.pojo.product.Sku;
import com.xuechenhe.ssm.service.SkuService;

@Controller
@RequestMapping("sku")
public class SkuController {
	@Autowired
	private SkuService skuService;
	@RequestMapping("list")
	public String list(Long productId,Model model) {
		List<Sku> skuList = skuService.findSkuList(productId);
		model.addAttribute("skuList", skuList);
		return "sku/list";
		
	}
	@RequestMapping("update")
	public void update(Sku sku,HttpServletResponse res) throws IOException {
		JSONObject jo = new JSONObject();
		try {
			skuService.updateSku(sku);
			jo.put("message", "保存成功");
		} catch (Exception e) {
			jo.put("message", "保存失败");
			e.printStackTrace();
		}
		
		res.setContentType("application/json;charset=utf-8");
		res.getWriter().write(jo.toString());
		
		
	}
}

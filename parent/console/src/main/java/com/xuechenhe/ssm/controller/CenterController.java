package com.xuechenhe.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("console")
public class CenterController {
	@RequestMapping("index")
	public String index() {
		return "index";
		
	}
	@RequestMapping("top")
	public String top() {
		return "top";
		
	}
	@RequestMapping("main")
	public String main() {
		return "main";
		
	}
	@RequestMapping("left")
	public String left() {
		return "left";
		
	}
	@RequestMapping("right")
	public String right() {
		return "right";
		
	}
	@RequestMapping("frame/productMain")
	public  String productMain() {
		return "frame/product_main";
		
	}
	@RequestMapping("frame/productLeft")
	public  String productLeft() {
		return "frame/product_left";
		
	}
	@RequestMapping("frame/adLeft")
	public  String adLeft() {
		return "frame/ad_left";
		
	}
	@RequestMapping("/frame/adMain")
	public  String adMain() {
		return "frame/ad_main";
		
	}
}

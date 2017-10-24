package com.xuechenhe.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xuechenhe.ssm.pojo.ad.Ad;
import com.xuechenhe.ssm.pojo.ad.Position;
import com.xuechenhe.ssm.service.AdService;
import com.xuechenhe.ssm.service.PositionService;

@Controller
@RequestMapping("ad")
public class AdController {
	@Autowired
	private AdService adService;
	@Autowired
	private PositionService positionService;

	@RequestMapping("list")
	public String list(String root, Model model) {
		List<Ad> list = null;

		list = adService.findAdListByParentId(Long.parseLong(root));
		model.addAttribute("list", list);
		model.addAttribute("positionId", root);
		return "ad/list";

	}
	@RequestMapping("toAdd")
	public String toAdd(Long positionId , Model model) {
		Position position = positionService.findPositionByPositionId(positionId);
		model.addAttribute("position", position);
		model.addAttribute("positionId", positionId);
		
		return "ad/add";
		
	}
	@RequestMapping("add")
	public String add(Ad ad,String positionId) {
		adService.insertAd(ad);
		return "redirect:/ad/list.action?root="+positionId;
		
	}
	

}

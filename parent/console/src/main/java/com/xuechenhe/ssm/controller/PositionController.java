package com.xuechenhe.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xuechenhe.ssm.pojo.ad.Position;
import com.xuechenhe.ssm.service.PositionService;

/**
 * 广告位维护
 *
 */
@Controller
@RequestMapping("/position")
public class PositionController {
	
	@Autowired
	private PositionService positionService;

	/**
	 * 获取树形菜单的数据
	 * 前端treeView插件, 在第一次获取数据的时候, 因为root为空所以传过来一个字符串叫做source, 如果我们接收到的是一个字符串source则认为是根节点.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/tree")
	public String tree(String root, Model model) throws Exception {
		List<Position> list = null;
		if("source".equals(root)){
			//根节点
			list = positionService.findPositionListByParentId(0L);
		} else {
			//根据当前节点id获取子节点
			list = positionService.findPositionListByParentId(Long.parseLong(root));
		}
		model.addAttribute("list", list);
		return "position/tree";
	}
	
	/**
	 * 加载广告位置列表
	 * @param root
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(String root, Model model) throws Exception {
		List<Position> list = null;
		if("source".equals(root)){
			//根节点
			list = positionService.findPositionListByParentId(0L);
		} else {
			//根据当前节点id获取子节点
			list = positionService.findPositionListByParentId(Long.parseLong(root));
		}
		model.addAttribute("list", list);
		return "position/list";
	}
}
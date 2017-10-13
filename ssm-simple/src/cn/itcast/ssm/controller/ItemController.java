package cn.itcast.ssm.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.ssm.po.Items;
import cn.itcast.ssm.po.QueryVo;
import cn.itcast.ssm.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 查询所有商品
	 * @return
	 */
	@RequestMapping("/itemList")
	public ModelAndView queryItemList() {
		//获取商品数据
		List<Items> list = this.itemService.queryItemList();
		
		ModelAndView view = new ModelAndView();
		//把商品数据放到模型中
		view.addObject("itemList", list);
		//设置逻辑视图
		view.setViewName("itemList");
		return view;
	}
	
	/**
	 * 根据id查询商品
	 * @param request
	 * @return
	 */
	/*@RequestMapping("/itemEdit")
	public ModelAndView queryItemById(HttpServletRequest request) {
		//从request中获取请求参数
		String strId = request.getParameter("id");
		int id = Integer.parseInt(strId);
		
		Items items = this.itemService.queryItemById(id);
		
		ModelAndView view = new ModelAndView();
		
		view.addObject("item",items);
		view.setViewName("editItem");
		return view;
	}*/
	/**
	 * 根据id查询商品，使用model
	 * @param request
	 * @param model
	 * @return
	 */
	/*@RequestMapping("/itemEdit")
	public String queryItemById(HttpServletRequest request,Model model) {
		//从request中获取请求参数
		String strId = request.getParameter("id");
		int id = Integer.valueOf(strId);
		
		Items items = this.itemService.queryItemById(id);
		model.addAttribute("item", items);
		return "editItem";
	}*/
	
	/**
	 * 根据id查询商品，绑定简单数据类型
	 * 当请求的参数和处理器形参名称一致时会将请求参数与形参进行绑定
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/itemEdit")
	public String queryItemById(int id,ModelMap model) {
		//
		Items items = this.itemService.queryItemById(id);
		//把商品放在模型中
		model.addAttribute("item", items);
		return "editItem";
		
	}
	@RequestMapping("/updateitem")
	public  String updateItem(Items item) {
		//调用服务更新商品
		this.itemService.updateItemById(item);
		return "success";
		
	}
	@RequestMapping("/item/queryitem")
	public String queryItem(QueryVo vo) {
		System.out.println(vo.getItem().getId());
		System.out.println(vo.getItem().getName());
		return "success";
		
	}
}

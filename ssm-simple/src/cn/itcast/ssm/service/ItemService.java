package cn.itcast.ssm.service;

import java.util.List;

import cn.itcast.ssm.po.Items;

public interface ItemService {
	
	/**
	 * 查询商品列表
	 * @return
	 */
	List<Items> queryItemList();
	/**
	 * 根据商品id查询商品
	 * @param id
	 * @return
	 */
	Items queryItemById(int id);
	/**
	 * 根据id更新商品
	 * @param id
	 */
	void updateItemById(Items item);
}

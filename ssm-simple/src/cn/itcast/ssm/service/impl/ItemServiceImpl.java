package cn.itcast.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.ssm.mapper.ItemsMapper;
import cn.itcast.ssm.po.Items;
import cn.itcast.ssm.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemsMapper itemMapper;
	@Override
	public List<Items> queryItemList() {
		//从数据库查询商品数据
		List<Items> list = this.itemMapper.selectByExampleWithBLOBs(null);
		return list;
	}
	@Override
	public Items queryItemById(int id) {
		Items items = this.itemMapper.selectByPrimaryKey(id);
		return items;
	}
	@Override
	public void updateItemById(Items item) {
		this.itemMapper.updateByPrimaryKeySelective(item);
		
	}

}

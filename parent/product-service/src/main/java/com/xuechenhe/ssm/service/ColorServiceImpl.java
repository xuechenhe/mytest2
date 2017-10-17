package com.xuechenhe.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuechenhe.ssm.dao.product.ColorDao;
import com.xuechenhe.ssm.pojo.product.Color;
import com.xuechenhe.ssm.pojo.product.ColorQuery;
import com.xuechenhe.ssm.pojo.product.ColorQuery.Criteria;

@Service("colorServiceImpl")
@Transactional
public class ColorServiceImpl implements ColorService{
	@Autowired
	private ColorDao colorDao;

	@Override
	public List<Color> findColorList() {
		ColorQuery cq=new ColorQuery();
		Criteria criteria = cq.createCriteria();
		criteria.andParentIdNotEqualTo(0L);//不查询色系只查询颜色
		List<Color> list = colorDao.selectByExample(cq);
		return list;
	}
	
}

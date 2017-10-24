package com.xuechenhe.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuechenhe.ssm.dao.ad.PositionDao;
import com.xuechenhe.ssm.pojo.ad.Position;
import com.xuechenhe.ssm.pojo.ad.PositionQuery;
import com.xuechenhe.ssm.pojo.ad.PositionQuery.Criteria;

@Service("positionServiceImpl")
@Transactional
public class PositionServiceImpl implements PositionService {
	@Autowired
	private PositionDao positionDao;
	@Override
	public List<Position> findPositionListByParentId(Long parentId) {
		PositionQuery positionQuery = new PositionQuery();
		//按照sort升序排序
		positionQuery.setOrderByClause("sort");
		Criteria createCriteria = positionQuery.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<Position> list = positionDao.selectByExample(positionQuery);
		return list;
	}
	@Override
	public Position findPositionByPositionId(Long positionId) {
		Position position = positionDao.selectByPrimaryKey(positionId);
		return position;
	}

}

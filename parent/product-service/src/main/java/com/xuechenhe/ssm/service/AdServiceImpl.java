package com.xuechenhe.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuechenhe.ssm.common.Constants;
import com.xuechenhe.ssm.common.JsonUtil;
import com.xuechenhe.ssm.dao.ad.AdDao;
import com.xuechenhe.ssm.dao.ad.PositionDao;
import com.xuechenhe.ssm.pojo.ad.Ad;
import com.xuechenhe.ssm.pojo.ad.AdQuery;
import com.xuechenhe.ssm.pojo.ad.AdQuery.Criteria;
import com.xuechenhe.ssm.pojo.ad.Position;

import redis.clients.jedis.Jedis;

@Service("adServiceImpl")
@Transactional
public class AdServiceImpl implements AdService {
	@Autowired
	private AdDao adDao;
	@Autowired
	private PositionDao positionDao;
	@Autowired
	private Jedis jedis;
	@Override
	public List<Ad> findAdListByParentId(Long parentId) {
		AdQuery AdQuery = new AdQuery();
		
		Criteria createCriteria = AdQuery.createCriteria();
		createCriteria.andPositionIdEqualTo(parentId);
		List<Ad> list = adDao.selectByExample(AdQuery);
		
		if(list!=null) {
			for (Ad ad : list) {
				Position position = positionDao.selectByPrimaryKey(ad.getPositionId());
				ad.setPosition(position);
			}
		}
		return list;
	}
	@Override
	public void insertAd(Ad ad) {
		adDao.insert(ad);
		
	}
	@Override
	public String findAdListFromRedis() throws Exception {
		String str = jedis.get(Constants.AD_KEY);
		if(str!=null && !"".equals(str)) {
			return str;
		}
		List<Ad> list = this.findAdListByParentId(89L);
		str=JsonUtil.obj2JsonStr(list);
		jedis.set(Constants.AD_KEY, str);
		return str;
	}

}

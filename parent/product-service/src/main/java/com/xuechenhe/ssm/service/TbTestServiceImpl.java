package com.xuechenhe.ssm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuechenhe.ssm.dao.TbTestMapper;
import com.xuechenhe.ssm.pojo.TbTest;
@Service("tbTestServiceImpl")//给实现类器别名相当于class全名类路径bean中的id方便dubbo  ref引用
@Transactional
public class TbTestServiceImpl implements TbTestService {
	@Autowired
	private TbTestMapper tbTestMapper;
	@Override
	public void insertTest(TbTest tb) {
		tbTestMapper.insertTest(tb);
		//throw new RuntimeException();
	}

}

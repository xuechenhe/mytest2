package com.xuechenhe.ssm.service;

import java.util.List;

import com.xuechenhe.ssm.pojo.ad.Position;

public interface PositionService {
	public  abstract List<Position>  findPositionListByParentId(Long parentId);
	
	public abstract Position findPositionByPositionId(Long positionId);

}

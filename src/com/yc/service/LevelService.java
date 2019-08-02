package com.yc.service;

import java.util.List;

import com.yc.bean.TblLevel;
import com.yc.dao.LevelDao;

public class LevelService {
	private LevelDao ldao=new LevelDao();
	//查询所有等级列表
	public List<TblLevel> queryAllLevel() {
		return ldao.queryAllLevel();
	}
	//添加等级方法
	public void addLevel(TblLevel level) throws ServiceException {
		int res = ldao.addLevel(level);
		if(res <=0){
			throw new ServiceException("添加失败");
		}
	}
	//删除等级方法
	public void deleteLevel(int lid) throws ServiceException {
		int res = ldao.deleteLevel(lid);
		if(res<=0){
			throw new ServiceException("删除失败");
		}
	}
	//修改
	public void mofifyLevel(TblLevel level) throws ServiceException {
		int res = ldao.mofifyLevel(level);
		if(res<=0){
			throw new ServiceException("修改失败");
		}
	}

}

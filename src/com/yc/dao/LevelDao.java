package com.yc.dao;

import java.util.List;

import com.yc.bean.TblLevel;
import com.yc.utils.DBUtil;

public class LevelDao {

	//查询所有等级列表
	public List<TblLevel> queryAllLevel() {
		String sql="select * from tbl_level";
		return DBUtil.list(TblLevel.class, sql);
	}
	//添加等级方法
	public int addLevel(TblLevel level) {
		String sql="insert into tbl_level values(null,?,?,?,?)";
		return DBUtil.doUpdate(sql, level.getLname(),level.getLtime(),level.getLtopicnum(),level.getLreplynum());
	}
	//deleteLevel
	public int deleteLevel(int lid) {
		String sql="delete FROM  tbl_level where lid=?";
		return DBUtil.doUpdate(sql, lid);
	}
	//
	public int mofifyLevel(TblLevel level) {
		String sql="update tbl_level set lname=?,ltime=?,ltopicnum=?,lreplynum=? where lid=?";
		return DBUtil.doUpdate(sql, level.getLname(),level.getLtime(),level.getLtopicnum(),level.getLreplynum(),level.getLid());
	}

}

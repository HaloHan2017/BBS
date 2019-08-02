package com.yc.dao;

import java.util.List;

import com.yc.bean.TblAdmin;
import com.yc.utils.DBUtil;

public class AdminDao {

	public TblAdmin adminLogin(TblAdmin a) {
		String sql="select * from tbl_admin where aname=? and apass=?";
		return DBUtil.get(TblAdmin.class, sql, a.getAname(),a.getApass());
		
	}

}

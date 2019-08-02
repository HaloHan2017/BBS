package com.yc.dao;

import java.util.List;
import java.util.Map;

import com.yc.utils.DBUtil;

public class HotDao {
	
	public List<Map<String,Object>> findIndex(){
		String sql ="SELECT * FROM"+
				"(SELECT a.boardname pname,b.boardname cname,c.cnt,b.boardid bid,d.*,e.uname,g.coun coun"+
						" FROM tbl_board a LEFT JOIN tbl_board b "+
						" ON a.boardid=b.parentid LEFT JOIN (SELECT boardid,COUNT(*) cnt FROM tbl_topic GROUP BY boardid) c "+
						" ON b.boardid = c.boardid LEFT JOIN(SELECT * FROM tbl_topic a LEFT JOIN (SELECT MAX(topicid) maxid FROM tbl_topic GROUP BY boardid) f"+
						" ON a.topicid = f.maxid ) d ON b.boardid = d.boardid "+
						" LEFT JOIN (SELECT topicid,COUNT(*) coun FROM tbl_reply GROUP BY topicid) g ON g.topicid = d.topicid "+
						" LEFT JOIN tbl_user e ON d.uid = e.uid WHERE a.parentid=0 AND g.coun IS NOT NULL) a ORDER BY coun DESC";
		return DBUtil.list(sql);
	}
	
	public List<Map<String,Object>> findUser(){
		String sql = "SELECT * FROM tbl_user a RIGHT JOIN"+
				" (SELECT uid,COUNT(*) coun FROM tbl_topic GROUP BY uid) b"+ 
				" ON a.uid=b.uid ORDER BY coun desc LIMIT 0,3";
		return DBUtil.list(sql);
	}
}

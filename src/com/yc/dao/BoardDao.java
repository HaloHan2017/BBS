package com.yc.dao;

import java.util.List;
import java.util.Map;

import com.yc.utils.DBUtil;

public class BoardDao {
	
	public List<Map<String,Object>> showIndexData(){
		String sql=
				"SELECT\n" +
						"	*\n" +
						"FROM\n" +
						"	(\n" +
						"		SELECT\n" +
						"			a.boardname pname,\n" +
						"			b.boardid as bid,\n" +
						"			b.boardname cname,\n" +
						"			c.ctn,\n" +
						"			d.*, e.uname\n" +
						"		FROM\n" +
						"			tbl_board a\n" +
						"		LEFT JOIN tbl_board b ON a.boardid = b.parentid\n" +
						"		LEFT JOIN (\n" +
						"			SELECT\n" +
						"				b.boardid,\n" +
						"				count(t.topicid) ctn\n" +
						"			FROM\n" +
						"				tbl_board b\n" +
						"			LEFT JOIN tbl_topic t ON b.boardid = t.boardid\n" +
						"			GROUP BY\n" +
						"				b.boardid\n" +
						"			ORDER BY\n" +
						"				b.boardid\n" +
						"		) c ON b.boardid = c.boardid\n" +
						"		LEFT JOIN (\n" +
						"			SELECT\n" +
						"				*\n" +
						"			FROM\n" +
						"				tbl_topic a\n" +
						"			JOIN (\n" +
						"				SELECT\n" +
						"					MAX(topicid) maxid\n" +
						"				FROM\n" +
						"					tbl_topic\n" +
						"				GROUP BY\n" +
						"					boardid\n" +
						"			) b ON a.topicid = b.maxid\n" +
						"		) d ON b.boardid = d.boardid\n" +
						"		LEFT JOIN tbl_user e ON d.uid = e.uid\n" +
						"		WHERE\n" +
						"			a.parentid = 0\n" +
						"	) a";

		return DBUtil.list(sql);
			
	}
}

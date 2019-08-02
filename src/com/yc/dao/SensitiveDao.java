package com.yc.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.yc.bean.PageBean;
import com.yc.bean.TblSWord;
import com.yc.utils.DBUtil;

public class SensitiveDao {
	private File file = new File("E:/eclipse/works_j2ee/bbs_class/src/SensitiveWords.txt");
	public List<TblSWord> query(PageBean pageBean, String sname){
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	tbl_sword\n" +
				"WHERE\n" +
				"	sstate = 1\n" +
				"AND sname LIKE ?\n" +
				"ORDER BY\n" +
				"	saddtime DESC\n" +
				"LIMIT ?,\n" +
				" ?";//sstate=1 代表该敏感词未被删除，正在使用
		List<TblSWord> swordlist = DBUtil.list(TblSWord.class,sql,"%"+sname+"%",
				(pageBean.getPage()-1)*pageBean.getRows(),pageBean.getRows());
		return swordlist;
	}
	
	//添加敏感词
	public int addWord(String word)  {
		//将敏感词写进文本里
		try {
			List<String> lines = FileUtils.readLines(file, "UTF-8");
			lines.add(word);
			FileUtils.writeLines(file, lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String sql="insert into tbl_sword values(null,?,now(),1)";
		return DBUtil.doUpdate(sql, word);
	}

	public int delWord(Integer sid, String sname)  {
		//将敏感词从文本里删除
		try {
			List<String> lines = FileUtils.readLines(file, "UTF-8");
			for (String s : lines) {
				if(s.equals(sname)){
					lines.remove(s);
					break;
				}
			}
			lines.remove(sid);
			FileUtils.writeLines(file, lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String sql="update tbl_sword set sstate=0 where sid=?";//将状态设置=0，表示已删除
		return DBUtil.doUpdate(sql, sid);
	}

	
	
	//获取总记录数 ---根据参数表示是  全查询  还是模糊查询
	public int getTotalCount(String sname){
		String sql="select count(*) as ctn from tbl_sword where sname like ?";
		Map<String, Object> map = DBUtil.get(sql,"%"+sname+"%");
		Long total = (Long) map.get("ctn");
		return total.intValue();
		
	}
}

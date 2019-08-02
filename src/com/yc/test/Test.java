package com.yc.test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.yc.bean.TblUser;
import com.yc.utils.DBUtil;

public class Test {
	public static void main(String[] args) {
//		String sql="select * from tbl_user where uid=4";
//		List<TblUser> list = DBUtil.list(TblUser.class, sql);
//		System.out.println(list.get(0));
		
//		String sql="select swcount from tbl_user where uid=?";
//		int num = Integer.parseInt(DBUtil.get(sql,1).get("swcount").toString());
//		boolean f = (num>=3) ?true:false;
//		System.out.println(f);
		//System.out.println(DBUtil.get(sql, 8).get("swcount").toString());
		try {
			
			List<String> list = FileUtils.readLines(
					new File("E:/eclipse/works_j2ee/bbs_class/src/SensitiveWords.txt"), "UTF-8");
			for(String s : list){
				String sql="insert into tbl_sword values(null,?,now(),1)";
				DBUtil.doUpdate(sql, s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package com.yc.service;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.yc.bean.PageBean;
import com.yc.bean.TblSWord;
import com.yc.dao.SensitiveDao;

public class SensitiveService {
	private SensitiveDao sdao=new SensitiveDao();
	
	//查询所有数据
	public String query(PageBean pageBean, String sname ) {
		List<TblSWord> swList = sdao.query(pageBean,sname);
		return pack(sname,swList);
	}
	//查询敏感词

	//将数据封装成json
	private String pack(String sname,List<TblSWord> list){
		String rtn="";
		//获取总记录数
		int total = sdao.getTotalCount(sname);//不带参数
		if(total>0){
			//获取分页查询的list
			String json = JSON.toJSONString(list);
			//利用转义字符转成JSON格式的语句
            rtn="{\"total\":"+total+",\"rows\":"+json+"}";
		}
		return rtn;
	}
	
	
	//添加敏感词
	public void addWord(String word) throws ServiceException {
		
		if("".equals(word.trim())){
			throw new ServiceException("敏感词不能为空");
		}
		int res = sdao.addWord(word);
		if(res<=0){
			throw new ServiceException("添加敏感词失败");
		}
	}
	
	public void delWord(Integer sid, String sname) throws ServiceException {
		int res = sdao.delWord(sid,sname);
		if(res<=0){
			throw new ServiceException("添加敏感词失败");
		}
	}
	
	//获取总记录数
	public int getTotalCount(String sname) {
		return sdao.getTotalCount(sname);
	}

}

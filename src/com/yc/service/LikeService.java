package com.yc.service;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import com.yc.bean.LikeTopic;
import com.yc.dao.LikeDao;

public class LikeService {
	private LikeDao ld=new LikeDao();

	public void findid(LikeTopic like) {
		if(ld.find(like.getTopicid())!=null){
			ld.del(like.getTopicid());
		}else{
			Timestamp time = new Timestamp(System.currentTimeMillis());
			like.setAddtime(time);
			ld.addlike(like);
		}
	}
		
}

package com.yc.dao;

import java.util.List;
import java.util.Map;

import com.yc.bean.LikeTopic;
import com.yc.utils.DBUtil;
import com.yc.utils.MyUtils;

public class LikeDao {
	private DBUtil db = new DBUtil();
	public int addlike(LikeTopic like){
		String sql = "INSERT INTO like_topic VALUES(NULL,?,?,?)";
		return db.doUpdate(sql, like.getTopicid(),like.getUid(),like.getAddtime());
	}
	public List<Map<String,Object>> find(int topicid){
		String sql = "SELECT * FROM like_topic WHERE topicid=?";
		return db.list(sql, topicid);
	}
	public int del(int topicid){
		String sql = "DELETE FROM like_topic WHERE topicid=?";
		return db.doUpdate(sql, topicid);
	}
	public LikeTopic index(LikeTopic like){
		String sql = "SELECT * FROM like_topic WHERE topicid = ? and uid = ?";
		return db.get(LikeTopic.class, sql, like.getTopicid(),like.getUid());
	}
	
}

package com.yc.service;

import com.yc.bean.TblReply;
import com.yc.bean.TblTopic;

import redis.clients.jedis.Jedis;

public class ReplyService {
	private static final String IP="47.100.123.185";
	private static final String PWD="tys";
		
	//帖子点赞
	public Long glkreply(TblReply reply){
		Jedis jedis = new Jedis(IP,6379);
		jedis.auth(PWD);
		//判断redis中是否有1 是否在键topic1中
		
		if(jedis.sismember("topic"+reply.getTopicid(),reply.getUid()+"")==false){
			jedis.sadd("topic"+reply.getTopicid(),reply.getUid()+"");
			
		}else{
			jedis.smove("topic"+reply.getTopicid(),"trash",reply.getUid()+"");
		}
		Long glk = jedis.scard("topic"+reply.getTopicid());
		return glk;
	}
	
	//获取点赞数
	public Long gettimes(TblReply reply){
		Jedis jedis = new Jedis(IP,6379);
		jedis.auth(PWD);
		Long glk = jedis.scard("topic"+reply.getTopicid());
		return glk;
	}
	
	public Long num(TblReply reply){
		Jedis jedis = new Jedis(IP,6379);
		jedis.auth(PWD);
		//判断redis中是否有1 是否在键topic1中
		
		if(jedis.sismember("topic"+reply.getReplyid(),reply.getUid()+"")==false){
			jedis.sadd("topic"+reply.getReplyid(),reply.getUid()+"");
			
		}else{
			jedis.smove("topic"+reply.getReplyid(),"trash",reply.getUid()+"");
		}
		Long glk = jedis.scard("topic"+reply.getReplyid());
		return glk;
	}
	
	//回帖的点赞数查询
	public Long counts(String replyid){
		Jedis jedis = new Jedis(IP,6379);
		jedis.auth(PWD);
		Long count = jedis.scard("topic"+replyid);
		return count;
	}
}

package com.yc.service;

import com.yc.bean.TblReply;
import com.yc.bean.TblTopic;

import redis.clients.jedis.Jedis;

public class ReplyService {
	
	//帖子点赞
	public Long glkreply(TblReply reply){
		Jedis jedis = new Jedis("localhost",6379);
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
		Jedis jedis = new Jedis("localhost",6379);
		Long glk = jedis.scard("topic"+reply.getTopicid());
		return glk;
	}
	
	public Long num(TblReply reply){
		Jedis jedis = new Jedis("localhost",6379);
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
		Jedis jedis = new Jedis("localhost",6379);
		Long count = jedis.scard("topic"+replyid);
		return count;
	}
}

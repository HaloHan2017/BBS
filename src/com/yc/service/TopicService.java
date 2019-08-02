package com.yc.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.yc.bean.TblLevel;
import com.yc.bean.TblReply;
import com.yc.bean.TblTopic;
import com.yc.bean.TblUser;
import com.yc.dao.TopicDao;
import com.yc.dao.UserDao;
import com.yc.utils.MailUtils;
import com.yc.utils.SensitiveWordsUtils;


public class TopicService {
	private TopicDao tdao=new TopicDao();
	private UserDao udao=new UserDao();
	//根据boardid查找所有topic列表
	public List<Map<String, Object>> findAllTopicList(int boardid) {
		return tdao.findAllTopicList(boardid);
	}
	
	//创建新帖子
	public String createNewTopic(TblTopic topic) throws ServiceException {
		//定义一个标志，用于判断用户是否发布包含敏感词的帖子
		String flag="no";
		
		String content = topic.getContent();
		String title = topic.getTitle();
		
		//判断内容  标题是否包含敏感词
		//filterString(content,title);
		if(!SensitiveWordsUtils.isContainsSensitiveWords(topic.getContent())){
			//过滤内容
			topic.setContent(SensitiveWordsUtils.filterSensitiveWords(topic.getContent()));
		}
		//判断标题是否包含敏感词
		if(!SensitiveWordsUtils.isContainsSensitiveWords(topic.getTitle())){
			//过滤标题
			topic.setContent(SensitiveWordsUtils.filterSensitiveWords(topic.getTitle()));
		}
		//设置 违规发言次数+1(判断是否topic内容，标题发生了变化)
		if(!content.equals(topic.getContent()) || !title.equals(topic.getTitle())){
			//将用户的附加属性  违规发言次数 +1
			//根据uid查找该用户
			List<TblUser> findUserById = udao.findUserById(topic.getUid());
			TblUser user=findUserById.get(0);
			user.setSwcount(user.getSwcount()+1);
			//要将swcount的值写到数据库里
			tdao.addSwcount(user);
			
			//用户发布不好的贴子  flag值变为yes
			flag="yes";
		}
		// 判断输入内容是否合适
		if(topic.getTitle().trim().length()<=2){
			throw new ServiceException("标题不能少于2个字符！");
		}
		if(topic.getContent().trim().length()<=10){
			throw new ServiceException("内容不能少于10个字符！");
		}
		Timestamp time = new Timestamp(System.currentTimeMillis());
		//把创建时间，修改时间  添加到topic对象中
		topic.setPublishtime(time);
		topic.setModifytime(time);
		
		//调用dao层的添加帖子方法
		tdao.createNewTopic(topic);
		
		//发完帖子之后，判断用户的的 等级数是否可以提升
		//increLevel(topic.getUid());
		
		return flag;
	}
	//判断用户的的 等级数是否可以提升
	private void increLevel(Integer uid) {
		//获取用户的 等级数  注册时长  帖子数 回复数  和下一等级的信息   进行比对
		TblLevel userlevel=tdao.getLevelInfoByUid(uid);
		//若满足条件，则进行等级提升
		
	}

	//根据id查到topic
	public TblTopic findTopicById(Integer topicid) {
		return tdao.findTopicById(topicid);
	}
	
	//根据topicid查到对应的reply集合
	public List<TblReply> findReplyListByTopicid(Integer topicid) {
		return tdao.findReplyListByTopicid(topicid);
	}
	
	//添加回复方法
	public String createNewReply(TblReply reply) throws ServiceException {
		//定义一个标志，用于判断用户是否发布包含敏感词的帖子
		String flag="no";
				
		String content = reply.getContent();
		String title = reply.getTitle();
		//判断内容是否包含敏感词
		if(!SensitiveWordsUtils.isContainsSensitiveWords(reply.getContent())){
			//过滤内容
			reply.setContent(SensitiveWordsUtils.filterSensitiveWords(reply.getContent()));
		}
		//判断标题是否包含敏感词   false代表包含
		if(!SensitiveWordsUtils.isContainsSensitiveWords(reply.getTitle())){
			//过滤标题
			reply.setTitle(SensitiveWordsUtils.filterSensitiveWords(reply.getTitle()));
		}
		//设置 违规发言次数+1(判断是否topic内容，标题发生了变化)
		if(!content.equals(reply.getContent()) || !title.equals(reply.getTitle())){
			List<TblUser> findUserById = udao.findUserById(reply.getUid());
			TblUser user=findUserById.get(0);
			user.setSwcount(user.getSwcount()+1);
			//要将swcount的值写到数据库里
			tdao.addSwcount(user);
			
			//用户发布不好的贴子  flag值变为yes
			flag="yes";
		}
		// 判断输入内容是否合适
		if(reply.getTitle().trim().length()<=2){
			throw new ServiceException("标题不能少于2个字符！");
		}
		if(reply.getContent().trim().length()<=10){
			throw new ServiceException("内容不能少于10个字符！");
		}
		
		//调用dao层的添加回复犯法
		tdao.createNewReply(reply);
		
		return flag;
	}
	

	//删除回复
	public void deleteReplyById(Integer replyid) {
		tdao.deleteReplyById(replyid);
	}

	public List<TblTopic> queryAllTopic() {
		return tdao.queryAllTopic();
	}
	
	public boolean isUserCanSpeak(Integer uid){
		//根据用户id查找到对应的客户
		List<TblUser> findUserById = udao.findUserById(uid);
		TblUser user=findUserById.get(0);
		Integer count=user.getSwcount();
//		Integer count = tdao.getBadSpeakCount(uid);
		return count>=3 ? false:true;	//大于等于3，表示不能发言了，返回false
	}
	
	//判断标题  和  内容 是否包含敏感词
//	private String filterString(String s1, String s2){
//		String s1_tp=s1;
//		String s2_tp=s2;
//		if(!SensitiveWordsUtils.isContainsSensitiveWords(s1) || !SensitiveWordsUtils.isContainsSensitiveWords(s2)){
//			//包含敏感词  则进行过滤
//			
//		}else{
//			//未包含
//		}
//		return list.;
//	}
}

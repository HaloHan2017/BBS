package com.yc.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yc.bean.TblLevel;
import com.yc.bean.TblReply;
import com.yc.bean.TblTopic;
import com.yc.bean.TblUser;
import com.yc.utils.DBUtil;

public class TopicDao {
	
	//根据boardid查找所有topic列表
	public List<Map<String, Object>> findAllTopicList(int boardid) {
		String sql=
				"select * from(\n" +
				"	(\n" +
				"		select t.topicid as topicid,t.boardid,t.title,u.uname from tbl_topic t INNER JOIN tbl_user u\n" +
				"		on t.uid=u.uid\n" +
				"	)a LEFT JOIN \n" +
				"	(\n" +
				"		select t.topicid as tid,COUNT(r.replyid) as ctn from tbl_topic t left JOIN tbl_reply r\n" +
				"		on t.topicid=r.topicid\n" +
				"		GROUP BY tid\n" +
				"		ORDER BY ctn\n" +
				"	)b on a.topicid=b.tid\n" +
				")\n" +
				"where a.boardid=?";

		return DBUtil.list(sql, boardid);
	}
	
	//创建新帖子
	public void createNewTopic(TblTopic topic) {
		String sql="insert into tbl_topic values(null,?,?,?,?,?,?)";
		DBUtil.doUpdate(sql, topic.getTitle()
				,topic.getContent()
				,topic.getPublishtime()
				,topic.getModifytime()
				,topic.getUid()
				,topic.getBoardid());
	}

	//根据id查到topic
	public TblTopic findTopicById(Integer topicid) {
		String sql="select t.*,u.uname,u.regtime,u.head from tbl_topic t,tbl_user u where topicid=? and t.uid=u.uid";
		return DBUtil.get(TblTopic.class, sql,topicid);
	}
	
	//根据topicid查到对应的reply集合
	public List<TblReply> findReplyListByTopicid(Integer topicid) {
		String sql="select t.*,u.uname,u.regtime,u.head from tbl_reply t,tbl_user u where topicid=? and t.uid=u.uid";
		return DBUtil.list(TblReply.class, sql,topicid);
	}
	
	//调用dao层的添加回复犯法
	public void createNewReply(TblReply reply) {
		String sql="insert into tbl_reply values(null,?,?,?,?,?,?)";
		DBUtil.doUpdate(sql,reply.getTitle()
				,reply.getContent()
				,reply.getPublishtime()
				,reply.getModifytime()
				,reply.getUid()
				,reply.getTopicid());
	}

	public void deleteReplyById(Integer replyid) {
		String sql="delete from tbl_reply where replyid=?";
		DBUtil.doUpdate(sql, replyid);
	}

	public List<TblTopic> queryAllTopic() {
		String sql="select * from tbl_topic";
		return DBUtil.list(TblTopic.class, sql);
	}
	
	/*//添加违规记录方法
	public void BadSpeak(Integer uid) {
		  String sql="INSERT into tbl_sw values(null,?,NOW())";
		  DBUtil.doUpdate(sql, uid);
	}*/

	public Integer getBadSpeakCount(Integer uid) {
		String sql="SELECT\n" +
				"	uid,\n" +
				"	count(uid) ctn\n" +
				"FROM\n" +
				"	tbl_sw\n" +
				"where uid=?\n" +
				"GROUP BY\n" +
				"	uid\n" +
				"ORDER BY\n" +
				"	uid";
		List<Map<String, Object>> list = DBUtil.list(sql, uid);
		return Integer.parseInt(list.get(0).get("ctn").toString());
	}
	
	//违规发言次数+1
	public void addSwcount(TblUser user) {
		String sql="update tbl_user set swcount=? where uid=?";
		DBUtil.doUpdate(sql, user.getSwcount(),user.getUid());
	}

	//查询用户对应的等级信息
	public TblLevel getLevelInfoByUid(Integer uid) {
		StringBuffer sql=new StringBuffer();
		
		sql.append("SELECT\n");
		sql.append("	ltime,\n");
		sql.append("	ltopicnum,\n");
		sql.append("	lreplynum\n");
		sql.append("FROM\n");
		sql.append("	tbl_level\n");
		sql.append("WHERE\n");
		sql.append("	tbl_level.lid-1 = (select lid from tbl_user where uid=?)");
		return DBUtil.get(TblLevel.class,sql.toString(), uid);
	}
	
	//根据用户id查询 发帖数
	public Integer getUserTopicNumByID(int uid) {
		StringBuffer sql=new StringBuffer("SELECT count(tbl_topic.topicid)as topicNum\n");
		sql.append("from tbl_user\n");
		sql.append("left join tbl_topic \n");
		sql.append("on tbl_user.uid=tbl_topic.uid \n");
		sql.append("where tbl_user.uid=? \n");
		Map<String, Object> map = DBUtil.get(sql.toString(), uid);
		Long topicNum = (Long) map.get("topicnum");
		return topicNum.intValue();
	}
	//根据用户id查询 回帖数
	public Integer getUserReplyNumByID(int uid) {
		StringBuffer sql=new StringBuffer("SELECT count(tbl_reply.topicid)as replyNum\n");
		sql.append("from tbl_user\n");
		sql.append("left join tbl_reply \n");
		sql.append("on tbl_user.uid=tbl_reply.uid \n");
		sql.append("where tbl_user.uid=? \n");
		Map<String, Object> map = DBUtil.get(sql.toString(), uid);
		Long topicNum = (Long) map.get("replynum");
		return topicNum.intValue();
	}

}

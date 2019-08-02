package com.yc.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 主题板块类
 */

public class TblTopic implements Serializable{

	private static final long serialVersionUID = -8823994326164907487L;
	
	/*create table tbl_topic(
		topicid int primary key identity,
		title varchar(50),
		content varchar(1000),
		publishtime datetime,
		modifytime datetime,
		uid int,
		boardid int
	)*/
	
	private Integer topicid;
	private String title;
	private String content;
	private Timestamp publishtime;	//注意这里的类型是时间戳Timestamp，java.sql下的类
	private Timestamp modifytime;
	private Integer uid;
	private Integer boardid;
	
	//附加属性，不往数据库中显示和写入
	private String uname;
	private String head;
	private Timestamp regtime;
	
	
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public Timestamp getRegtime() {
		return regtime;
	}
	public void setRegtime(Timestamp regtime) {
		this.regtime = regtime;
	}
	public Integer getTopicid() {
		return topicid;
	}
	public void setTopicid(Integer topicid) {
		this.topicid = topicid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getPublishtime() {
		return publishtime;
	}
	public void setPublishtime(Timestamp publishtime) {
		this.publishtime = publishtime;
	}
	public Timestamp getModifytime() {
		return modifytime;
	}
	public void setModifytime(Timestamp modifytime) {
		this.modifytime = modifytime;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getBoardid() {
		return boardid;
	}
	public void setBoardid(Integer boardid) {
		this.boardid = boardid;
	}
	
	public TblTopic(Integer topicid, String title, String content, Timestamp publishtime, Timestamp modifytime,
			Integer uid, Integer boardid) {
		super();
		this.topicid = topicid;
		this.title = title;
		this.content = content;
		this.publishtime = publishtime;
		this.modifytime = modifytime;
		this.uid = uid;
		this.boardid = boardid;
	}
	public TblTopic() {
		super();
	}
	
	@Override
	public String toString() {
		return "TblTopic [topicid=" + topicid + ", title=" + title + ", content=" + content + ", publishtime="
				+ publishtime + ", modifytime=" + modifytime + ", uid=" + uid + ", boardid=" + boardid + "]";
	}
	
	

}

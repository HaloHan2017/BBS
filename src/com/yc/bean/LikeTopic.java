package com.yc.bean;

import java.sql.Timestamp;

public class LikeTopic {
	private int lid;
	private int topicid;
	private int uid;
	private Timestamp addtime;
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public int getTopicid() {
		return topicid;
	}
	public void setTopicid(int topicid) {
		this.topicid = topicid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public Timestamp getAddtime() {
		return addtime;
	}
	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}
	
	
}

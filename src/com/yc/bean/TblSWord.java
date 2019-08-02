package com.yc.bean;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * 敏感词实体
 * */
public class TblSWord implements Serializable {

	private static final long serialVersionUID = -7724385947717816042L;
	
	private Integer sid;
	private String sname;
	private Timestamp saddtime;
	private Integer sstate;
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public Timestamp getSaddtime() {
		return saddtime;
	}
	public void setSaddtime(Timestamp saddtime) {
		this.saddtime = saddtime;
	}
	public Integer getSstate() {
		return sstate;
	}
	public void setSstate(Integer sstate) {
		this.sstate = sstate;
	}
	
}

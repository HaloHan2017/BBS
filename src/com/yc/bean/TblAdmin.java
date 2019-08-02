package com.yc.bean;

import java.io.Serializable;

public class TblAdmin implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer aid;
	private String aname;
	private String apass;
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getApass() {
		return apass;
	}
	public void setApass(String apass) {
		this.apass = apass;
	}
	public TblAdmin(Integer aid, String aname, String apass) {
		super();
		this.aid = aid;
		this.aname = aname;
		this.apass = apass;
	}
	public TblAdmin() {
		super();
	}
	@Override
	public String toString() {
		return "TblAdmin [aid=" + aid + ", aname=" + aname + ", apass=" + apass + "]";
	}
	
	
	
}

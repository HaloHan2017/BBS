package com.yc.bean;

public class TblLevel {
	private Integer lid;
	private String lname;
	private Long ltime;
	private Integer ltopicnum;
	private Integer lreplynum;
	public Integer getLid() {
		return lid;
	}
	public void setLid(Integer lid) {
		this.lid = lid;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public Long getLtime() {
		return ltime;
	}
	public void setLtime(Long ltime) {
		this.ltime = ltime;
	}
	public Integer getLtopicnum() {
		return ltopicnum;
	}
	public void setLtopicnum(Integer ltopicnum) {
		this.ltopicnum = ltopicnum;
	}
	public Integer getLreplynum() {
		return lreplynum;
	}
	public void setLreplynum(Integer lreplynum) {
		this.lreplynum = lreplynum;
	}
	public TblLevel(Integer lid, String lname, Long ltime, Integer ltopicnum, Integer lreplynum) {
		super();
		this.lid = lid;
		this.lname = lname;
		this.ltime = ltime;
		this.ltopicnum = ltopicnum;
		this.lreplynum = lreplynum;
	}
	public TblLevel() {
		super();
	}
	@Override
	public String toString() {
		return "TblLevel [lid=" + lid + ", lname=" + lname + ", ltime=" + ltime + ", ltopicnum=" + ltopicnum
				+ ", lreplynum=" + lreplynum + "]";
	}
	
}

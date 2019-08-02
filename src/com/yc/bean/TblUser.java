package com.yc.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户实体类
 */

public class TblUser implements Serializable{
	
	private static final long serialVersionUID = 8288053065042297245L;
	
	/*create table tbl_user(
		uid int primary key identity,
		uname varchar(20),
		upass varchar(100),
		head varchar(100),
		regtime datetime,
		gender int
	)*/
	
	private Integer uid;
	private String uname;
	private String upass;
	private String head;
	private Timestamp regtime;	//注意这里的类型是时间戳Timestamp，java.sql下的类
	private Integer gender;
	private String email;
	private String phone;
	//等级id
	private Integer lid;
	//附加属性   违规发言次数
	private Integer swcount;
	private Integer sendEmial=0;//默认未发送
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUpass() {
		return upass;
	}
	public void setUpass(String upass) {
		this.upass = upass;
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
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getLid() {
		return lid;
	}
	public void setLid(Integer lid) {
		this.lid = lid;
	}
	public Integer getSwcount() {
		return swcount;
	}
	public void setSwcount(Integer swcount) {
		this.swcount = swcount;
	}
	public Integer getSendEmial() {
		return sendEmial;
	}
	public void setSendEmial(Integer sendEmial) {
		this.sendEmial = sendEmial;
	}
	@Override
	public String toString() {
		return "TblUser [uid=" + uid + ", uname=" + uname + ", upass=" + upass + ", head=" + head + ", regtime="
				+ regtime + ", gender=" + gender + ", email=" + email + ", phone=" + phone + ", lid=" + lid
				+ ", swcount=" + swcount + ", sendEmial=" + sendEmial + "]";
	}
	
	

}

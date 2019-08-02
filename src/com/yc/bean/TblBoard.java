package com.yc.bean;

import java.io.Serializable;

/**
 * 版块实体类
 */
public class TblBoard implements Serializable{
	
	private static final long serialVersionUID = 3327296684118950841L;
	/*create table tbl_board(
		boardid int primary key identity,
		boardname varchar(50),
		parentid int
	)*/
	
	private Integer boardid;
	private String boardname;
	private Integer parentid;
	public Integer getBoardid() {
		return boardid;
	}
	public void setBoardid(Integer boardid) {
		this.boardid = boardid;
	}
	public String getBoardname() {
		return boardname;
	}
	public void setBoardname(String boardname) {
		this.boardname = boardname;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	
	public TblBoard(Integer boardid, String boardname, Integer parentid) {
		super();
		this.boardid = boardid;
		this.boardname = boardname;
		this.parentid = parentid;
	}
	public TblBoard() {
		super();
	}
	
	@Override
	public String toString() {
		return "TblBoard [boardid=" + boardid + ", boardname=" + boardname + ", parentid=" + parentid + "]";
	}
	
	
	
	
	
}

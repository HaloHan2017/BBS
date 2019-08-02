package com.yc.bean;

import java.util.List;

public class PageBean <T>{
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	//已知数据
	private int page;	//页码
	private int rows; 	//每页显示记录数
	private int total;	//总记录数
	
	//用来保存分页查到的数据列表
	private List<T> list;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}


	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	
	
}

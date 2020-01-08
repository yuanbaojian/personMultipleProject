package com.atoz.capp.model;

import java.util.List;

public class PaginationObj<T> {
	
	private int draw;//分页页数
	private long recordsFiltered;
	private long recordsTotal;//总记录条数
	private String lastId;//当前页最后一条记录的id
	private int pageSize;//每页显示条数
	private int toPage;//跳转到页数
	private int nowPage;//当前页数
	private List<T>  data;//数据
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public long getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public long getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public String getLastId() {
		return lastId;
	}
	public void setLastId(String lastId) {
		this.lastId = lastId;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getToPage() {
		return toPage;
	}
	public void setToPage(int toPage) {
		this.toPage = toPage;
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
}

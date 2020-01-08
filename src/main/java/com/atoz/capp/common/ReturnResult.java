package com.atoz.capp.common;

public class ReturnResult {
	private int code = 0;// 用于判断是否出现错误 0 正常
	private String msg = "";// 返回信息
	private Object data = null;// 返回其他对象信息
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}

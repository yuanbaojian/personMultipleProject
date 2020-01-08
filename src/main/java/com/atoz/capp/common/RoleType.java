package com.atoz.capp.common;

public enum RoleType {
	
	STUDENT("student",1),
	TEACHER("teacher",2),;
	  
	private String name;
	private int index;
	   
	private RoleType(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}

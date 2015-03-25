package com.mulitmenuselect.demo.dialog;

import java.io.Serializable;

public class DictUnit implements Serializable{

	public int id;
	public String name;
	public int flag;
	
	public DictUnit() {
		
	}
	public DictUnit(int id, String name, int flag) {
		this.id = id;
		this.name = name;
		this.flag = flag;
	}
	
}

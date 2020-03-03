package com.app.mdc.utils.viewbean.pieecharts;

import java.util.ArrayList;
import java.util.List;

public class Serie {

	private String name;
	
	private String type;

	private List<Item> data;

	public Serie() {
		this.data = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Item> getData() {
		return data;
	}

	public void setData(List<Item> data) {
		this.data = data;
	}
}
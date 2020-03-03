package com.app.mdc.utils.viewbean.echarts;

import java.util.ArrayList;
import java.util.List;

//@Data
public class Serie {

	private String name;	//对应Legend中的一种类别
	
	private String type;	//line 折线

	private List<Object> data;//Y轴数据
	
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

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}
}
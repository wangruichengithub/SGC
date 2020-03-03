package com.app.mdc.utils.viewbean.echarts;

import java.util.ArrayList;
import java.util.List;

//X轴数据，如 ['周一','周二','周三','周四','周五','周六','周日']
//@Data
public class XAxis {
	private List<String> data;

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	public XAxis(List<String> data) {
		this.data = data;
	}

	public XAxis() {
		this.data = new ArrayList<>();
	}
}
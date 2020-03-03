package com.app.mdc.utils.viewbean.echarts;

import java.util.List;

//类型，如 ['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
//@Data
public class Legend {

	private List<String> data;

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}
}

package com.app.mdc.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TreeUtils {

	public static JSONArray listToTree(JSONArray arr,String id,String pid,String child){
		JSONArray r = new JSONArray();
		JSONObject hash = new JSONObject();
		//将数组转为Object的形式，key为数组中的id
		for (Object anArr : arr) {
			JSONObject json = (JSONObject) anArr;
			hash.put(json.getString(id), json);
		}
		//遍历结果集
		for (Object anArr : arr) {
			//单条记录
			JSONObject aVal = (JSONObject) anArr;
			//在hash中取出key为单条记录中pid的值
			JSONObject hashVP = (JSONObject) hash.get(aVal.get(pid).toString());
			//如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
			if (hashVP != null) {
				//检查是否有child属性
				if (hashVP.get(child) != null) {
					JSONArray ch = (JSONArray) hashVP.get(child);
					ch.add(aVal);
					hashVP.put(child, ch);
				} else {
					JSONArray ch = new JSONArray();
					ch.add(aVal);
					hashVP.put(child, ch);
				}
			} else {
				r.add(aVal);
			}
		}
		return r;
	}
}

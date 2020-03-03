package com.app.mdc.utils.httpclient;

import com.alibaba.fastjson.JSON;
import com.app.mdc.model.system.ApiLog;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class HttpUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	public static ApiLog doGet(String url, Map<String, Object> param) {
		// 创建Httpclient对象
		logger.info("开始GET请求调用");
		logger.info("创建httpClient对象");
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String resultString = "";
		CloseableHttpResponse response = null;
		//保存请求信息
		ApiLog apiLog = null;
		try {
			// 创建uri
			logger.info("根据url建立连接并设置请求参数");
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key).toString());
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);

			//请求设置超时时间
			logger.info("设置请求超时时间:3s");
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(3000).setConnectionRequestTimeout(3000)
					.setSocketTimeout(3000).build();
			httpGet.setConfig(requestConfig);
			// 执行请求
			logger.info("执行请求");
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			logger.info("请求完毕，返回值:"+resultString);
		} catch (Exception e) {
			logger.error("GET调用异常:"+e.getMessage());
			resultString = e.getMessage();
		} finally {
			try {
				int code = 500;
				if (response != null) {
					code = response.getStatusLine().getStatusCode();
					response.close();
				}
				apiLog = newApiLog(url,"GET",param,code,resultString);
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return apiLog;
	}

	public static String doGet2(String url,Map<String,Object> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key).toString());
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);

			//请求设置超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(3000).setConnectionRequestTimeout(3000)
					.setSocketTimeout(3000).build();
			httpGet.setConfig(requestConfig);
			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	public static String doPost(String url, Map<String, String> param,String token) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);

			//请求设置超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(10000).setConnectionRequestTimeout(10000)
					.setSocketTimeout(10000).build();
			if(StringUtils.isNotEmpty(token)){
				httpPost.setHeader("usertoken",token);
			}
			httpPost.setConfig(requestConfig);

			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"UTF-8");
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	public static String doPost(String url) {
		return doPost(url, null,null);
	}

	public static String doPostJson(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);

			//请求设置超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(3000).setConnectionRequestTimeout(3000)
					.setSocketTimeout(3000).build();
			httpPost.setConfig(requestConfig);

			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.TEXT_PLAIN);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}


	public static ApiLog doPost2(String url, Map<String, Object> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		ApiLog apiLog = null;
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			//请求设置超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(3000).setConnectionRequestTimeout(3000)
					.setSocketTimeout(3000).build();
			httpPost.setConfig(requestConfig);

			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key).toString()));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "UTF-8");

		} catch (Exception e) {
			e.printStackTrace();
			resultString = e.getMessage();
		} finally {
			try {
				int code = 500;
				if(response != null){
					code = response.getStatusLine().getStatusCode();
					response.close();
				}
				apiLog = newApiLog(url,"POST",param,code,resultString);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return apiLog;
	}

	public static ApiLog quartzPost(String url, Map<String, Object> param,String header,String headerValue) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		ApiLog apiLog = null;
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			if(header != null && !"".equals(header.trim())){
				httpPost.addHeader(header,headerValue);
			}
			//请求设置超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(3000).setConnectionRequestTimeout(3000)
					.setSocketTimeout(3000).build();
			httpPost.setConfig(requestConfig);

			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key).toString()));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			resultString = e.getMessage();
		} finally {
			try {
				int code = 500;
				if(response != null){
					code = response.getStatusLine().getStatusCode();
					response.close();
				}
				apiLog = newApiLog(url,"POST",param,code,resultString);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return apiLog;
	}

	private static ApiLog newApiLog(String url,String requestType,Map<String,Object> requestParam,int code,String responseParam){
		ApiLog apiLog = new ApiLog();
		Date date = new Date();
		apiLog.setCreatetime(date);
		apiLog.setDeleted(0);
		apiLog.setUpdatetime(date);
		apiLog.setRequestUrl(url);
		apiLog.setRequestType(requestType);
		apiLog.setRequestTx(JSON.toJSONString(requestParam));
		apiLog.setStatus(code);
		apiLog.setResponseTx(responseParam);
		try {
			apiLog.setIp(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return apiLog;
	}
}

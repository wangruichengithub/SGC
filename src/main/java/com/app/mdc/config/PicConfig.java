package com.app.mdc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ Author     ：zl
 * @ Description
 */
@Component
@ConfigurationProperties(prefix = "pic")
public class PicConfig {
    /**
     * 图片访问路径
     */
    private String host;

    /**
     * 图片保存路径
     */
    private String savePath;
    /**
     * post请求地址
     */
    private String postUrl;

    /**
     * libreOffice安装目录
     */
    private String libreOffice;

    /**
     * 转pdf后文件地址
     */
    private String fileChangePath;

	/**
	 * 推送相关，appId
	 */
	private String APPID;

	/**
	 * 推送相关APPKEY
	 */
    private String APPKEY;

	/**
	 * 推送相关MASTERSECRET
	 */
	private String MASTERSECRET;

	/**
	 * 推送相关的地址
	 */
	private String msgPushUrl;

	/**
	 * 常见问题，外部链接
	 */
	private String questionUrl;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getLibreOffice() {
        return libreOffice;
    }

    public void setLibreOffice(String libreOffice) {
        this.libreOffice = libreOffice;
    }

    public String getFileChangePath() {
        return fileChangePath;
    }

    public void setFileChangePath(String fileChangePath) {
        this.fileChangePath = fileChangePath;
    }

	public String getAPPID() {
		return APPID;
	}

	public void setAPPID(String APPID) {
		this.APPID = APPID;
	}

	public String getAPPKEY() {
		return APPKEY;
	}

	public void setAPPKEY(String APPKEY) {
		this.APPKEY = APPKEY;
	}

	public String getMASTERSECRET() {
		return MASTERSECRET;
	}

	public void setMASTERSECRET(String MASTERSECRET) {
		this.MASTERSECRET = MASTERSECRET;
	}

	public String getMsgPushUrl() {
		return msgPushUrl;
	}

	public void setMsgPushUrl(String msgPushUrl) {
		this.msgPushUrl = msgPushUrl;
	}

	public String getQuestionUrl() {
		return questionUrl;
	}

	public void setQuestionUrl(String questionUrl) {
		this.questionUrl = questionUrl;
	}
}


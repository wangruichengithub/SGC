package com.app.mdc.enums;

public enum ApiErrEnum {

    //校验失败err
    ERR100("请入参usertoken！"),
    ERR101("access failed！"),
    ERR201("该推送码已失效，请重新填写有效推送码"),
    ERR202("支付密码错误，请重新填写"),
    ERR203("验证码错误，请重新填写验证码"),
    ERR204("该用户未有钱包余额"),
    ERR205("交易失败，您的USDT余额不足"),
    ERR206("交易失败，您的MDC余额不足"),
    ERR207("交易失败，余额不足"),
    ERR208("交易地址不存，请重新填写"),
    ERR301("该文件已被删除，请选择其他文件"),
    ERR302("该文件已不存在，请选择其他文件"),
    ERR500( "数据处理失败！"),


    ERR600("该用户名已注册，请确认"),
    ERR601("该字典编号已存在，请重新输入"),
    ERR602("该用户姓名已存在，请重新输入姓名以便区分");

	



    private String desc;
    ApiErrEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public ApiErrEnum setDesc(String desc) {
        this.desc = desc;
        return this;
    }
}

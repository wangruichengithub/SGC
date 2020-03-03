package com.app.mdc.utils.verification;

import java.util.Random;


/**
 * 工具类,生成随机验证码
 */
public class RandomValidateCodeUtil {
    private static Random random = new Random();
//    private static String randString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生的字符串
    private static String randString = "0123456789";//随机产生的字符串

    private static int stringNum = 6;// 随机产生的字符数量

    /**
     * 生成随机图片
     */
    public static String getRandcode() {
        String randomString = "";
        for (int i = 1; i <= stringNum; i++) {
            randomString += getRandomString(random.nextInt(randString.length()));
        }
        return randomString;
    }


    /*
     * 获取随机的字符
     */
    public static String getRandomString(int num) {
        return String.valueOf(randString.charAt(num));
    }

    public static void main(String[] args) {
        System.out.println(getRandcode());
    }
}
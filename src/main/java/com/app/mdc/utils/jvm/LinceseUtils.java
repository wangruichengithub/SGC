package com.app.mdc.utils.jvm;

import com.app.mdc.utils.encryptdecrypt.DES;

import java.io.BufferedReader;
import java.io.FileReader;

public class LinceseUtils {
    private static LinceseUtils ourInstance;
    private static String key;

    public static LinceseUtils getInstance(String keyStr) {
        key = keyStr;
        if (ourInstance == null){
            return new LinceseUtils();
        }
        return ourInstance;
    }

    private LinceseUtils() {
    }

    public boolean isCheckLincese(){

        //判断是否是windows系统
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("win");
        String lincese = "";
        boolean isChesck;
        try {
            if (isWindows){
                try (FileReader reader = new FileReader("C:/mdc/mdc.txt");
                     BufferedReader br = new BufferedReader(reader)
                ) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        lincese = line;
                    }
                }
            }
            String linceseMsg = decrypt(lincese, key);
            if (linceseMsg == null){
                return false;
            }

            String[] linceseMsgStrings = linceseMsg.split("_");
            if (linceseMsgStrings.length != 3){
                return false;
            }

            isChesck = linceseMsgStrings[0].equals(JvmUtils.getWindowsMac())
                    && linceseMsgStrings[1].equals(JvmUtils.getCPUIDWindows())
                    && Long.valueOf(linceseMsgStrings[2]) >= System.currentTimeMillis();
        }catch (Exception e){
            return false;
        }
        return isChesck;

    }

    /**
     * 使用默认密钥进行DES加密
     */
    private String encrypt(String plainText) {
        try {
            return new DES().encrypt(plainText);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 使用指定密钥进行DES解密
     */
    private String encrypt(String plainText, String key) {
        try {
            return new DES(key).encrypt(plainText);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 使用默认密钥进行DES解密
     */
    private String decrypt(String plainText) {
        try {
            return new DES().decrypt(plainText);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 使用指定密钥进行DES解密
     */
    private String decrypt(String plainText, String key) {
        try {
            return new DES(key).decrypt(plainText);
        } catch (Exception e) {
            return null;
        }
    }
}

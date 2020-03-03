package com.app.mdc.utils.jvm;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

public class JvmUtils {

    public static String getWindowsMac() throws Exception {
        InetAddress ip;
        NetworkInterface ni;
        List<String> macList = new ArrayList<>();
        Enumeration<NetworkInterface> netInterfaces = NetworkInterface
                .getNetworkInterfaces();
        while (netInterfaces.hasMoreElements()) {
            ni = netInterfaces.nextElement();
            Enumeration<InetAddress> ips = ni.getInetAddresses();
            while (ips.hasMoreElements()) {
                ip = ips.nextElement();
                if (!ip.isLoopbackAddress()
                        && ip.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
                    macList.add(getMacFromBytes(ni.getHardwareAddress()));
                }
            }
        }
        return macList.get(0);
    }

    /**
     * 获取系统唯一编码Windows
     * @return 获取CPU序列号
     */
    public static String getCPUIDWindows() throws Exception {
        Process process = Runtime.getRuntime().exec(
                new String[] { "wmic", "csproduct", "get", "IdentifyingNumber" });
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        sc.next();
        return sc.next();
    }


    private static String getMacFromBytes(byte[] bytes) {
        StringBuilder mac = new StringBuilder();
        byte currentByte;
        boolean first = false;
        for (byte b : bytes) {
            if (first) {
                mac.append("-");
            }
            currentByte = (byte) ((b & 240) >> 4);
            mac.append(Integer.toHexString(currentByte));
            currentByte = (byte) (b & 15);
            mac.append(Integer.toHexString(currentByte));
            first = true;
        }
        return mac.toString().toUpperCase();
    }

}

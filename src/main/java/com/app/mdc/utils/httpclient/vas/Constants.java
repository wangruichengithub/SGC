package com.app.mdc.utils.httpclient.vas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Constants {
    public static String RPC_USER = "mdcrpc";
    public static String RPC_PASSWORD = "Mdc123456&";
    public static String RPC_ALLOWIP = "124.156.167.70";
    public static String RPC_PORT = "6512";
}

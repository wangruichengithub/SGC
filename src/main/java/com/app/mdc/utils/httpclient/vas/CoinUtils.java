package com.app.mdc.utils.httpclient.vas;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.app.mdc.mapper.mdc.VasWalletMapper;
import com.app.mdc.model.mdc.Block;
import com.app.mdc.model.mdc.VasWallet;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
@Component
@Configuration
public class CoinUtils {
    @Autowired
    private  VasWalletMapper walletMapper;
    private static final Logger log = LoggerFactory.getLogger(CoinUtils.class);
    private static CoinUtils instance;
    private static void init() throws Throwable {
        if(null == instance){
            instance = new CoinUtils();
        }
    }
    private JsonRpcHttpClient client;
    public CoinUtils() throws Throwable{
        // 身份认证
        String cred = Base64.encodeBase64String((Constants.RPC_USER + ":" + Constants.RPC_PASSWORD).getBytes());
        Map <String, String> headers = new HashMap <String, String>(1);
        headers.put("Authorization", "Basic " + cred);
        client = new JsonRpcHttpClient(new URL("http://"+Constants.RPC_ALLOWIP+":"+Constants.RPC_PORT), headers);
    }
    /**
     * 创建地址
     * @return
     * @throws Throwable
     */
    public VasWallet createaddress(int userId) throws Throwable {
        String address = JSONObject.toJSONString(client.invoke("getnewaddress", new Object[] {}, Object.class));
        VasWallet vasWallet = VasWallet.builder()
                .address(address)
                .userId(userId)
                .pubkey("")
                .privkey("")
                .balance(0.00)
                .build();
        return vasWallet;
    }


    /**
     * 获取余额
     * @param address
     * @return
     * @throws Throwable
     */
    public Double getBalances(String address) throws Throwable {
        Double ye=0.00;
        String str = JSONObject.toJSONString(client.invoke("getbalances", new Object[] {address}, Object.class));
        JSONObject jsonObject = JSONObject.parseObject(str);;
        JSONArray jsonArray = jsonObject.getJSONArray("total");
        for(int i=0;i<jsonArray.size();i++){
            ye = Double.valueOf(jsonArray.getJSONObject(i).get("qty").toString());
        }
        return ye;
    }

    /**
     * 转账
     *
     * @return
     * @throws Throwable
     */
    public void transfer(String formAddress,String toAddress,Double amount) throws Throwable {
        String str = JSONObject.toJSONString(client.invoke("sendfrom", new Object[] {formAddress,toAddress,amount}, Object.class));
    }

    /**
     * 查看区块同步情况
     */
    public JSONObject getsyncinfo() throws Throwable {
       JSONObject jsonObject  = (JSONObject) JSONObject.toJSON(client.invoke("getsyncinfo", new Object[] {}, Object.class));
       return jsonObject;
    }

    /**
     * 解析区块
     * @return
     * @throws Throwable
     */
    public Block getblock(String height) throws Throwable {
        LinkedHashMap string = ((LinkedHashMap)client.invoke("getblock", new Object[] {height,4}, Object.class));
        String json = JSONObject.toJSONString(string);
        Block vasBlock =stringToBean(json,Block.class);
        return vasBlock;
    }

    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }

    }

}

package com.app.mdc.utils.httpclient.vas;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.app.mdc.mapper.mdc.VasWalletMapper;
import com.app.mdc.model.mdc.VasWallet;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
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
        String jsonObject = JSONObject.toJSONString(client.invoke("createaddresses", new Object[] {1}, Object.class));
        JSONArray result = JSONObject.parseArray(jsonObject);
        String address ="";
        String pubkey = "";
        String privkey = "";
        for (int i = 0; i < result.size(); i++) {
             address = result.getJSONObject(i).getString("address");
             pubkey = result.getJSONObject(i).getString("pubkey");
             privkey = result.getJSONObject(i).getString("privkey");
        }
        VasWallet vasWallet = VasWallet.builder()
                .address(address)
                .userId(userId)
                .pubkey(pubkey)
                .privkey(privkey)
                .balance(0.00)
                .build();
        return vasWallet;
    }

    /**
     * 导入地址
     * @param address
     * @throws Throwable
     */
    public void  importAddress(String address) throws Throwable {
        client.invoke("createaddresses", new Object[] {1}, Object.class);
    }

}
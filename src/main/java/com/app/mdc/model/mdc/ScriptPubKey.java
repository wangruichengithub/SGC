package com.app.mdc.model.mdc;

import java.util.List;

public class ScriptPubKey
{
    private String asm;

    private String hex;

    private int reqSigs;

    private String type;

    private List <String> addresses;

    public void setAsm(String asm){
        this.asm = asm;
    }
    public String getAsm(){
        return this.asm;
    }
    public void setHex(String hex){
        this.hex = hex;
    }
    public String getHex(){
        return this.hex;
    }
    public void setReqSigs(int reqSigs){
        this.reqSigs = reqSigs;
    }
    public int getReqSigs(){
        return this.reqSigs;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setAddresses(List<String> addresses){
        this.addresses = addresses;
    }
    public List<String> getAddresses(){
        return this.addresses;
    }
}

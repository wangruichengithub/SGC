package com.app.mdc.model.mdc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class Vout
{
    private String value;

    private int n;

    private ScriptPubKey scriptPubKey;

    public Vout() {
    }

    public void setValue(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
    public void setN(int n){
        this.n = n;
    }
    public int getN(){
        return this.n;
    }
    public void setScriptPubKey(ScriptPubKey scriptPubKey){
        this.scriptPubKey = scriptPubKey;
    }
    public ScriptPubKey getScriptPubKey(){
        return this.scriptPubKey;
    }
}

package com.app.mdc.model.mdc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;
@AllArgsConstructor
@Data
@ToString
public class Block
{
    public Block() {
    }

    private String hash;

    private String miner;

    private int confirmations;

    private int size;

    private int height;

    private int version;

    private String merkleroot;

    private List <Tx> tx;

    private int time;

    private int nonce;

    private String bits;

    private double difficulty;

    private String chainwork;

    private String prevblockhash;

    private String nextblockhash;
}

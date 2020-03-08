package com.app.mdc.model.mdc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;
@AllArgsConstructor
@Data
@ToString
public class Tx
{
    private String txid;

    private int version;

    private int locktime;

    private List<Vin> vin;

    private List<Vout> vout;

    private String hex;

    public Tx() {
    }
}

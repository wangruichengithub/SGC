package com.app.mdc.model.mdc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class Vin {
    private String coinbase;

    private int sequence;

    public Vin() {
    }
}

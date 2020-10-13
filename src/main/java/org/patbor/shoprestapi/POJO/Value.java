package org.patbor.shoprestapi.POJO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Value {

    private BigDecimal valueNetto;
    private BigDecimal valueBrutto;

    public Value() {
        valueBrutto = BigDecimal.valueOf(0);
        valueNetto = BigDecimal.valueOf(0);
    }
}

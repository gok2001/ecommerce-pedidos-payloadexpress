package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;

public abstract class FormaPagamento {

    private BigDecimal valor;

    public BigDecimal getValo(){
        return valor;
    }

    public abstract boolean processar();

}

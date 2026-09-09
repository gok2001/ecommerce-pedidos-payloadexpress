package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;

public abstract class FormaPagamento {

    private BigDecimal valor;

    public FormaPagamento(BigDecimal valor){
        setValor(valor);
    }

    public BigDecimal getValo(){
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public abstract boolean processar();

    public String getResumo() {
        return "Pagamento de R$ " + valor;
    }

}

package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class FormaPagamento {

    private BigDecimal valor;
    private LocalDate dataDoPagamento;

    public FormaPagamento(BigDecimal valor, LocalDate dataDoPagamento){
        setValor(valor);
        setDataDoPagamento(dataDoPagamento);
    }

    public BigDecimal getValo(){
        return valor;
    }

    public void setValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do pagamento deve ser positivo");
        }

        this.valor = valor;
    }

    public LocalDate getDataDoPagamento() {
        return dataDoPagamento;
    }

    public void setDataDoPagamento(LocalDate dataDoPagamento) {
        if (dataDoPagamento == null || dataDoPagamento.isBefore(LocalDate.now())) {
             throw new IllegalArgumentException("A data do pagamento deve ser maior ou igual a data atual.");
        }

        this.dataDoPagamento = dataDoPagamento;
    }

    public abstract boolean processar();

    public String getResumo() {
        return String.format("%s no valor de R$ %s",
                             getClass().getSimpleName(), valor);
    }

}

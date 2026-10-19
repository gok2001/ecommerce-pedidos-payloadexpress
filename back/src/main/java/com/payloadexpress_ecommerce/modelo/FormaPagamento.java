package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class FormaPagamento implements ProcessadorPagamento {

    private final BigDecimal valor;
    private final LocalDate dataDoPagamento;
    private String comprovante;

    public FormaPagamento(BigDecimal valor, LocalDate dataDoPagamento){
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("Valor do pagamento deve ser positivo.");
        }

        if (dataDoPagamento == null || dataDoPagamento.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data do pagamento não pode ser passada.");
        }

        this.valor = valor;
        this.dataDoPagamento = dataDoPagamento;
    }

    public BigDecimal getValor(){
        return valor;
    }

    public LocalDate getDataDoPagamento() {
        return dataDoPagamento;
    }

    @Override
    public final boolean processar(BigDecimal valorCobrado) {
        if (valorCobrado == null || valorCobrado.signum() <= 0) {
            throw new IllegalArgumentException("Valor cobrado deve ser positivo.");
        }

        if (valorCobrado.compareTo(valor) != 0) {
            throw new IllegalArgumentException("Valor do pagamento difere do total do pedido.");
        }

        return processar();
    }

    public abstract boolean processar();

    protected void registrarComprovante(String prefixo) {
        if (comprovante == null) {
            comprovante = prefixo + "-" + java.util.UUID.randomUUID();
        }
    }

    @Override 
    public String getComprovante() {
        return comprovante;
    }

    public String getResumo() {
        return String.format("%s no valor de R$ %s",
                             getClass().getSimpleName(), valor);
    }

}

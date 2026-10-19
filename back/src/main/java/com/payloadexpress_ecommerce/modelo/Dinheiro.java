package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;
import java.util.UUID;

public class Dinheiro implements ProcessadorPagamento {

    private final BigDecimal valorRecebido;
    private BigDecimal troco = BigDecimal.ZERO;
    private String comprovante;

    public Dinheiro(BigDecimal valorRecebido) {
        if (valorRecebido == null || valorRecebido.signum() <= 0) {
            throw new IllegalArgumentException("Valor recebido deve ser positivo.");
        }

        this.valorRecebido = valorRecebido;
    }

    @Override
    public boolean processar(BigDecimal valor) {
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("Valor cobrado deve ser positivo.");
        }

        if (comprovante != null) {
            throw new IllegalStateException("Pagamento em dinheiro já processado.");
        }

        if (valorRecebido.compareTo(valor) < 0) return false;

        troco = valorRecebido.subtract(valor);
        comprovante = "DINHEIRO-" + UUID.randomUUID();
        return true;
    }

    public BigDecimal getTroco() {
        return troco;
    }

    @Override
    public String getComprovante() {
        return comprovante;
    }

    @Override
    public String getDescricao() {
        return "Dinheiro";
    }
}
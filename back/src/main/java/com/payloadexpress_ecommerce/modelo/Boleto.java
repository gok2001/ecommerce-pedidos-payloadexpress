package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Boleto extends FormaPagamento {

    private final String codigoDeBarras;
    private final LocalDate dataDeVencimento;
    private boolean compensado;

    public Boleto(BigDecimal valor, LocalDate dataDoPagamento, String codigoDeBarras, LocalDate dataDeVencimento) {
        super(valor, dataDoPagamento);

        if (codigoDeBarras == null || codigoDeBarras.isBlank()) {
            throw new IllegalArgumentException("Código de barras é obrigatório.");
        }

        if (dataDeVencimento == null || dataDeVencimento.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Vencimento não pode estar no passado.");
        }

        this.codigoDeBarras = codigoDeBarras.trim();
        this.dataDeVencimento = dataDeVencimento;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public LocalDate getDataDeVencimento() {
        return dataDeVencimento;
    }

    public void confirmarCompensacao() {
        if (LocalDate.now().isAfter(dataDeVencimento)) {
            throw new IllegalStateException("Boleto vencido não pode ser compensado nesta simulação.");
        }

        compensado = true;
    }

    @Override
    public boolean processar() {
        if (!compensado || LocalDate.now().isAfter(dataDeVencimento)) {
            return false;
        }

        registrarComprovante("BOLETO");
        return true;
    }

    @Override
    public String getDescricao() { return "Boleto " + codigoDeBarras; }
}
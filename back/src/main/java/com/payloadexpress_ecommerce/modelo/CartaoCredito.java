package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CartaoCredito extends FormaPagamento {

    private final String numeroMascarado;
    private final String bandeira;
    private final int quantidadeDeParcelas;
    private final BigDecimal limiteDisponivel;

    public CartaoCredito(
        BigDecimal valor,
        LocalDate dataDoPagamento,
        String numeroMascarado,
        String bandeira,
        int quantidadeDeParcelas,
        BigDecimal limiteDisponivel
    ) {

        super(valor, dataDoPagamento);

        if (numeroMascarado == null || numeroMascarado.isBlank()) {
            throw new IllegalArgumentException("Número mascarado é obrigatório.");
        }

        if (bandeira == null || bandeira.isBlank()) {
            throw new IllegalArgumentException("Bandeira é obrigatória.");
        }

        if (quantidadeDeParcelas < 1 || quantidadeDeParcelas > 12) {
            throw new IllegalArgumentException("Quantidade de parcelas deve ser de 1 a 12.");
        }

        if (limiteDisponivel == null || limiteDisponivel.signum() < 0) {
            throw new IllegalArgumentException("Limite disponível deve ser não negativo.");
        }

        this.numeroMascarado = numeroMascarado.trim();
        this.bandeira = bandeira.trim();
        this.quantidadeDeParcelas = quantidadeDeParcelas;
        this.limiteDisponivel = limiteDisponivel;
    }

    @Override
    public boolean processar() {
        if (limiteDisponivel.compareTo(getValor()) < 0) return false;
        registrarComprovante("CARTAO-" + bandeira);
        return true;
    }

    @Override
    public String getDescricao() {
        return "Cartão " + bandeira + " " + numeroMascarado + " em " + quantidadeDeParcelas + "x";
    }
}
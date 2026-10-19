package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pix extends FormaPagamento {

    private final String chave;
    private final TipoChavePix tipoDaChave;

    public Pix(BigDecimal valor, LocalDate dataDoPagamento, String chave, TipoChavePix tipoDaChave){
        super(valor, dataDoPagamento);
        
        if (tipoDaChave == null || chave == null || chave.isBlank()) {
            throw new IllegalArgumentException("Tipo e chave são obrigatórios.");
        }

        boolean valida = switch (tipoDaChave) {
            case CPF -> chave.matches("\\d{11}");
            case EMAIL -> chave.matches("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");
            case TELEFONE -> chave.matches("\\d{10,11}");
            case ALEATORIA -> chave.matches("(?i)[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}");
        };

        if (!valida) {
            throw new IllegalArgumentException("Chave Pix incompatível com o tipo " + tipoDaChave);
        }

        this.chave = chave;
        this.tipoDaChave = tipoDaChave;
    }

    public String getChave() {
        return chave;
    }
    public TipoChavePix getTipoDaChave() {
        return tipoDaChave;
    }

    @Override
    public boolean processar(BigDecimal valor) {
        registrarComprovante("PIX");
        return true;
    }

    @Override
    public String getDescricao() {
        return "Pix (" + tipoDaChave + ") - " + chave;
    }
}

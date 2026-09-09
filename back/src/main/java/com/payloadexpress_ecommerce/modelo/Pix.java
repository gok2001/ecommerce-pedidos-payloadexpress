package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pix extends FormaPagamento{

    private String chave;

    public Pix(BigDecimal valor, LocalDate dataDoPagamento, String chave){
        super(valor, dataDoPagamento);
        setChave(chave);
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        if (chave == null || chave.isBlank()) {
            throw new IllegalArgumentException("Chave Pix é obrigatória");
        }

        this.chave = chave;
    }

    @Override 
    public boolean processar(){
        // simulação: um Pix é aprovado na hora
        System.out.println("Processando Pix para a chave " + chave);
        return true;
    }

    @Override
    public String getResumo(){
        return super.getResumo() + " (chave " + chave + ")";
    }
}

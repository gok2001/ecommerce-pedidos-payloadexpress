package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pix extends FormaPagamento implements ProcessadorPagamento{

    //Atributo
    private String chave;

    //Metodo construtor
    public Pix(BigDecimal valor, LocalDate dataDoPagamento, String chave){
        super(valor, dataDoPagamento);
        setChave(chave);
    }

    //Metodos gets e setters
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


    //metodos da interface
    @Override
    public boolean processar(BigDecimal valor) {
        System.out.println("Enviando cobrança Pix para a chave " + chave);
        return true; // aprovação imediata
    }
    @Override
    public String getComprovante() {
        return "PIX-" + System.currentTimeMillis();
    }
    @Override
    public String getDescricao() {
        return "Pix - chave " + chave;
    }
}

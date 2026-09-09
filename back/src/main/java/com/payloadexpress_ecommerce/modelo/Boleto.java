package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Boleto extends FormaPagamento{

    //Atributos
    private String codigoDeBarras;
    private LocalDate dataDeVencimeto;


    //Metodo construtor
    Boleto(BigDecimal valor, LocalDate dataDoPagamento, String codigoDeBarras, LocalDate dataDeVencimento){
        super(valor, dataDoPagamento);
        setCodigoDeBarras(codigoDeBarras);
        setDataDeVencimeto(dataDeVencimento);
    }


    //Metodos gets e setters
    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        if (codigoDeBarras == null) {
            throw new IllegalArgumentException("Código de barras não pode ser vazio: "+codigoDeBarras);
        }

        this.codigoDeBarras = codigoDeBarras;
    }

    public LocalDate getDataDeVencimeto() {
        return dataDeVencimeto;
    }

    public void setDataDeVencimeto(LocalDate dataDeVencimeto) {
        if (dataDeVencimeto == null || dataDeVencimeto.isBefore(LocalDate.now())) {
             throw new IllegalArgumentException("A data de vencimento deve ser maior ou igual a data atual.");
        }

        this.dataDeVencimeto = dataDeVencimeto;
    }


    //Metodo exibir resumo
    @Override
    public String getResumo(){
        return super.getResumo() + " (boleto " + codigoDeBarras + ")";
    }


    @Override 
    public boolean processar(){
        // simulação: um Boleto é aprovado na hora
        System.out.println("Processando Boleto para o código " + codigoDeBarras);
        return true;
    }
}

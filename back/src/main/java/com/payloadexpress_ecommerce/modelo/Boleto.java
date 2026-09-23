package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Boleto extends FormaPagamento implements ProcessadorPagamento{

    //Atributos
    private String codigoDeBarras;
    private LocalDate dataDeVencimento;


    //Metodo construtor
    Boleto(BigDecimal valor, LocalDate dataDoPagamento, String codigoDeBarras, LocalDate dataDeVencimento){
        super(valor, dataDoPagamento);
        setCodigoDeBarras(codigoDeBarras);
        setDataDeVencimento(dataDeVencimento);
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

    public LocalDate getDataDeVencimento() {
        return dataDeVencimento;
    }

    public void setDataDeVencimento(LocalDate dataDeVencimento) {
        if (dataDeVencimento == null || dataDeVencimento.isBefore(LocalDate.now())) {
             throw new IllegalArgumentException("A data de vencimento deve ser maior ou igual a data atual.");
        }

        this.dataDeVencimento = dataDeVencimento;
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


    //metodos da interface
    @Override
    public boolean processar(BigDecimal valor) {
        System.out.println("Enviando cobrança em Boleto com o código de barras: " + codigoDeBarras);
        return true; // aprovação imediata
    }
    @Override
    public String getComprovante() {
        return "Boleto-" + System.currentTimeMillis();
    }
    @Override
    public String getDescricao() {
        return "Boleto - código de barras " + codigoDeBarras;
    }
}

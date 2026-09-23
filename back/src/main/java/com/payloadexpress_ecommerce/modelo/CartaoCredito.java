package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CartaoCredito extends FormaPagamento implements ProcessadorPagamento{

    //Atributos
    private String numeroMascarado;
    private String bandeira;
    private int quantidadeDeParcelas;

    //Metodo construtor
    public CartaoCredito(BigDecimal valor, LocalDate dataDoPagamento, String numeroMascarado, String bandeira, int quantidadeDeParcelas){
        super(valor, dataDoPagamento);
        setNumeroMascarado(numeroMascarado);
        setBandeira(bandeira);
        setQuantidadeDeParcelas(quantidadeDeParcelas);
    }

    //Metodos gets e setters
    public String getNumeroMascarado() {
        return numeroMascarado;
    }

    public void setNumeroMascarado(String numeroMascarado) {
        if (numeroMascarado == null) {
            throw new IllegalArgumentException("Numero mascarado não pode ser vazio"+numeroMascarado);
        }

        this.numeroMascarado = numeroMascarado;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        if (bandeira == null) {
            throw new IllegalArgumentException("Bandeira não pode ser vazio"+bandeira);
        }
        this.bandeira = bandeira;
    }

    public int getQuantidadeDeParcelas() {
        return quantidadeDeParcelas;
    }

    public void setQuantidadeDeParcelas(int quantidadeDeParcelas) {
        if (quantidadeDeParcelas < 0 || quantidadeDeParcelas > 12){
            throw new IllegalArgumentException("Quantidade de parcelas invalida: "+quantidadeDeParcelas);
        }
        this.quantidadeDeParcelas = quantidadeDeParcelas;
    }

    //Metodo exibir resumo
    @Override
    public String getResumo(){
        return super.getResumo() + " (Cartão de Crédito " + numeroMascarado + ", "+bandeira+")";
    }


    @Override 
    public boolean processar(){
        // simulação: um Cartão de Crédito é aprovado na hora
        System.out.println("Processando Cartão de Crédito para o pagamento");
        return true;
    }


    //metodos da interface
    @Override
    public boolean processar(BigDecimal valor) {
        System.out.println("Enviando cobrança em Cartão de Crédito com o numero mascarado: " + numeroMascarado + (quantidadeDeParcelas == 0?".":".\nQuantidade de parcelas: "+quantidadeDeParcelas));
        return true; // aprovação imediata
    }
    @Override
    public String getComprovante() {
        return "Cartão de Crédito-" + System.currentTimeMillis();
    }
    @Override
    public String getDescricao() {
        return "Cartão de Crédito - numero mascarado " + numeroMascarado;
    }
}

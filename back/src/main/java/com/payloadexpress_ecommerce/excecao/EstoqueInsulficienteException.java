package com.payloadexpress_ecommerce.excecao;

import com.payloadexpress_ecommerce.modelo.Produto;

public class EstoqueInsulficienteException extends ECommerceException {

    private final Produto produto;    
    private final int quantidadeSolicitada;

    public EstoqueInsulficienteException(Produto produto, int quantidade) {
        super("Estoque insuficiente de " + produto.getNome()
            + ": disponível " + produto.getQuantidadeEmEstoque()
            + ", solicitado " + quantidade);
        
        this.produto = produto;
        this.quantidadeSolicitada = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }
}
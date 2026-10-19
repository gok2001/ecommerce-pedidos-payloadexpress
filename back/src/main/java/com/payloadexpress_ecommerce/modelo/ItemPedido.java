package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;

public class ItemPedido {

    private final Produto produto;
    private final int quantidade;
    private final BigDecimal precoPraticado;

    public ItemPedido(Produto produto, int quantidade, BigDecimal precoPraticado){
        if (produto == null) {
            throw new IllegalArgumentException("Produto é obrigatório.");
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva.");
        }

        if (precoPraticado == null || precoPraticado.signum() <= 0) {
            throw new IllegalArgumentException("Preço praticado deve ser positivo.");
        }

        this.produto = produto;
        this.quantidade = quantidade;
        this.precoPraticado = precoPraticado;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoPraticado() {
        return precoPraticado;
    }

    public BigDecimal calcularSubtotal(){
        return precoPraticado.multiply(BigDecimal.valueOf(quantidade));
    }

    @Override
    public String toString(){
        return String.format("[%s] %s x %d = R$ %s", produto.getCodigo(), produto.getNome(), quantidade, calcularSubtotal());
    }
}

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

    public long getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoPraticado() {
        return precoPraticado;
    }

    @Override
    public String toString(){
        return String.format("[%s] %s = R$%s", this.produto, this.quantidade, this.precoPraticado);
    }

    public BigDecimal calcularSubtotal(){
        return this.precoPraticado.multiply(BigDecimal.valueOf(this.quantidade));
    }
}

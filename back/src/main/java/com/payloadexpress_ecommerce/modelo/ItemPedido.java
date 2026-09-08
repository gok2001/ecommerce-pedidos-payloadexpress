package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;

public class ItemPedido {

    private Produto produto;
    private long quantidade;
    private BigDecimal precoPraticado;

    public ItemPedido(){

    }

    public ItemPedido(Produto produto, long quantidade, BigDecimal precoPraticado){
        setProduto(produto);
        setQuantidade(quantidade);
        setPrecoPraticado(precoPraticado);
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser vazio: "+produto);
            
        }
        this.produto = produto;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        if (quantidade < 0){
            throw new IllegalArgumentException("Quantidade não pode ser negativo: "+quantidade);
        }
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoPraticado() {
        return precoPraticado;
    }

    public void setPrecoPraticado(BigDecimal precoPraticado) {
        if (precoPraticado == null || precoPraticado.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Preço praticado não pode ser negativo: "+precoPraticado);
        }
        this.precoPraticado = precoPraticado;
    }

    @Override
    public String toString(){
        return String.format("[%s] %s", this.produto, this.quantidade, this.precoPraticado);
    }

    public BigDecimal calcularSubtotal(){
        return this.precoPraticado.multiply(BigDecimal.valueOf(this.quantidade));
    }
}

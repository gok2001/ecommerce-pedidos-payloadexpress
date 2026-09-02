package com.payloadexpress_ecommerce.modelo;

public class ItemPedido {

    private Produto produto;
    private long quantidade;
    private double precoPraticado;

    public ItemPedido(){

    }

    public ItemPedido(Produto produto, long quantidade, double precoPraticado){
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoPraticado = precoPraticado;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoPraticado() {
        return precoPraticado;
    }

    public void setPrecoPraticado(double precoPraticado) {
        this.precoPraticado = precoPraticado;
    }

    @Override
    public String toString(){
        return String.format("[%s] %s", this.produto, this.quantidade, this.precoPraticado);
    }

    public double calcularSubtotal(){
        return this.quantidade * this.precoPraticado;
    }
}

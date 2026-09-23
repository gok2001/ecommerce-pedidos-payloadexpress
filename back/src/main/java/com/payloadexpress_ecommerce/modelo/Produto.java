package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;

public class Produto {

    private String codigo;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int quantidadeEmEstoque;
    private boolean ativo;

    public Produto(String codigo, String nome, String descricao, BigDecimal preco, int quantidadeEmEstoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.ativo = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean temEstoqueDisponivel(int quantidadeDesejada) {
        return ativo && quantidadeEmEstoque >= quantidadeDesejada;
    }

    public void baixarEstoque(int quantidade) {
        this.quantidadeEmEstoque = this.quantidadeEmEstoque - quantidade;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - R$ %.2f (%d em estoque) - %s", this.codigo, this.nome, this.preco, this.quantidadeEmEstoque, this.descricao);
    }
}

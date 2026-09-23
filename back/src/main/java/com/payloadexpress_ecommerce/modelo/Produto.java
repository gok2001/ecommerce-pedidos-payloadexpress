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
        
        setNome(nome);
        setDescricao(descricao);
        setPreco(preco);
        setQuantidadeEmEstoque(quantidadeEmEstoque);

        this.ativo = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }

        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia.");
        }

        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        if (preco == null || preco.signum() == -1 ) {
            throw new IllegalArgumentException("Preço não pode ser nulo ou negativo.");
        }

        this.preco = preco;
    }

    public int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
        if (quantidadeEmEstoque < 0) {
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa.");
        }

        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean temEstoqueDisponivel(int quantidadeDesejada) {
        if (quantidadeDesejada < 0) {
            throw new IllegalArgumentException("Quantidade inválida.");
        }

        return ativo && quantidadeEmEstoque >= quantidadeDesejada;
    }

    public void baixarEstoque(int quantidade) {
        if (quantidade > this.quantidadeEmEstoque || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade inválida a ser retirada.");
        }

        this.quantidadeEmEstoque = this.quantidadeEmEstoque - quantidade;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - R$ %.2f (%d em estoque) - %s", this.codigo, this.nome, this.preco, this.quantidadeEmEstoque, this.descricao);
    }
}

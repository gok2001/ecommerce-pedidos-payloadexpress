package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;

import com.payloadexpress_ecommerce.excecao.EstoqueInsulficienteException;

public class Produto {

    private String codigo;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int quantidadeEmEstoque;
    private boolean ativo;
    //private double peso;

    public Produto(String codigo, String nome, String descricao, BigDecimal preco, int quantidadeEmEstoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.ativo = true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean temEstoqueDisponivel(int quantidadeDesejada) {
        return ativo && quantidadeEmEstoque >= quantidadeDesejada;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - R$ %.2f (%d em estoque) - %s", this.codigo, this.nome, this.preco, this.quantidadeEmEstoque, this.descricao);
    }

    public void baixarEstoque(int quantidade) throws EstoqueInsulficienteException {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva.");
        }
        if (quantidade > this.quantidadeEmEstoque) {
            throw new EstoqueInsulficienteException(this, quantidade);
        }
        this.quantidadeEmEstoque -= quantidade;
    }
}

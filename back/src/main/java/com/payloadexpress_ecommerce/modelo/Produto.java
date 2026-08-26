package com.payloadexpress_ecommerce.modelo;

public class Produto {

    private String codigo;
    private String nome;
    private String descricao;
    private double preco;
    private int quantidadeEmEstoque;
    private boolean ativo;
    private double peso;

    public Produto (String codigo, String nome, double preco, int quantidadeEmEstoque, String descricao){
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.ativo = true;
        }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public boolean isAtvio(){           //não se usa "get" em boolean usa-se "is""
        return ativo;
    }

    public boolean temEstoqueDisponivel(int quantidadeDesejada){
        return ativo && this.quantidadeEmEstoque >= quantidadeDesejada;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - R$ %.2f (%d em estoque) - %s", this.codigo, this.nome, this.preco, this.quantidadeEmEstoque, this.descricao);
    }
    public void baixarEstoque(int quantidade){
        this.quantidadeEmEstoque = this.quantidadeEmEstoque - quantidade;
    }
}

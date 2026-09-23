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

    /**
     * Define o nome do produto.
     *
     * @param nome nome do produto, não pode ser nulo ou vazio
     * @throws IllegalArgumentException se o nome for nulo ou vazio
     */
    public void setNome(String nome) {

        validarTexto(nome, "Nome");

        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do produto.
     *
     * @param descricao descrição do produto, não pode ser nula ou vazia
     * @throws IllegalArgumentException se a descrição for nula ou vazia
     */
    public void setDescricao(String descricao) {

        validarTexto(descricao, "Descrição");

        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    /**
     * Define o preço do produto.
     *
     * @param preco preço do produto, não pode ser nulo ou negativo
     * @throws IllegalArgumentException se o preço for nulo ou negativo
     */
    public void setPreco(BigDecimal preco) {
        if (preco == null || preco.signum() == -1 ) {
            throw new IllegalArgumentException("Preço não pode ser nulo ou negativo.");
        }

        this.preco = preco;
    }

    public int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    /**
     * Define a quantidade disponível em estoque.
     *
     * @param quantidadeEmEstoque quantidade em estoque, não pode ser negativa
     * @throws IllegalArgumentException se a quantidade for negativa
     */
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
        if (quantidadeDesejada <= 0) {
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

    /** 
     * Valida textos utilizados nos atributos do produto.
     *
     * @param valor texto a ser validado
     * @param nomeCampo nome do campo utilizado na mensagem de erro
     * @throws IllegalArgumentException se o texto for nulo ou vazio
     */
    private void validarTexto(String valor, String nomeCampo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(nomeCampo + " não pode ser nulo ou vazio.");
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - R$ %.2f (%d em estoque) - %s", this.codigo, this.nome, this.preco, this.quantidadeEmEstoque, this.descricao);
    }

    @Override 
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Produto)) {
            return false;
        }

        Produto produto = (Produto) o;

        return this.codigo.equals(produto.codigo);
    }
}

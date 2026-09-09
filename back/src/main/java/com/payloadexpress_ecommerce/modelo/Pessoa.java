
package com.payloadexpress_ecommerce.modelo;

//Classe abstrata que constroi identificação do cliente
public abstract class Pessoa {

    private String nome;
    private String documento;

    public Pessoa(String nome, String documento) {
        setNome(nome);
        setDocumento(documento);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        this.nome = nome.trim();
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        if (documento == null || documento.isBlank()) {
            throw new IllegalArgumentException("Documento é obrigatório");
        }

        this.documento = documento.trim();
    }

    public abstract String getIdentificacao();
}
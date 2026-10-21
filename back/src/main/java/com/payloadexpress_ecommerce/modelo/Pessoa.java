
package com.payloadexpress_ecommerce.modelo;

//Classe abstrata que constroi identificação do cliente
public abstract class Pessoa {

    private String nome;
    private final String documento;

    public Pessoa(String nome, String documento) {
        setNome(nome);

        if (documento == null || !documento.matches("(?:\\d{11}|\\d{3}.\\d{3}.\\d{3}-\\d{2})")) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos (com ou sem máscara).");
        }

        this.documento = documento.replaceAll("\\D", "");
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

    public abstract String getIdentificacao();

    public String getResumo(){
        return nome + " (" + documento + ")";
    }
}
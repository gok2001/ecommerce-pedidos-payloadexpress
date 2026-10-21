package com.payloadexpress_ecommerce.modelo;

// Classe responsável pelo cadastro de clientes
// Herda dados da class Pessoa
public class Cliente extends Pessoa {

    private String email;
    private String telefone;
    private String endereco;

    public Cliente(String nome, String cpf, String email, String telefone, String endereco) {

        super(nome, cpf);

        setEmail(email);
        setTelefone(telefone);
        setEndereco(endereco);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.trim().matches("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
            throw new IllegalArgumentException("Email inválido.");
        }

        this.email = email.trim();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || !telefone.replaceAll("\\D", "").matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Telefone deve conter DDD e 10 ou 11 dígitos.");
        }

        this.telefone = telefone.replaceAll("\\D", "");
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        if (endereco == null || endereco.isBlank()) {
            throw new IllegalArgumentException("Endereço é obrigatório.");
        }
        
        this.endereco = endereco.trim();
    }

    @Override
    public String getIdentificacao() {
        return getNome() + " (CPF " + getDocumento() + ")";
    }

    public String retornaIdentificacaoCliente() {
        return "Nome: " + getNome()
                + "\nCPF: " + getDocumento()
                + "\nEmail: " + email
                + "\nTelefone: " + telefone
                + "\nEndereço: " + endereco;
    }
}
package com.payloadexpress_ecommerce.modelo;

// Classe responsável pelo cadastro de clientes
//Herda dados da class Pessoa
public class Cliente extends Pessoa {

    private String email;
    private String telefone;
    private String endereco;

    public Cliente(String nome, String CPF, String email, String telefone, String endereco) {

        super(nome, CPF);

        setEmail(email);
        setTelefone(telefone);
        setEndereco(endereco);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String getIdentificacao() {
        return getNome() + " (CPF " + getDocumento() + ")";
    }

    public String retornaIdentificacaoCliente() {
        return "Nome: " + getNome()
                + "\nCPF: " + getDocumento()
                + "\nEmail: " + getEmail()
                + "\nTelefone: " + getTelefone()
                + "\nEndereço: " + getEndereco();
    }
}
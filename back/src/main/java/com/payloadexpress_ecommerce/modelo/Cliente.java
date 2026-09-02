package com.payloadexpress_ecommerce.modelo;

//Classe responsavel pelo cadastro de clientes
public class Cliente {

    private String nome;
    private String CPF;
    private String Email;
    private String telefone;
    private String endereco;

    public Cliente(String nome, String CPF, String Email, String telefone, String endereco) {
        this.nome = nome;
        this.CPF = CPF;
        this.Email = Email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public String getnome(){
        return nome;
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public String getCPF(){
        return CPF;
    }

    public void setCPF(String CPF){
        this.CPF = CPF;
    }

    public String Email(){
        return Email;
    }

    public void setEmail(String Email){
        this.Email = Email;
    }

    public String getTelefone(){
        return telefone;
    }

    public void setTelefone(String telefone){
        this.telefone = telefone; 
    }

    public String getEndereco(){
        return endereco;
    }

    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    public String retornaIdentificacaoCliente(){
        return "Nome: " + nome +
                "\nCPF: " + CPF +
                "\nEmail: " + Email +
                "\ntelefone: " + telefone +
                "\nendereco: " + endereco;
    }

    
}
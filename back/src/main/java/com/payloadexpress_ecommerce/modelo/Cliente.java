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

    public void setnome (String nome) {
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

    public String gettelefone(){
        return telefone;
    }

    public void settelefone(String telefone){
        this.telefone = telefone; 
    }

    public String getendereco(){
        return endereco;
    }

    public void setendereco(String endereco){
        this.endereco = endereco;
    }

    public String pessoa(){
        return "Nome: " + nome +
                "\nCPF: " + CPF +
                "\nEmail: " + Email +
                "\ntelefone: " + telefone +
                "\nendereco: " + endereco;
    }

    
}
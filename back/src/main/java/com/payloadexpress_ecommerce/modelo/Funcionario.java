package com.payloadexpress_ecommerce.modelo;

public class Funcionario extends Pessoa {

    private String matricula;
    private String cargo;

    public Funcionario(String nome, String cpf, String matricula, String cargo){
        
        super(nome, cpf);
        setMatricula(matricula);
        setCargo(cargo);
    }

    public void setMatricula(String matricula) {
        if (matricula == null || matricula.isBlank()){
            throw new IllegalArgumentException("Matrícula é obrigatória");
        }
        this.matricula = matricula;
    }

    @Override 
    public String getIdentificacao() {
        return getNome() + " -matrícula " + matricula;
    }
    
    @Override
    public String getResumo() {
        return  super.getResumo() + " - " + cargo;
    } 
}

package com.veterinaria;

import java.time.LocalDate;

public abstract class Funcionario extends Pessoa{
    private int id;
    private float salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNasc, String cpf, Endereço endereço, Telefone telefone, String email, int id, float salario, String funcao){
        super(nome, dataNasc, cpf, endereço, telefone, email);
        this.id = id;
        this.salario = salario;
        this.funcao = funcao;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setSalario(float salario) {
        this.salario = salario;
    }
    public float getSalario() {
        return salario;
    }
    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
    public String getFuncao() {
        return funcao;
    }

    @Override
    public String exibeDados(){
        String texto = super.exibeDados();
        texto += "Id: " + getId() + "\n";
        texto += "Salário: " + getSalario() + "\n";
        texto += "Função: " + getFuncao() + "\n";
        return texto;
    }
}

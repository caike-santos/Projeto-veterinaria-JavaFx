package com.veterinaria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Funcionario extends Pessoa{
    private int id;
    private float salario;
    private String profissao;
    private List<Servicos> funcoes = new ArrayList<>();

    public Funcionario(String nome, LocalDate dataNasc, String cpf, Endereço endereço, Telefone telefone, String email, int id, float salario, String profissao, List<Servicos> funcoes) {
        super(nome, dataNasc, cpf, endereço, telefone, email);
        this.id = id;
        this.salario = salario;
        this.profissao = profissao;
        this.funcoes = funcoes;
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
    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }
    public String getProfissao() {
        return profissao;
    }
    public List<Servicos> getFuncoes() {
        return funcoes;
    }
    public void setFuncoes(List<Servicos> funcoes) {
        this.funcoes = funcoes;
    }
    public List<String> funcoesRealizadas() {
        List<String> funcoesRealizadas = new ArrayList<>();
        for (Servicos servico : funcoes) {
            if(servico.getProfissional().equals(getProfissao())) {
                funcoesRealizadas.add(servico.getNome());
            }
        }
        return funcoesRealizadas;
    }
    @Override
    public String exibeDados(){
        String texto = super.exibeDados();
        texto += "Id: " + getId() + "\n";
        texto += "Salário: " + getSalario() + "\n";
        texto += "Profissão: " + getProfissao() + "\n";
        return texto;
    }
}

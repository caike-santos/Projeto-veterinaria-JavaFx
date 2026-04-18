package com.veterinaria;

import java.time.LocalDate;

public class Tosador extends Funcionario{
    public Tosador(String nome, LocalDate dataNascimento, String cpf, Endereço endereço, Telefone telefone, String email, int id, float salario, String funcao){
        super(nome, dataNascimento, cpf, endereço, telefone, email, id, salario, funcao);
    }

        @Override
        public String exibeDados(){
        String dados = super.exibeDados();
        return dados;
    }

}

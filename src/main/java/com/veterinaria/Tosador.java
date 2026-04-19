package com.veterinaria;

import java.time.LocalDate;
import java.util.List;

public class Tosador extends Funcionario{
    public Tosador(String nome, LocalDate dataNascimento, String cpf, Endereço endereço, Telefone telefone, String email, int id, float salario, String profissao, List<Servicos> funcoes) {
        super(nome, dataNascimento, cpf, endereço, telefone, email, id, salario, profissao, funcoes);
    }

        @Override
        public String exibeDados(){
        String dados = super.exibeDados();
        return dados;
    }

}

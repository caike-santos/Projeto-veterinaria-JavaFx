package com.veterinaria.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@DiscriminatorValue("Tosador")
public class Tosador extends Funcionario {
    public Tosador(String nome, LocalDate dataNascimento, String cpf, Endereco endereco, Telefone telefone, String email, UUID id, float salario, List<Servico> funcoes, String usuario, String senha) {
        super(nome, dataNascimento, cpf, endereco, telefone, email, id, salario, Profissao.Tosador, funcoes, usuario, senha);
    }
    public Tosador(){}

       @Override
        public String exibeDados(){
           return super.exibeDados();
    }

}

package com.veterinaria.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@DiscriminatorValue("Atendente")
public class Atendente extends Funcionario {
    public Atendente(String nome, LocalDate dataNascimento, String cpf, Endereco endereco, Telefone telefone, String email, UUID id, float salario, String profissao, List<Servico> funcoes) {
        super(nome, dataNascimento, cpf, endereco, telefone, email, id, salario, profissao, funcoes);
    }
    public Atendente(){}

   

    
}

package com.veterinaria.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@DiscriminatorValue("Tosador")
public class Tosador extends Funcionario {
    public Tosador(String nome, LocalDate dataNascimento, String cpf, Endereco endereco, Telefone telefone, String email, BigDecimal salario, List<Servico> funcoes, String usuario, String senha) {
        super(nome, dataNascimento, cpf, endereco, telefone, email, salario, Profissao.Tosador, funcoes, usuario, senha);
    }
    public Tosador(){}

       @Override
        public String exibeDados(){
           return super.exibeDados();
    }

}

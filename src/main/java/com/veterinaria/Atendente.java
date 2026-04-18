package com.veterinaria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;

public class Atendente extends Funcionario{
    public Atendente(String nome, LocalDate dataNascimento, String cpf, Endereço endereço, Telefone telefone, String email, int id, float salario, String funcao) {
        super(nome, dataNascimento, cpf, endereço, telefone, email, id, salario, funcao);
    }

   

    
}

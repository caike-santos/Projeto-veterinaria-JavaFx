package com.veterinaria.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@DiscriminatorValue("Medico")
public class Medico extends Funcionario {
    private String CMRV;
    public Medico(String nome, LocalDate dataNascimento, String cpf, Endereco endereco, Telefone telefone, String email, BigDecimal salario, List<Servico> funcoes, String CMRV, String usuario, String senha){
        super(nome, dataNascimento, cpf, endereco, telefone, email, salario,Profissao.Medico_Veterinario, funcoes, usuario, senha);
        this.CMRV = CMRV;
    }
    public Medico(){}
    @Override
    public String exibeDados(){
        String dados = super.exibeDados();
        dados += "\nCMRV: " + getCMRV();
        return dados;
    }
}

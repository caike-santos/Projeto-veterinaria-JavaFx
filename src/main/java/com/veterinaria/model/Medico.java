package com.veterinaria.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@DiscriminatorValue("Medico")
public class Medico extends Funcionario {
    private String cmrv;
    public Medico(String nome, LocalDate dataNascimento, String cpf, Endereco endereco, Telefone telefone, String email, UUID id, float salario, String profissao, List<Servico> funcoes, String cmrv){
        super(nome, dataNascimento, cpf, endereco, telefone, email, id, salario, profissao, funcoes);
        this.cmrv = cmrv;
    }
    public Medico(){}
    public void setCmrv(String cmrv) {
        this.cmrv = cmrv;
    }
    public String getCmrv() {
        return cmrv;
    }

    @Override
    public String exibeDados(){
        String dados = super.exibeDados();
        dados += "\nCMRV: " + getCmrv();
        return dados;
    }
}

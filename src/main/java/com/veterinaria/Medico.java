package com.veterinaria;

import java.time.LocalDate;

public class Medico extends Funcionario{
    private String cmrv;
    public Medico(String nome, LocalDate dataNascimento, String cpf, Endereço endereço, Telefone telefone, String email, int id, float salario, String funcao, String cmrv){
        super(nome, dataNascimento, cpf, endereço, telefone, email, id, salario, funcao);
        this.cmrv = cmrv;
    }
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

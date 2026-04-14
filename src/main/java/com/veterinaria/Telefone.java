package com.veterinaria;

public class Telefone {
    private String ddd = "00";
    private String numero = "00000-0000";

    public Telefone(String ddd, String numero){
        this.ddd = ddd;
        this.numero = numero;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }
    public String getDdd() {
        return ddd;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getNumero() {
        return numero;
    }

    
    public String exibeTelefone(){
        return "Telefone: (" + getDdd() + ") " + getNumero();
    }
    
}

package com.veterinaria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Telefone {
    private String ddd = "00";

    @Column(name = "numero_telefone")
    private String numero = "00000-0000";

    public Telefone(String ddd, String numero){
        this.ddd = ddd;
        this.numero = numero;
    }
    public Telefone(){}


    public String exibeTelefone(){
        return "Telefone: (" + getDdd() + ") " + getNumero();
    }
    
}

package com.veterinaria.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Endereco {
    private String bairro = "null";
    private String rua = "null";
    private int numero = 0;
    private String cep = "null";
    private String complemento = "null";

    public Endereco(String bairro, String rua, int numero, String cep, String complemento){
        this.bairro = bairro;
        this.cep = cep;
        this.numero = numero;
        this.rua = rua;
        this.complemento = complemento;

    }
    public Endereco(){}

    public String exibeEndereço(){
        return "\nRua: " + getRua() +
        "\nBairro: " + getBairro() +
        "\nNumero: " + getNumero() +
        "\nCep: " + getCep() +
        "\nComplemento: " + getComplemento() + "\n";
    }
    
}

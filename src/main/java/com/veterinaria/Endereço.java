package com.veterinaria;

public class Endereço {
    private String bairro = "null";
    private String rua = "null";
    private int numero = 0;
    private String cep = "null";
    private String complemento = "null";

    public Endereço(String bairro, String rua, int numero, String cep, String complemento){
        this.bairro = bairro;
        this.cep = cep;
        this.numero = numero;
        this.rua = rua;
        this.complemento = complemento;

    }

    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public String getRua() {
        return rua;
    }
    public void setRua(String rua) {
        this.rua = rua;
    }
    

    public String exibeEndereço(){
        return "\nRua: " + getRua() +
        "\nBairro: " + getBairro() +
        "\nNumero: " + getNumero() +
        "\nCep: " + getCep() +
        "\nComplemento: " + getComplemento() + "\n";
    }
    
}

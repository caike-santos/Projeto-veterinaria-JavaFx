package com.veterinaria;

public class Servicos {
    private String nome;
    private float preco;
    private String profissional;

    public Servicos(String nome, float preco, String profissional) {
        this.nome = nome;
        this.preco = preco;
        this.profissional = profissional;

    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfissional() {
        return profissional;
    }

    public void setProfissional(String profissional) {
        this.profissional = profissional;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
    public String exibeDados(){
        String dados =  getNome() + "\nR$ " + getPreco() + "\nProfissional: " + getProfissional();
        return dados;
    }
}

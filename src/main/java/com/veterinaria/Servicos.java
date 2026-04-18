package com.veterinaria;

public class Servicos {
    private String nome;
    private float preco;

    public Servicos(String nome, float preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
    public String exibeDados(){
        String dados =  getNome() + "\nR$ " + getPreco() + "\n";
        return dados;
    }
}

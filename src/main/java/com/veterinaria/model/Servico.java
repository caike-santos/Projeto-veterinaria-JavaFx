package com.veterinaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Data
@Entity(name = "servicos")
@SQLDelete(sql = "UPDATE servicos Set ativo = false WHERE id = ? AND versao = ?")
@SQLRestriction("ativo=true")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @PositiveOrZero(message = "O preço tem que ser positivo")
    private float preco;

    @NotBlank(message = "A profissão é obrigatoria")
    private String profissional;

    private boolean ativo = true;
    @Version
    private Long versao;

    public Servico(String nome, float preco, String profissional) {
        this.nome = nome;
        this.preco = preco;
        this.profissional = profissional;
    }
    public Servico(){}

    public String exibeDados(){
        String dados =  getNome() + "\nR$ " + getPreco() + "\nProfissional: " + getProfissional();
        return dados;
    }
}

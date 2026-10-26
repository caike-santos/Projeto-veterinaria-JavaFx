package com.veterinaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "A profissão é obrigatoria")
    @Enumerated(EnumType.STRING)
    private Profissao profissao;

    private boolean ativo = true;

    @Version
    private Long versao;

    public Servico(String nome, float preco, Profissao profissao) {
        this.nome = nome;
        this.preco = preco;
        this.profissao = profissao;
    }
    public Servico(){}

    public String exibeDados(){
        return getNome() + "\nR$ " + getPreco() + "\nProfissional: " + getProfissao();
    }
}

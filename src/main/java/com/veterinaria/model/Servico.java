package com.veterinaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "servicos")
@SQLDelete(sql = "UPDATE servicos Set ativo = false WHERE id = ? AND versao = ?")
@SQLRestriction("ativo=true")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @PositiveOrZero(message = "O preço tem que ser positivo")
    private BigDecimal preco;

    @NotNull(message = "A profissão é obrigatoria")
    @Enumerated(EnumType.STRING)
    private Profissao profissao;

    private boolean ativo = true;

    @Version
    private Long versao;

    public Servico(String nome, BigDecimal preco, Profissao profissao) {
        this.nome = nome;
        this.preco = preco;
        this.profissao = profissao;
    }
    public Servico(){}

    public String exibeDados(){
        return getNome() + "\nR$ " + getPreco() + "\nProfissional: " + getProfissao();
    }
}

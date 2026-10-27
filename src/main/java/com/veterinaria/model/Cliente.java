package com.veterinaria.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity(name = "clientes")
@SQLDelete(sql = "UPDATE clientes Set ativo = false WHERE id = ? AND versao = ?")
@SQLRestriction("ativo=true")
public class Cliente extends Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Valid
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets = new ArrayList<>();

    public Cliente(String nome, LocalDate dataNascimento, String cpf, Endereco endereco, Telefone telefone, String email, List<Pet> pets) {
        super(nome, dataNascimento, cpf, endereco, telefone, email);
        this.pets = pets;
    }

    private boolean ativo = true;
    @Version
    private Long versao;

    public Cliente(){}
}

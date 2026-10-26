package com.veterinaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "funcionarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_funcionario")
@SQLDelete(sql = "UPDATE funcionarios Set ativo = false WHERE id = ? AND versao = ?")
@SQLRestriction("ativo=true")
public abstract class Funcionario extends Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Positive
    private float salario;

    @NotBlank(message = "A profissão é obrigatória")
    private String profissao;

    @ManyToMany(fetch = FetchType.EAGER)
    @NotEmpty(message = "As funções são obrigatórias")
    private List<Servico> funcoes = new ArrayList<>();

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    private boolean ativo = true;
    @Version
    private Long versao;

    public Funcionario(String nome, LocalDate dataNasc, String cpf, Endereco endereco, Telefone telefone, String email, UUID id, float salario, String profissao, List<Servico> funcoes) {
        super(nome, dataNasc, cpf, endereco, telefone, email);
        this.id = id;
        this.salario = salario;
        this.profissao = profissao;
        this.funcoes = funcoes;
    }

    public Funcionario(){}

    public List<String> funcoesRealizadas() {
        List<String> funcoesRealizadas = new ArrayList<>();
        for (Servico servico : funcoes) {
            if(servico.getProfissional().equals(this.getProfissao())) {
                funcoesRealizadas.add(servico.getNome());
            }
        }
        return funcoesRealizadas;
    }
    @Override
    public String exibeDados(){
        String texto = super.exibeDados();
        texto += "Id: " + getId() + "\n";
        texto += "Salário: " + getSalario() + "\n";
        texto += "Profissão: " + getProfissao() + "\n";
        return texto;
    }
}

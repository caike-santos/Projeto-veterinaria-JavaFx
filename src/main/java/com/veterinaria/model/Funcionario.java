package com.veterinaria.model;

import com.veterinaria.App;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity(name = "funcionarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_funcionario")
@SQLDelete(sql = "UPDATE funcionarios Set ativo = false WHERE id = ? AND versao = ?")
@SQLRestriction("ativo=true")
public abstract class Funcionario extends Pessoa {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Positive
    private BigDecimal salario;

    @NotNull(message = "A profissão é obrigatória")
    @Enumerated(EnumType.STRING)
    private Profissao profissao;

    @ManyToMany(fetch = FetchType.EAGER)
    @NotEmpty(message = "As funções são obrigatórias")
    private List<Servico> funcoes = new ArrayList<>();

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Column(nullable = false, unique = true)
    private String usuario;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    private boolean ativo = true;
    @Version
    private Long versao;

    public Funcionario(String nome, LocalDate dataNasc, String cpf, Endereco endereco, Telefone telefone, String email, BigDecimal salario, Profissao profissao, List<Servico> funcoes, String usuario, String senha) {
        super(nome, dataNasc, cpf, endereco, telefone, email);
        this.salario = salario;
        this.profissao = profissao;
        this.funcoes = funcoes;
        this.usuario = usuario;
        this.senha = ENCODER.encode(senha);
    }


    public Funcionario(){}

    public List<String> funcoesRealizadas() {
        List<String> funcoesRealizadas = new ArrayList<>();
        for (Servico servico : funcoes) {
            if(servico.getProfissao().equals(this.getProfissao())) {
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

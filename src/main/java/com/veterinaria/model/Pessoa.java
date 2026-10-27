package com.veterinaria.model;

import com.veterinaria.utils.Datas;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.Period;
@MappedSuperclass
@Getter
@Setter
public abstract class Pessoa {
    @Column(nullable = false)
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Column(nullable = false)
    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data tem que estar no passado")
    private LocalDate dataNascimento;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "O cpf é obrigatório")
    @CPF(message = "formato do cpf invalido")
    private String cpf;

    @Valid
    @Embedded
    @NotNull(message = "O endereço é obrigatório")
    private Endereco endereco;

    @Valid
    @Embedded
    @NotNull(message = "O telefone é obrigatório")
    private Telefone telefone;

    @Column(nullable = false)
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "formato de e-mail invalido")
    private String email;

    public Pessoa(String nome, LocalDate dataNascimento, String cpf, Endereco endereco, Telefone telefone, String email){
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }
    public Pessoa(){};

    public String exibeDados(){
        return "\nDados da pessoa:" +
        "\nNome: " + getNome() +
        "\nIdade: " + Datas.idade(this.getDataNascimento()) +
        "\ncpf: " + getCpf() +
        this.endereco.exibeEndereço() +
        this.telefone.exibeTelefone() +
        "\nEmail: " + getEmail() + "\n";
    }
}

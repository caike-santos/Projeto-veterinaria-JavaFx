package com.veterinaria.model;
import com.veterinaria.utils.Datas;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@SQLDelete(sql = "UPDATE pets Set ativo = false WHERE id = ? AND versao = ?")
@SQLRestriction("ativo=true")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "pets")
public class Pet{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @Past(message = "A data tem que estar no passado")
    @NotNull(message = "A data é obrigatória")
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @NotNull(message = "O genero é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genero genero;

    @NotBlank(message = "A raça é obrigatória")
    @Column(nullable = false)
    private String raca;

    private boolean ativo = true;
    @Version
    private Long versao;

    public Pet(String nome, LocalDate dataNascimento, Genero genero, String raca){

        this.dataNascimento = dataNascimento;
        this.nome = nome;

        this.genero = genero;

        this.raca = raca;
    }
    public Pet(){}

    public void serBanhado(){
            System.out.println("Banho tomado");
    }

    public void serTosado(){
        System.out.println("Tosado");
    }

    public void serChecado(){
        System.out.println("Check-Up completo :)");
    }

    public void serVacinado(){
        System.out.println("Vacinado com sucesso");
    }

    public void serInternado(){
        System.out.println("Internaçao aceita, venha buscar seu pet no dia seguinte");
    }

    public void serOperado(){
        System.out.println("Cirurgia bem sucedida");
    }

    public String exibePet(){
        return "Nome: " + getNome() +
        //System.out.printf("Peso: %.2f\n", getPeso());
        "\nIdade: " + Datas.idade(this.getDataNascimento()) +
        //System.out.printf("Altura: %.2f\n", getAltura());
        "\nSexo: " + getGenero() +
        //System.out.println("Especie: " + getEspecie());
        "\nRaça: "+ getRaca() + "\n";
    }

}

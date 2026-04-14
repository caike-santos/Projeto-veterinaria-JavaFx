package com.veterinaria;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;
public class Pet{
    private String nome;
    //private float peso;
    private int idade;
    private LocalDate dataNascimento;
    //private float altura;
    private String sexo;
    //private String especie;
    private String raca;

    public Pet(String nome, LocalDate dataNascimento, String sexo, String raca){
        //this.altura = altura;
        this.dataNascimento = dataNascimento;
        this.nome = nome;
        //this.peso = peso;
        this.sexo = sexo;
        //this.especie = especie;
        this.raca = raca;
    }

    public int getIdade(){
        LocalDate dataAtual = LocalDate.now();
        Period periodo = Period.between(this.dataNascimento, dataAtual);
        setIdade(periodo.getYears());
        return periodo.getYears();
    }

    /*public void setAltura(float altura) {
        this.altura = altura;
    }*/
    /*public float getAltura() {
        return altura;
    }*/
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
    /*public void setPeso(float peso) {
        this.peso = peso;
    }
    public float getPeso() {
        return peso;
    }*/
    public void setSexo(String sexo) {
            this.sexo = sexo;
    }
    public String getSexo() {
        return sexo;
    }
    /*public void setEspecie(String especie) {
        this.especie = especie;
    }
    public String getEspecie() {
        return especie;
    }*/
    public void setRaca(String raca) {
        this.raca = raca;
    }
    public String getRaca() {
        return raca;
    }

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
        "\nIdade: " + getIdade() +
        //System.out.printf("Altura: %.2f\n", getAltura());
        "\nSexo: " + getSexo() +
        //System.out.println("Especie: " + getEspecie());
        "\nRaça: "+ getRaca() + "\n";
    }

}

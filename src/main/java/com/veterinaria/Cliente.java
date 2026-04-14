package com.veterinaria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa{
    private List<Pet> pet = new ArrayList<>();
    public Cliente(String nome, LocalDate dataNascimento, String cpf, Endereço endereço, Telefone telefone, String email, List<Pet> pet1){
        super(nome, dataNascimento, cpf, endereço, telefone, email);
        pet = pet1;
    }
    public List<Pet> getPet() {
        return pet;
    }
    public void setPet(List<Pet> pet1) {
        pet = pet1;
    }

    
}

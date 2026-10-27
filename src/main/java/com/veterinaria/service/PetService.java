package com.veterinaria.service;

import com.veterinaria.model.Cliente;
import com.veterinaria.model.Genero;
import com.veterinaria.model.Pet;
import com.veterinaria.repository.ClienteRepository;
import com.veterinaria.utils.Datas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Slf4j
@Service
public class PetService {
    private final ClienteRepository clienteRepository;

    public PetService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Pet cadastrarPet(String nome, LocalDate dataDeNascimento, Genero genero, String raca) {
        if (dataDeNascimento == null) {
            throw new IllegalArgumentException("Por favor, preencha a data de nascimento com uma data válida.");
        }
        if (genero == null) {
            throw new IllegalArgumentException("Por favor, preencha com o gênero correto para cadastrar o pet.");
        }

        Pet pet = new Pet(nome, dataDeNascimento, genero, raca);
        int idadeNum = Datas.idade(pet.getDataNascimento());
        if (idadeNum < 0 || idadeNum > 100) {
            throw new IllegalArgumentException("Idade do pet inválida! O pet deve ter entre 0 e 100 anos.");
        }
        log.info("\nnome: {}\nidade: {}\nraca: {}\nsexo: {}", pet.getNome(), Datas.idade(pet.getDataNascimento()), pet.getRaca(), pet.getGenero());
        return pet;
    }

    public Cliente adicionarPet(List<Pet> listaPet, Cliente c) {
        if (listaPet.isEmpty()) {
            throw new IllegalArgumentException("Faça pelo menos um cadastro de pet para poder finalizar.");
        }
        for (Pet p : listaPet) {
            c.getPets().add(p);
        }
        log.info("Pet adicionado com sucesso!");
        return clienteRepository.save(c);
    }

    public Cliente removerPet(String nomePet, Cliente c) {
        for (Pet p : c.getPets()) {
            if (p.getNome().equalsIgnoreCase(nomePet)) {
                c.getPets().remove(p);
                return clienteRepository.save(c);
            }
        }
        throw new IllegalArgumentException("O pet digitado não foi encontrado.");
    }
}

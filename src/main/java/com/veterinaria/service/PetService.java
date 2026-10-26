package com.veterinaria.service;

import com.veterinaria.model.Cliente;
import com.veterinaria.model.Genero;
import com.veterinaria.model.Pet;
import com.veterinaria.repository.ClienteRepository;
import com.veterinaria.repository.PetRepository;
import com.veterinaria.view.CadastroCliente;
import com.veterinaria.view.CadastroPet;
import com.veterinaria.view.EditarCliente;
import jakarta.transaction.Transactional;
import javafx.scene.control.Alert;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Slf4j
@Service
public class PetService {
    private final ClienteRepository clienteRepository;

    public PetService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public Pet cadastrarPet(String nome, LocalDate dataDeNascimento, String genero, String raca){
        if(dataDeNascimento == null){
            CadastroCliente.mostarNaTela("Data de nascimento inválida!", "Por favor, preencha a data de nascimento com uma data válida.");
            throw new IllegalArgumentException("Data invalida");
        }
        else{
            Genero gen = switch (genero) {
                case "Macho" -> Genero.Macho;
                case "Femea" -> Genero.Femea;
                default -> {
                    CadastroCliente.mostarNaTela("Genero invalido", "Por favor, preencha com o genero correto para cadastrar o pet.");
                    throw new IllegalArgumentException("Genero invalido");
                }
            };

            Pet pet = new Pet(nome, dataDeNascimento, gen, raca);
            int idadeNum = pet.getIdade();
            if(idadeNum < 0 || idadeNum > 100){
                CadastroCliente.mostarNaTela("Idade do pet inválida!", "Por favor, preencha a data de nascimento com uma data válida. O pet deve ter entre 0 e 100 anos.");
                throw new IllegalArgumentException("Idade inválida");
            }
            log.info("\nnome: "+ pet.getNome() +
                    "\nidade: "+ pet.getIdade()  +
                    "\nraca: "+ pet.getRaca() +
                    "\nsexo: "+ pet.getGenero());
            return pet;
        }
    }

    public Cliente adicionarPet(List<Pet> listaPet, Cliente c){

        if(listaPet.isEmpty()){
            CadastroCliente.mostarNaTela("Nenhum cadastro realizado", "Faça pelo menos um cadastro para poder finalizado");
            throw new IllegalArgumentException("Nenhum cadastro foi realizado");
        }
        for(Pet p : listaPet){
            c.getPet().add(p);
        }
        log.info("Pet adicionado com sucesso!");
        return clienteRepository.save(c);

    }

    public Cliente removerPet(String nomePet, Cliente c){
        for (Pet p : c.getPet()) {
            if (p.getNome().equalsIgnoreCase(nomePet)) {
                c.getPet().remove(p);
                return clienteRepository.save(c);
            }
        }
            CadastroCliente.mostarNaTela("Pet não encontrado", "O pet digitado não foi encontrado.");
            throw new IllegalArgumentException("Nenhum pet foi encontrado com esse nome");
    }
}

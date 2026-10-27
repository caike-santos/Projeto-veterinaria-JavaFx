package com.veterinaria.service;

import com.veterinaria.model.Cliente;
import com.veterinaria.repository.ClienteRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Transactional
@Slf4j
@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public Cliente buscarCliente(String cpf, String nomeAtendente) {
        if (cpf.isEmpty()) {
            throw new IllegalArgumentException(nomeAtendente + ", o campo CPF está vazio. Preencha corretamente.");
        } else if (cpf.length() != 14) {
            throw new IllegalArgumentException(nomeAtendente + ", quantidade de caracteres do CPF inválida.");
        }

        return clienteRepository.findByCpf(cpf);
    }

    public Cliente cadastrarCliente(@Valid Cliente c) {
        if (clienteRepository.findByCpf(c.getCpf()) != null) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com esse CPF.");
        }
        log.info("Novo cliente cadastrado: [{}]", c.getNome());
        return clienteRepository.save(c);
    }

    public List<Cliente> listarClienteOrdemAlfabetica() {
        return clienteRepository.findAllByOrderByNomeAsc();
    }

    public Cliente atualizarDados(@Valid Cliente c) {
        log.info("Dados do cliente [{}] atualizados com sucesso!", c.getNome());
        return clienteRepository.save(c);
    }
}

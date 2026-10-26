package com.veterinaria.service;

import com.veterinaria.model.Cliente;
import com.veterinaria.repository.ClienteRepository;
import com.veterinaria.view.CadastroCliente;

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
    public Cliente buscarCliente(String cpf, String nomeAtendente){
        if(cpf.isEmpty()){
            CadastroCliente.mostarNaTela("Campo Vazio", nomeAtendente + ", preencha o campo corretamente");
            throw new IllegalArgumentException("Campo cpf vazio");
        }else if(cpf.length() != 14){
            CadastroCliente.mostarNaTela("Quantidade de caracteres inválidos", nomeAtendente + ", preencha o campo corretamente");
            throw new IllegalArgumentException("Campo cpf faltando caracteres");
        }

        return clienteRepository.findByCpf(cpf);

    }

    public Cliente cadastrarCliente(@Valid Cliente c){
        if (clienteRepository.findByCpf(c.getCpf()) != null) {
            CadastroCliente.mostarNaTela("CPF já cadastrado!", "Já existe um cliente com esse CPF.");
            throw new IllegalArgumentException("CPF duplicado");
        }
        log.info("Novo cliente cadastrado: [{}]", c.getNome());
        return clienteRepository.save(c);
    }

    public List<Cliente> listarClienteOrdemAlfabetica(){
        return clienteRepository.findAllByOrderByNomeAsc();
    }

    public Cliente atualizarDados(@Valid Cliente c){
        log.info("Dados do cliente [{}] atualizados com sucesso!", c.getNome());
        return clienteRepository.save(c);
    }

}


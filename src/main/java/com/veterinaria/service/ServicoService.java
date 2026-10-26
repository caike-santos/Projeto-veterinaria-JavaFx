package com.veterinaria.service;

import com.veterinaria.model.Servico;
import com.veterinaria.repository.ServicoRepository;
import com.veterinaria.view.CadastroCliente;
import com.veterinaria.view.EditarCliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@Service
public class ServicoService {
    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public List<Servico> listarServicos(){
        List<Servico> servicos = servicoRepository.findAll();
        if(servicos.isEmpty()){
            EditarCliente.mostarNaTela("Serviços inexistentes", "Nenhum serviço foi cadastrado até o momento");
            throw new IllegalArgumentException("Serviços inexistentes");
        }else {
            return servicos;
        }
    }
}

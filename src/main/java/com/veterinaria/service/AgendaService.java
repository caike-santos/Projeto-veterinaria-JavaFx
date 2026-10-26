package com.veterinaria.service;

import com.veterinaria.model.Agenda;
import com.veterinaria.model.Cliente;
import com.veterinaria.repository.AgendaRepository;
import com.veterinaria.view.CadastroCliente;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Validated
@Transactional
@Slf4j
@Service
public class AgendaService {
    private final AgendaRepository agendaRepository;

    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public List<Agenda> listarAgendasOrdemDataHora(Cliente cliente){
        return agendaRepository.findByClienteOrderByDataAscHoraAsc(cliente);
    }

    public void removerAgendas(Cliente cliente, LocalDate data, LocalTime hora){
       Agenda agenda = agendaRepository.findByClienteAndDataAndHora(cliente, data, hora);
       if(agenda == null){
           CadastroCliente.mostarNaTela("Agendamento não encontrado", "O agendamento digitado não foi encontrado.");
           throw new IllegalArgumentException("Agendamento não encontrado");
       }else{
           agendaRepository.delete(agenda);
       }

    }

    public void cadastrarAgenda(@Valid Agenda agenda){
        if(agendaRepository.findByClienteAndDataAndHora(agenda.getCliente(), agenda.getData(), agenda.getHora()) != null){
            CadastroCliente.mostarNaTela("Agendamento já existente", "Já tem uma agenda para esse cliente na mesma data e hora.");
            throw new IllegalArgumentException("Agendamento na mesma data e hora pro mesmo cliente");
        }else{
            agendaRepository.save(agenda);
        }
    }
}

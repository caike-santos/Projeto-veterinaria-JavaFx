package com.veterinaria.service;

import com.veterinaria.model.Agenda;
import com.veterinaria.model.Cliente;
import com.veterinaria.repository.AgendaRepository;
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

    public List<Agenda> listarAgendasOrdemDataHora(Cliente cliente) {
        return agendaRepository.findByClienteOrderByDataAscHoraAsc(cliente);
    }

    public void removerAgendas(Cliente cliente, LocalDate data, LocalTime hora) {
        Agenda agenda = agendaRepository.findByClienteAndDataAndHora(cliente, data, hora);
        if (agenda == null) {
            throw new IllegalArgumentException("O agendamento digitado não foi encontrado.");
        }
        agendaRepository.delete(agenda);
    }

    public void cadastrarAgenda(@Valid Agenda agenda) {
        if (agendaRepository.findByClienteAndDataAndHora(agenda.getCliente(), agenda.getData(), agenda.getHora()) != null) {
            throw new IllegalArgumentException("Já tem uma agenda para esse cliente na mesma data e hora.");
        }
        agendaRepository.save(agenda);
    }
}

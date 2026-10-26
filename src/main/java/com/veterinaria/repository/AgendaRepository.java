package com.veterinaria.repository;

import com.veterinaria.model.Agenda;
import com.veterinaria.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface AgendaRepository extends JpaRepository<Agenda, UUID> {

    List<Agenda> findByClienteOrderByDataAscHoraAsc(Cliente cliente);

    Agenda findByClienteAndDataAndHora(Cliente cliente, LocalDate data, LocalTime hora);
}

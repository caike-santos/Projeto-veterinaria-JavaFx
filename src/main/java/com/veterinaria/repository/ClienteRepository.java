package com.veterinaria.repository;

import com.veterinaria.model.Cliente;
import com.veterinaria.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Cliente findByCpf(String cpf);

    List<Cliente> findAllByOrderByNomeAsc();
}

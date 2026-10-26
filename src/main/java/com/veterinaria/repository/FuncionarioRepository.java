package com.veterinaria.repository;

import com.veterinaria.model.Funcionario;
import com.veterinaria.model.Profissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
    Funcionario findByUsuario(String usuario);

    List<Funcionario> findByFuncoesNome(String nomeServico);
}

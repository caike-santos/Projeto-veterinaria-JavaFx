package com.veterinaria.repository;

import com.veterinaria.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
    Funcionario findByNomeAndProfissao(String nome, String profissao);

    List<Funcionario> findByFuncoesNome(String nomeServico);
}

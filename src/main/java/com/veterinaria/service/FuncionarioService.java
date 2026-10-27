package com.veterinaria.service;

import com.veterinaria.model.Funcionario;
import com.veterinaria.repository.FuncionarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@Service
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, PasswordEncoder passwordEncoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Funcionario login(String usuario, String senha) {
        Funcionario funcionario = funcionarioRepository.findByUsuario(usuario);

        if (funcionario == null || !passwordEncoder.matches(senha, funcionario.getSenha())) {
            log.warn("Falha no login: credenciais inválidas para o Usuário [{}]", usuario);
            throw new IllegalArgumentException("Usuário ou senha incorretos. Faça o login novamente.");
        }
        return funcionario;
    }

    public List<Funcionario> buscarPorServico(String nomeServico) {

        return funcionarioRepository.findByFuncoesNome(nomeServico);
    }
}

package com.veterinaria.service;

import com.veterinaria.App;
import com.veterinaria.model.Atendente;
import com.veterinaria.model.Funcionario;
import com.veterinaria.model.Profissao;
import com.veterinaria.repository.FuncionarioRepository;
import com.veterinaria.view.CadastroCliente;
import com.veterinaria.view.EditarCliente;
import com.veterinaria.view.Home;
import jakarta.transaction.Transactional;
import javafx.scene.control.Alert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public Funcionario login(String usuario, String senha){
        Funcionario funcionario = funcionarioRepository.findByUsuario(usuario);

        if(funcionario == null || !passwordEncoder.matches(senha, funcionario.getSenha())){
            log.warn("Falha no login: credenciais inválidas para o Usuário [{}]", usuario);
            EditarCliente.mostarNaTela("Usuário ou senha incorretos", "Faça o login novamente");
            throw new IllegalArgumentException("Usuário ou senha incorretos");
        }
        return funcionario;
    }

    public List<Funcionario> buscarPorServico(String nomeServico){
        List<Funcionario> funcionarios = funcionarioRepository.findByFuncoesNome(nomeServico);
        if(funcionarios.isEmpty()){
            EditarCliente.mostarNaTela("Funcionário inexistentes", "Nenhum Funcionário foi cadastrado até o momento para esse serviço");
            throw new IllegalArgumentException("Funcionário inexistentes para esse serviço");
        }else{
            return funcionarios;
        }
    }

    public List<Funcionario> listarFuncionarios(){
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        if(funcionarios.isEmpty()){
            EditarCliente.mostarNaTela("Funcionário inexistentes", "Nenhum Funcionário foi cadastrado até o momento");
            throw new IllegalArgumentException("Funcionário inexistentes");
        }else{
            return funcionarios;
        }
    }
}

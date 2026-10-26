package com.veterinaria.view;

import java.time.LocalDate;

import com.veterinaria.*;
import com.veterinaria.model.Atendente;
import com.veterinaria.model.Cliente;
import com.veterinaria.model.Pet;
import com.veterinaria.service.ClienteService;
import jakarta.validation.ConstraintViolationException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Cadastro {
    public static Parent criarCena(Atendente atendente) {
        TabPane painelDeAbas = new TabPane();

        Tab abaCliente = new Tab("Dados do Cliente");
        abaCliente.setClosable(false);

        abaCliente.setContent(CadastroCliente.criarCena(atendente));

        Tab abaPet = new Tab("Dados do Pet");
        abaPet.setClosable(false);

        abaPet.setContent(CadastroPet.criarCena());

        painelDeAbas.getTabs().addAll(abaCliente, abaPet);

        Button btnSalvarTudo = new Button("Salvar Cliente e Pet");
        btnSalvarTudo.getStyleClass().add("btn_salvar");

        Button btnCancelar = new Button("Cancelar");

        btnCancelar.setOnAction(evento -> {
            Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
            confirmar.setTitle("Atenção");
            confirmar.setHeaderText("CANCELAR CADASTRO!!!");
            confirmar.setContentText("TEM CERTEZA QUE QUER CANCELAR?");

            ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnNao = new ButtonType("Não", javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE);

            confirmar.getButtonTypes().setAll(btnSim, btnNao);

            confirmar.showAndWait().ifPresent(resposta -> {
                if (resposta == btnSim) {
                    CadastroPet.getCadastro().clear();
                    CadastroPet.getListaPet().clear();
                    App.inserirCena(Home.criarCena(atendente));
                }
            });


        });

        HBox rodape = new HBox(50);
        rodape.setAlignment(Pos.CENTER);


        rodape.setPadding(new Insets(20, 30, 30, 30));


        rodape.getChildren().addAll(btnSalvarTudo, btnCancelar);

        btnSalvarTudo.setOnAction(evento -> {
            try {
                CadastroCliente.setDpIdade(CadastroCliente.getDpIdade().getConverter().fromString(CadastroCliente.getDpIdade().getEditor().getText()));
            } catch (Exception ex) {
                CadastroCliente.setDpIdade(null); // Se deu erro na conversão, vira nulo para ser barrado no if
            }
            String cadastroAtual = CadastroPet.getCadastro().getText().trim();

            try {
                if (CadastroCliente.temCampoVazioEndereco()) {
                    EditarCliente.mostarNaTela("Faltam dados do endereço do Cliente!", "Por favor, volte na aba 'Dados do Cliente' e preencha todos os campos.");
                    return;
                } else if (CadastroCliente.temCampoVazioDados()) {
                    EditarCliente.mostarNaTela("Faltam dados pessoais do Cliente!", "Por favor, volte na aba 'Dados do Cliente' e preencha todos os campos.");
                    return;
                } else if (cadastroAtual.isEmpty()) {
                    EditarCliente.mostarNaTela("Nenhum cadastro realizado", "Faça pelo menos um cadastro para poder finalizado");
                    return;
                }

                int idadeNum = Cliente.getIdade(CadastroCliente.getDpIdade().getValue());

                if (CadastroCliente.getTxtTelefone().getLength() < 9 || CadastroCliente.getTxtDdd().getLength() < 2) {
                    EditarCliente.mostarNaTela("Quantidade de caracteres do ddd e telefone invalido", "Por favor, preencha o campo ddd e telefone corretamente");
                } else if (idadeNum < 18 || idadeNum > 120) {
                    EditarCliente.mostarNaTela("Idade inválida", "Por favor, preencha o campo idade com uma data de nascimento válida. O cliente deve ter entre 18 e 120 anos.");
                } else if (CadastroCliente.getTxtCpf().getLength() < 14) {
                    EditarCliente.mostarNaTela("Quantidade de caracteres do cpf invalido", "Por favor, preencha o campo cpf corretamente");
                } else if (CadastroCliente.getTxtCep().getLength() < 11) {
                    EditarCliente.mostarNaTela("Quantidade de caracteres do cep invalido", "Por favor, preencha o campo cep corretamente");
                } else if (CadastroCliente.emailInvalido()) {
                    EditarCliente.mostarNaTela("E-mail Inválido", "Por favor, digite um e-mail no formato correto (exemplo@dominio.com).");
                } else {
                    Cliente c = CadastroCliente.pegarDados(CadastroPet.getListaPet());
                    ClienteService clienteService = App.getBean(ClienteService.class);
                    Cliente clienteSalvo = clienteService.cadastrarCliente(c);

                    Alert concluido = new Alert(Alert.AlertType.INFORMATION);
                    concluido.setTitle("Cadastro Finalizado");
                    concluido.setHeaderText("Imprimindo Cadastro");
                    concluido.setContentText(exibirDados(clienteSalvo));
                    concluido.showAndWait();

                    CadastroPet.getCadastro().clear();
                    CadastroPet.getListaPet().clear();
                    App.inserirCena(Home.criarCena(atendente));
                }
            } catch (IllegalArgumentException | ConstraintViolationException e) {
                log.warn("Erro de validação: {}", e.getMessage());
            } catch (Exception e) {
                log.error("Erro inesperado:", e);
            }
        });

        VBox telaPrincipal = new VBox();
        telaPrincipal.setAlignment(Pos.CENTER);
        VBox.setVgrow(painelDeAbas, Priority.ALWAYS);

        telaPrincipal.getChildren().addAll(painelDeAbas, rodape);

        return telaPrincipal;
    }


    public static String exibirDados(Cliente c) {
        StringBuilder exibirDados = new StringBuilder();
        exibirDados = new StringBuilder(c.exibeDados());
        for (Pet pet : c.getPet()) {
            exibirDados.append("\nPet ").append(c.getPet().indexOf(pet) + 1).append("\n").append(pet.exibePet());
        }
        return exibirDados.toString();
    }
}

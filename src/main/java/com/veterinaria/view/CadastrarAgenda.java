package com.veterinaria.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.veterinaria.App;
import com.veterinaria.model.*;
import com.veterinaria.service.AgendaService;
import com.veterinaria.service.FuncionarioService;
import com.veterinaria.service.ServicoService;
import com.veterinaria.utils.Acoes;
import com.veterinaria.utils.Alertas;
import com.veterinaria.utils.Componentes;
import com.veterinaria.utils.Datas;
import com.veterinaria.utils.Mascaras;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CadastrarAgenda {
    private static ComboBox<Servico> cbmServico;
    private static DatePicker data;
    private static TextField txtHora;
    private static ComboBox<Funcionario> cbmFuncionario;
    private static ComboBox<Pet> cbmPet;

    public static void abrirJanela() {
        Stage popup = new Stage();
        popup.setTitle("Cadastrar Agenda");

        HBox header = Componentes.header("Cadastrar Agenda");

        Label lblServico = new Label("Serviço:");
        cbmServico = new ComboBox<>();
        Componentes.configurarComboBox(cbmServico, Servico::getNome);
        ServicoService servicoService = App.getBean(ServicoService.class);
        List<Servico> servicos = servicoService.listarServicos();
        if(servicos == null){
            Alertas.info("Nenhum serviço foi cadastrado até o momento.", "Aguarde até o sistema cadastrar serviços");
            return;
        }
        for (Servico servico : servicos) {
            if (servico.getProfissao() != Profissao.Atendente) {
                cbmServico.getItems().add(servico);
            }
        }
        cbmServico.setPromptText("Selecione o serviço");
        cbmServico.setMaxWidth(250);

        Label lblData = new Label("Data do agendamento:");
        data = new DatePicker();
        Datas.configurarPickerBR(data, true);
        data.setPromptText("Selecione a data");
        data.setMaxWidth(250);

        Label lblHora = new Label("Hora do agendamento:");
        txtHora = new TextField();
        Mascaras.aplicar(txtHora, "##:##");

        txtHora.setPromptText("Ex: 09:00");
        txtHora.setMaxWidth(250);

        Label lblFuncionario = new Label("Funcionário:");
        cbmFuncionario = new ComboBox<>();
        Componentes.configurarComboBox(cbmFuncionario, Funcionario::getNome);
        cbmFuncionario.setPromptText("Selecione o funcionário");
        cbmFuncionario.setMaxWidth(250);

        // Listener para atualizar os funcionários quando um serviço é selecionado
        cbmServico.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> Acoes.executar(() -> {
            cbmFuncionario.getItems().clear(); // Limpa os funcionários anteriores
            FuncionarioService funcionarioService = App.getBean(FuncionarioService.class);

            if (newVal != null) { // Verifica se um serviço foi selecionado
                cbmFuncionario.getItems().addAll(funcionarioService.buscarPorServico(newVal.getNome()));
            }
        }));

        Label lblPet = new Label("Pet:");
        cbmPet = new ComboBox<>();
        Componentes.configurarComboBox(cbmPet, Pet::getNome);
        cbmPet.getItems().addAll(EditarCliente.getC().getPets());
        cbmPet.setPromptText("Selecione o Pet");
        cbmPet.setMaxWidth(250);

        Button btnCadastrar = new Button("Cadastrar");
        HBox linhaBtn = new HBox();
        linhaBtn.getChildren().add(btnCadastrar);
        linhaBtn.setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();
        grid.add(header, 0, 0, 3, 1);
        grid.add(lblServico, 0, 1);
        grid.add(cbmServico, 1, 1);
        grid.add(lblPet, 0, 2);
        grid.add(cbmPet, 1, 2);
        grid.add(lblData, 0, 3);
        grid.add(data, 1, 3);
        grid.add(lblHora, 0, 4);
        grid.add(txtHora, 1, 4);
        grid.add(lblFuncionario, 0, 5);
        grid.add(cbmFuncionario, 1, 5);
        grid.add(linhaBtn, 0, 6, 3, 1);
        GridPane.setHalignment(linhaBtn, javafx.geometry.HPos.CENTER);

        grid.setVgap(10);
        grid.setPadding(new Insets(30, 20, 20, 20));
        HBox linhaGridBox = new HBox();
        linhaGridBox.getChildren().add(grid);
        linhaGridBox.setAlignment(Pos.CENTER);

        VBox principal = new VBox(50);
        principal.getChildren().addAll(header, linhaGridBox);
        principal.setAlignment(Pos.CENTER);

        btnCadastrar.setOnAction(evento -> Acoes.executar(() -> {
            try {
                data.setValue(data.getConverter().fromString(data.getEditor().getText()));
            } catch (Exception ex) {
                data.setValue(null);
            }

            if (cbmServico.getValue() == null || cbmPet.getValue() == null || data == null || data.getValue() == null || txtHora.getText().trim().isEmpty() || cbmFuncionario.getValue() == null) {
                Alertas.erro("Faltam dados do Agendamento!", "Por favor, preencha todos os campos para cadastrar o agendamento.");
                return;
            }
            Optional<LocalTime> horaOpt = Datas.parseHora(txtHora.getText());
            if (horaOpt.isEmpty()) {
                Alertas.erro("Formato de hora inválido!", "Use o formato: HH:mm ou HH:mm:ss\nExemplos: 09:00 ou 09:30:00");
                return;
            }
            LocalTime hora = horaOpt.get();

            if (data.getValue().isBefore(LocalDate.now())) {
                Alertas.erro("Data inválida!", "A data do agendamento não pode ser anterior a hoje (" + LocalDate.now() + ").");
                return;
            }

            Servico servicoSelecionado = cbmServico.getValue();
            Funcionario funcionarioSelecionado = cbmFuncionario.getValue();
            Pet petSelecionado = cbmPet.getValue();

            Agenda novaAgenda = new Agenda(
                    data.getValue(),
                    hora,
                    EditarCliente.getC(),
                    petSelecionado,
                    servicoSelecionado,
                    funcionarioSelecionado
            );

            AgendaService agendaService = App.getBean(AgendaService.class);
            agendaService.cadastrarAgenda(novaAgenda);

            Alertas.info(
                    "Imprimindo Cadastro",
                    "Agendamento para o serviço " + servicoSelecionado.getNome()
                            + " no dia " + data.getValue().toString()
                            + " às " + txtHora.getText().trim()
                            + " com o funcionário " + funcionarioSelecionado.getNome()
                            + " foi cadastrado com Sucesso"
            );
            popup.close();
        }));

        String cssGlobal = App.class.getResource("/styles/style.css").toExternalForm();
        principal.getStylesheets().add(cssGlobal);

        Scene pop = new Scene(principal, 700, 600);
        popup.setScene(pop);
        popup.showAndWait();
    }
}

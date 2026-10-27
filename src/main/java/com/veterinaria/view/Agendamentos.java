package com.veterinaria.view;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.veterinaria.App;
import com.veterinaria.model.Agenda;
import com.veterinaria.service.AgendaService;
import com.veterinaria.utils.Componentes;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Agendamentos {
    public static Parent criarCena() {

        Label lblTitulo = new Label("Agendamentos");
        lblTitulo.getStyleClass().add("titulo");

        AgendaService agendaService = App.getBean(AgendaService.class);
        List<Agenda> agendas = agendaService.listarAgendasOrdemDataHora(EditarCliente.getC());
        List<TitledPane> paineis = new ArrayList<>();

        if (agendas.isEmpty()) {
            paineis.add(new TitledPane("Nenhum agendamento encontrado", new VBox()));
        } else {
            int i = 0;
            for (Agenda agenda : agendas) {
                VBox detalhes = new VBox(5);
                Label tituloAgendas = new Label("Agenda " + ++i);
                detalhes.getChildren().add(tituloAgendas);
                detalhes.getChildren().add(new Label(agenda.exibirAgenda()));

                String tituloBarra = agenda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + agenda.getHora().format(DateTimeFormatter.ofPattern("HH:mm"));
                paineis.add(new TitledPane(tituloBarra, detalhes));
            }
        }

        VBox coluna = Componentes.listaSanfona(paineis);

        Button btnCadastrar = new Button("Novo Agendamento");
        GridPane grid = new GridPane();
        grid.add(lblTitulo, 0, 0, 3, 1);
        grid.add(coluna, 0, 1, 3, 1);
        grid.add(btnCadastrar, 0, 2);

        GridPane.setHalignment(btnCadastrar, javafx.geometry.HPos.CENTER);
        Button btnRemover = new Button("Remover Agendamento");
        grid.add(btnRemover, 2, 2);
        GridPane.setHalignment(lblTitulo, javafx.geometry.HPos.CENTER);
        grid.setVgap(10);
        grid.setPadding(new Insets(30, 20, 20, 20));

        HBox linhaBotoes = new HBox(10);
        linhaBotoes.getChildren().addAll(btnCadastrar, btnRemover);
        linhaBotoes.setAlignment(Pos.CENTER);

        HBox linhaTitulo = new HBox();
        linhaTitulo.getChildren().add(lblTitulo);
        linhaTitulo.setAlignment(Pos.CENTER);

        HBox linhaConteudo = new HBox();
        linhaConteudo.getChildren().add(coluna);
        linhaConteudo.setAlignment(Pos.CENTER);

        btnCadastrar.setOnAction(e -> {
            CadastrarAgenda.abrirJanela();
            App.inserirCena(EditarCliente.criarCena(EditarCliente.getC()));
        });
        btnRemover.setOnAction(e -> {
            RemoverAgenda.abrirJanela();
            App.inserirCena(EditarCliente.criarCena(EditarCliente.getC()));
        });
        return new VBox(20, linhaTitulo, linhaConteudo, linhaBotoes);
    }
}

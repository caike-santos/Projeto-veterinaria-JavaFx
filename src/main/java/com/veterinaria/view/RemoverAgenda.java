package com.veterinaria.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


import com.veterinaria.App;
import com.veterinaria.service.AgendaService;
import com.veterinaria.utils.Acoes;
import com.veterinaria.utils.Alertas;
import com.veterinaria.utils.Componentes;
import com.veterinaria.utils.Datas;
import com.veterinaria.utils.Mascaras;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RemoverAgenda {
    public static void abrirJanela() {
        Stage popup = new Stage();
        popup.setTitle("Remover Agenda");

        HBox header = Componentes.header("Remover Agenda");

        Label lblData = new Label("Data do agendamento:");

        DatePicker data = new DatePicker();
        data.setMaxWidth(250);
        Datas.configurarPickerBR(data, true);

        Label lblHora = new Label("Hora do agendamento:");

        TextField txtHora = new TextField();
        txtHora.setMaxWidth(250);
        Mascaras.aplicar(txtHora, "##:##");

        HBox linhaData = new HBox(15);
        linhaData.getChildren().addAll(lblData, data);
        linhaData.setAlignment(Pos.CENTER);

        HBox linhaHora = new HBox(15);
        linhaHora.getChildren().addAll(lblHora, txtHora);
        linhaHora.setAlignment(Pos.CENTER);

        Button btnRemover = new Button("Remover");
        HBox linhaBtn = new HBox();
        linhaBtn.getChildren().add(btnRemover);
        linhaBtn.setAlignment(Pos.CENTER);

        VBox principal = new VBox(50);
        principal.getChildren().addAll(header, linhaData, linhaHora, linhaBtn);
        principal.setAlignment(Pos.CENTER);

        btnRemover.setOnAction(event -> Acoes.executar(() -> {
            String hora = txtHora.getText().trim();
            data.setValue(data.getConverter().fromString(data.getEditor().getText()));
            if (data.getValue() == null || hora.isEmpty()) {
                Alertas.erro("Campo Vazio", "Por favor, digite a data e a hora do agendamento que deseja remover.");
                return;
            }
            Optional<LocalTime> horaOpt = Datas.parseHora(hora);
            if (horaOpt.isEmpty()) {
                Alertas.erro("Formato de hora inválido!", "Use o formato: HH:mm ou HH:mm:ss\nExemplos: 09:00 ou 09:30:00");
                return;
            }
            LocalTime hora1 = horaOpt.get();
            LocalDate data1 = data.getValue();

            AgendaService agendaService = App.getBean(AgendaService.class);
            agendaService.removerAgendas(EditarCliente.getC(), data1, hora1);

            Alertas.info("Sucesso", "Agendamento removido com sucesso!");
            popup.close();
        }));

        String cssGlobal = App.class.getResource("/styles/style.css").toExternalForm();
        principal.getStylesheets().add(cssGlobal);

        Scene pop = new Scene(principal, 700, 600);
        popup.setScene(pop);
        popup.showAndWait();
    }
}

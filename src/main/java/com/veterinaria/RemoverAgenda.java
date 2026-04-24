package com.veterinaria;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RemoverAgenda {
    public static void abrirJanela(){
        Stage popup = new Stage();
        popup.setTitle("Remover Agenda");

        Image logo = new Image(CadastroPet.class.getResource("/imagens/pet.png").toExternalForm());
        ImageView visulogoCadastro = new ImageView(logo);
        visulogoCadastro.setFitHeight(60);
        visulogoCadastro.setPreserveRatio(true);

        Label lblTituloRemover = new Label("Remover Agenda");
        lblTituloRemover.getStyleClass().add("titulo");

        

        HBox header = new HBox(20);
        header.getChildren().addAll(visulogoCadastro, lblTituloRemover);
        header.getStyleClass().add("header");
        header.setAlignment(Pos.CENTER);
        //fimHeader

        Label lblData = new Label("Data do agendamento:");

        DatePicker Data = new DatePicker();
        Data.setMaxWidth(250);

        Label lblHora = new Label("Hora do agendamento:");

        TextField txtHora = new TextField();
        txtHora.setMaxWidth(250);

        HBox linhaData = new HBox(15);
        linhaData.getChildren().addAll(lblData, Data);
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

        btnRemover.setOnAction(e -> {
            String data = Data.getValue().toString();
            String hora = txtHora.getText().trim();
            if (data.isEmpty() || hora.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Campo Vazio");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, digite a data e a hora do agendamento que deseja remover.");
                alert.showAndWait();
                return;
            }else{
                // Aceita formatos como "9:00", "09:00", "9:30:00", "09:30:00"
                DateTimeFormatter[] formatadores = {
                    DateTimeFormatter.ofPattern("H:mm"),      // 9:00
                    DateTimeFormatter.ofPattern("HH:mm"),     // 09:00
                    DateTimeFormatter.ofPattern("H:mm:ss"),   // 9:00:00
                    DateTimeFormatter.ofPattern("HH:mm:ss")   // 09:00:00
                };
                
                LocalTime hora1 = null;
                String horaTexto = txtHora.getText().trim();
                
                for (DateTimeFormatter fmt : formatadores) {
                    try {
                        hora1 = LocalTime.parse(horaTexto, fmt);
                        break;
                    } catch (Exception e1) {
                        // Tenta o próximo formato
                    }
                }
                
                if (hora1 == null) {
                    CadastroCliente.mostarNaTela("Formato de hora inválido!", "Use o formato: HH:mm ou HH:mm:ss\nExemplos: 09:00 ou 09:30:00");
                    return;
                }
            boolean agendaRemovida = false;
            //procura o agendamento na lista de agendamentos do cliente e remove
            Iterator<Agenda> agendamento = BancoDeDados.getAgendas().iterator();
            while(agendamento.hasNext()) {
                Agenda a = agendamento.next();
                if(a.getData().toString().equals(data) && a.getHora().toString().equals(hora1) && a.getCliente().getNome().equalsIgnoreCase(EditarCliente.getC().getNome())) {
                    agendamento.remove();
                    agendaRemovida = true;
                    break;
                }
            }
            
            
            if (!agendaRemovida) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Agendamento não encontrado");
                alert.setHeaderText(null);
                alert.setContentText("O agendamento digitado não foi encontrado.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucesso");
                    alert.setHeaderText(null);
                    alert.setContentText("Agendamento removido com sucesso!");
                    alert.showAndWait();
            }
            popup.close();
            
        }

        });

        String cssGlobal = App.class.getResource("/styles/style.css").toExternalForm();
            principal.getStylesheets().add(cssGlobal);

        Scene pop = new Scene(principal, 700, 600);
        popup.setScene(pop);
        popup.showAndWait();


    }
}

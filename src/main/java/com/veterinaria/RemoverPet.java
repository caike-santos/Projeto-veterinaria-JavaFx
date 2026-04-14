package com.veterinaria;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RemoverPet {
    public static void abrirJanela(){
        Stage popup = new Stage();
        popup.setTitle("Remover Pet");

        Image logo = new Image(CadastroPet.class.getResource("/imagens/pet.png").toExternalForm());
        ImageView visulogoCadastro = new ImageView(logo);
        visulogoCadastro.setFitHeight(60);
        visulogoCadastro.setPreserveRatio(true);

        Label lblTituloRemover = new Label("Remover Pet");
        lblTituloRemover.getStyleClass().add("titulo");

        

        HBox header = new HBox(20);
        header.getChildren().addAll(visulogoCadastro, lblTituloRemover);
        header.getStyleClass().add("header");
        header.setAlignment(Pos.CENTER);
        //fimHeader

        Label lblNomePet = new Label("Nome do pet:");

        TextField txtNomePet = new TextField();
        txtNomePet.setMaxWidth(250);

        HBox linhaRemover = new HBox(15);
        linhaRemover.getChildren().addAll(lblNomePet, txtNomePet);
        linhaRemover.setAlignment(Pos.CENTER);

        Button btnRemover = new Button("Remover");
        HBox linhaBtn = new HBox();
        linhaBtn.getChildren().add(btnRemover);
        linhaBtn.setAlignment(Pos.CENTER);

        VBox principal = new VBox(50);
        principal.getChildren().addAll(header, linhaRemover, linhaBtn);
        principal.setAlignment(Pos.CENTER);

        btnRemover.setOnAction(e -> {
            String nomePet = txtNomePet.getText().trim();
            if (nomePet.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Campo Vazio");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, digite o nome do pet que deseja remover.");
                alert.showAndWait();
                return;
            }
            boolean petRemovido = false;
            for (Pet p : EditarCliente.getC().getPet()) {
                if (p.getNome().equalsIgnoreCase(nomePet)) {
                    EditarCliente.getC().getPet().remove(p);
                    petRemovido = true;
                    break;
                }
            }
            if (!petRemovido) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Pet não encontrado");
                alert.setHeaderText(null);
                alert.setContentText("O pet digitado não foi encontrado.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucesso");
                    alert.setHeaderText(null);
                    alert.setContentText("Pet removido com sucesso!");
                    alert.showAndWait();
            }
            popup.close();
            


        });

        String cssGlobal = App.class.getResource("/styles/style.css").toExternalForm();
            principal.getStylesheets().add(cssGlobal);

        Scene pop = new Scene(principal, 700, 600);
        popup.setScene(pop);
        popup.showAndWait();


    }
}

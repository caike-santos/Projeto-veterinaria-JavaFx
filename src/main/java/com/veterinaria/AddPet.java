package com.veterinaria;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddPet {
    public static void abrirJanela(){
        Stage popup = new Stage();
        popup.setTitle("Adicionar novo Pet");

        VBox cadastro = CadastroPet.getLayout();

        Button btnAdicionar = new Button("Adicionar");
        HBox linhaBtn = new HBox();
        linhaBtn.getChildren().add(btnAdicionar);
        linhaBtn.setAlignment(Pos.CENTER);

        VBox principal = new VBox(50);
        principal.getChildren().addAll(cadastro, btnAdicionar);
        principal.setAlignment(Pos.CENTER);

        btnAdicionar.setOnAction(e -> {
            if(CadastroPet.getCadastro().getText().isEmpty() ){
                CadastroCliente.mostarNaTela("Nenhum cadastro realizado", "Faça pelo menos um cadastro para poder finalizado");
                return;
            }
            for(Pet p : CadastroPet.getListaPet()){
                EditarCliente.getC().getPet().add(p);
            }
            CadastroPet.getListaPet().clear();
            CadastroPet.getCadastro().clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText(null);
                alert.setContentText("Pet adicionado com sucesso!");
                alert.showAndWait();
            popup.close();
            


        });

        String cssGlobal = App.class.getResource("/styles/style.css").toExternalForm();
            principal.getStylesheets().add(cssGlobal);

        Scene pop = new Scene(principal, 700, 600);
        popup.setScene(pop);
        popup.showAndWait();


    }
}

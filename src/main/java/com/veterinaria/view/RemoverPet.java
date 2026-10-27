package com.veterinaria.view;

import com.veterinaria.App;
import com.veterinaria.model.Cliente;
import com.veterinaria.service.PetService;
import com.veterinaria.utils.Acoes;
import com.veterinaria.utils.Alertas;
import com.veterinaria.utils.Componentes;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RemoverPet {
    public static void abrirJanela() {
        Stage popup = new Stage();
        popup.setTitle("Remover Pet");

        HBox header = Componentes.header("Remover Pet");

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

        btnRemover.setOnAction(event -> Acoes.executar(() -> {
            String nomePet = txtNomePet.getText().trim();
            Cliente c = EditarCliente.getC();

            if (nomePet.isEmpty()) {
                Alertas.erro("Campo Vazio", "Por favor, digite o nome do pet que deseja remover.");
                return;
            }

            PetService petService = App.getBean(PetService.class);
            Cliente clienteAtualizado = petService.removerPet(nomePet, c);
            EditarCliente.setC(clienteAtualizado);

            Alertas.info("Sucesso", "Pet removido com sucesso!");

            popup.close();
        }));

        String cssGlobal = App.class.getResource("/styles/style.css").toExternalForm();
        principal.getStylesheets().add(cssGlobal);

        Scene pop = new Scene(principal, 700, 600);
        popup.setScene(pop);
        popup.showAndWait();


    }
}

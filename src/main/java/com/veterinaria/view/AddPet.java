package com.veterinaria.view;

import com.veterinaria.App;
import com.veterinaria.model.Cliente;
import com.veterinaria.model.Pet;
import com.veterinaria.service.PetService;
import jakarta.validation.ConstraintViolationException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
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

        btnAdicionar.setOnAction(event -> {
            try {
                PetService petService = App.getBean(PetService.class);
                Cliente clienteAtualizado = petService.adicionarPet(CadastroPet.getListaPet(), EditarCliente.getC());
                EditarCliente.setC(clienteAtualizado);
                CadastroPet.getListaPet().clear();
                CadastroPet.getCadastro().clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText(null);
                alert.setContentText("Pet adicionado com sucesso!");
                alert.showAndWait();

                popup.close();
            } catch (IllegalArgumentException | ConstraintViolationException e) {
                log.warn("Erro de validação: {}", e.getMessage());
            } catch (Exception e) {
                log.error("Erro inesperado:", e);
            }

        });

        String cssGlobal = App.class.getResource("/styles/style.css").toExternalForm();
            principal.getStylesheets().add(cssGlobal);

        Scene pop = new Scene(principal, 700, 600);
        popup.setScene(pop);
        popup.showAndWait();
    }
}

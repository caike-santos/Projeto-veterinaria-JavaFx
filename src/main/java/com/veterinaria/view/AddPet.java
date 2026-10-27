package com.veterinaria.view;

import com.veterinaria.App;
import com.veterinaria.model.Cliente;
import com.veterinaria.service.PetService;
import com.veterinaria.utils.Acoes;
import com.veterinaria.utils.Alertas;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddPet {
    public static void abrirJanela() {
        Stage popup = new Stage();
        popup.setTitle("Adicionar novo Pet");

        VBox cadastro = CadastroPet.getLayout();

        Button btnAdicionar = new Button("Adicionar");
        HBox linhaBtn = new HBox();
        linhaBtn.getChildren().add(btnAdicionar);
        linhaBtn.setAlignment(Pos.CENTER);

        VBox principal = new VBox(50);
        principal.getChildren().addAll(cadastro, linhaBtn);
        principal.setAlignment(Pos.CENTER);

        btnAdicionar.setOnAction(event -> Acoes.executar(() -> {
            PetService petService = App.getBean(PetService.class);
            Cliente clienteAtualizado = petService.adicionarPet(CadastroPet.getListaPet(), EditarCliente.getC());
            EditarCliente.setC(clienteAtualizado);
            CadastroPet.getListaPet().clear();
            CadastroPet.getCadastro().clear();

            Alertas.info("Sucesso", "Pet adicionado com sucesso!");

            popup.close();
        }));

        String cssGlobal = App.class.getResource("/styles/style.css").toExternalForm();
        principal.getStylesheets().add(cssGlobal);

        Scene pop = new Scene(principal, 700, 600);
        popup.setScene(pop);
        popup.showAndWait();
    }
}

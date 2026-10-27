package com.veterinaria.view;

import java.util.ArrayList;
import java.util.List;

import com.veterinaria.App;
import com.veterinaria.model.Cliente;
import com.veterinaria.model.Pet;
import com.veterinaria.service.ClienteService;
import com.veterinaria.utils.Componentes;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class ExibirClientes {
    public static Parent criarCena() {
        ClienteService clienteService = App.getBean(ClienteService.class);
        List<Cliente> clientes = clienteService.listarClienteOrdemAlfabetica();
        List<TitledPane> paineis = new ArrayList<>();

        for (Cliente cliente : clientes) {
            VBox detalhes = new VBox(5);
            detalhes.getChildren().add(new Label(cliente.exibeDados()));

            Label tituloPets = new Label("\n🐾 Pets do Cliente:");
            tituloPets.setStyle("-fx-font-weight: bold;");
            detalhes.getChildren().add(tituloPets);

            if (cliente.getPets().isEmpty()) {
                detalhes.getChildren().add(new Label("Nenhum pet cadastrado."));
            } else {
                for (Pet p : cliente.getPets()) {
                    detalhes.getChildren().add(new Label("\nPet " + (cliente.getPets().indexOf(p) + 1) + ":\n" + p.exibePet()));
                }
            }

            paineis.add(new TitledPane(cliente.getNome(), detalhes));
        }

        return Componentes.listaSanfona(paineis);
    }
}

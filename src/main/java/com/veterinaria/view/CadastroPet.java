package com.veterinaria.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.veterinaria.App;
import com.veterinaria.model.Genero;
import com.veterinaria.model.Pet;
import com.veterinaria.service.PetService;
import com.veterinaria.utils.Acoes;
import com.veterinaria.utils.Alertas;
import com.veterinaria.utils.Componentes;
import com.veterinaria.utils.Datas;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CadastroPet {
    @Getter
    private static TextField txtNome;
    @Getter
    private static DatePicker dpIdade;
    @Getter
    private static TextField txtRaca;
    @Getter
    private static ComboBox<Genero> cbmSexo;
    @Getter
    private static List<Pet> listaPet = new ArrayList<>();
    @Getter
    private static TextArea cadastro;
    @Getter
    private static VBox layout;

    public static Parent criarCena() {
        HBox header = Componentes.header("Cadastro do Pet");

        //formulario
        GridPane gridPet = new GridPane();
        gridPet.setAlignment(Pos.CENTER);
        gridPet.setHgap(10);
        gridPet.setVgap(15);

        Label lblNome = new Label("Nome do pet:");
        txtNome = new TextField();
        txtNome.setMaxWidth(250);

        Label lblIdade = new Label("Data de Nascimento:");
        dpIdade = new DatePicker();
        dpIdade.setMaxWidth(150);
        Datas.configurarPickerBR(dpIdade, false);

        Label lblRaca = new Label("Raça do pet:");
        txtRaca = new TextField();
        txtRaca.setMaxWidth(250);

        Label lblSexo = new Label("Sexo do pet:");
        cbmSexo = new ComboBox<>();
        cbmSexo.getItems().addAll(Genero.values());
        Componentes.configurarComboBox(cbmSexo, Genero::name);
        cbmSexo.getSelectionModel().selectFirst();

        gridPet.add(lblNome, 0, 0);
        gridPet.add(txtNome, 1, 0);

        gridPet.add(lblIdade, 0, 1);
        gridPet.add(dpIdade, 1, 1);

        gridPet.add(lblRaca, 0, 2);
        gridPet.add(txtRaca, 1, 2);

        gridPet.add(lblSexo, 0, 3);
        gridPet.add(cbmSexo, 1, 3);

        Button btnCadastrar = new Button("Cadastrar Pet");

        cadastro = new TextArea();
        cadastro.setEditable(false);
        cadastro.setMaxWidth(300);
        cadastro.setPrefHeight(150);

        btnCadastrar.setOnAction(evento -> Acoes.executar(() -> {
            try {
                dpIdade.setValue(dpIdade.getConverter().fromString(dpIdade.getEditor().getText()));
            } catch (Exception e) {
                dpIdade.setValue(null); // Se deu erro na conversão, vira nulo para ser barrado no if
            }

            if (temCampoVazioDados()) {
                Alertas.erro("Faltam dados do pet!", "Por favor, preencha todos os campos.");
                log.warn("Dados faltantes");
                return;
            }
            PetService petService = App.getBean(PetService.class);
            Pet pet = petService.cadastrarPet(
                    txtNome.getText().trim(),
                    dpIdade.getValue(),
                    cbmSexo.getValue(),
                    txtRaca.getText().trim()
            );

            listaPet.add(pet);

            cadastro.appendText("\nCadastro concluído...\n");
            cadastro.appendText("nome: " + pet.getNome() + "\n");
            cadastro.appendText("idade: " + Datas.idade(pet.getDataNascimento()) + "\n");
            cadastro.appendText("raça: " + pet.getRaca() + "\n");
            cadastro.appendText("sexo: " + pet.getGenero() + "\n");

            dpIdade.setValue(null);
            txtNome.clear();
            txtRaca.clear();
        }));
        //fimCadastro

        //montandoCena
        HBox linhaBtn = new HBox();
        HBox linhaCadastro = new HBox();

        linhaBtn.getChildren().add(btnCadastrar);
        linhaCadastro.getChildren().add(cadastro);

        layout = new VBox(20);

        Arrays.asList(header, linhaBtn, linhaCadastro)
                .forEach(linha -> linha.setAlignment(Pos.CENTER));

        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(header, gridPet, linhaBtn, linhaCadastro);

        return layout;

        //fimMontandoCena
        //fimCadastro
    }

    public static boolean temCampoVazioDados() {
        return txtNome.getText().trim().isEmpty() || txtRaca.getText().trim().isEmpty() || cbmSexo.getValue() == null;
    }
}

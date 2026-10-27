package com.veterinaria.view;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.veterinaria.model.*;
import com.veterinaria.utils.Componentes;
import com.veterinaria.utils.Datas;
import com.veterinaria.utils.Mascaras;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class CadastroCliente {
    @Getter
    private static TextField txtNome;
    @Getter
    private static DatePicker dpIdade;
    @Getter
    private static TextField txtCpf;
    private static TextField txtEmail;
    @Getter
    private static TextField txtDdd;
    @Getter
    private static TextField txtTelefone;
    @Getter
    private static TextField txtBairro;
    @Getter
    private static TextField txtRua;
    @Getter
    private static TextField txtCep;
    @Getter
    private static TextField txtNumero;
    @Getter
    private static TextArea areaComplemento;

    public static Parent criarCena(Atendente atendente) {
        HBox header = Componentes.header("Cadastro do Cliente");

        GridPane gridDados = new GridPane();
        gridDados.setHgap(10);
        gridDados.setVgap(15);

        Label lblSubTituloDados = new Label("Dados Pessoais");
        lblSubTituloDados.getStyleClass().add("subtitulo");

        Label lblNome = new Label("Nome:");
        txtNome = new TextField();
        txtNome.setMaxWidth(150);

        Label lblIdade = new Label("Data de Nascimento:");
        dpIdade = new DatePicker();
        dpIdade.setMaxWidth(150);
        Datas.configurarPickerBR(dpIdade, false);

        Label lblCpf = new Label("Cpf:");
        txtCpf = new TextField();
        txtCpf.setMaxWidth(150);
        txtCpf.setPromptText("000.000.000-00");

        Mascaras.aplicar(txtCpf, "###.###.###-##");


        Label lblEmail = new Label("Email:");
        txtEmail = new TextField();
        txtEmail.setMaxWidth(150);
        txtEmail.setPromptText("exemplo@email.com");

        Label lblDdd = new Label("Ddd:");
        txtDdd = new TextField();
        txtDdd.setMaxWidth(150);
        txtDdd.setPromptText("00");
        Mascaras.aplicar(txtDdd, "##");

        Label lblTelefone = new Label("Telefone:");
        txtTelefone = new TextField();
        txtTelefone.setMaxWidth(150);
        txtTelefone.setPromptText("00000-0000");
        Mascaras.aplicar(txtTelefone, "#####-####");


        gridDados.add(lblSubTituloDados, 0, 0, 2, 1);
        GridPane.setHalignment(lblSubTituloDados, javafx.geometry.HPos.CENTER);

        gridDados.add(lblNome, 0, 1);
        gridDados.add(txtNome, 1, 1);

        gridDados.add(lblIdade, 0, 2);
        gridDados.add(dpIdade, 1, 2);

        gridDados.add(lblCpf, 0, 3);
        gridDados.add(txtCpf, 1, 3);

        gridDados.add(lblEmail, 0, 4);
        gridDados.add(txtEmail, 1, 4);

        gridDados.add(lblDdd, 0, 5);
        gridDados.add(txtDdd, 1, 5);

        gridDados.add(lblTelefone, 0, 6);
        gridDados.add(txtTelefone, 1, 6);

        GridPane gridEndereco = new GridPane();
        gridEndereco.setHgap(10);
        gridEndereco.setVgap(15);

        Label lblSubTituloEndereco = new Label("Endereço");
        lblSubTituloEndereco.getStyleClass().add("subtitulo");

        Label lblBairro = new Label("Bairro:");
        txtBairro = new TextField();
        txtBairro.setMaxWidth(150);

        Label lblRua = new Label("Rua:");
        txtRua = new TextField();
        txtRua.setMaxWidth(150);

        Label lblNumero = new Label("Numero:");
        txtNumero = new TextField();
        txtNumero.setMaxWidth(150);
        Mascaras.aplicar(txtNumero, "######");

        Label lblCep = new Label("Cep:");
        txtCep = new TextField();
        txtCep.setMaxWidth(150);
        txtCep.setPromptText("00000-000");
        Mascaras.aplicar(txtCep, "#####-###");


        Label lblComplemento = new Label("Complemento:");
        areaComplemento = new TextArea();
        areaComplemento.setEditable(true);
        areaComplemento.setMaxWidth(150);
        areaComplemento.setPrefHeight(75);

        gridEndereco.add(lblSubTituloEndereco, 0, 0, 2, 1);
        GridPane.setHalignment(lblSubTituloEndereco, javafx.geometry.HPos.CENTER);

        gridEndereco.add(lblBairro, 0, 1);
        gridEndereco.add(txtBairro, 1, 1);

        gridEndereco.add(lblRua, 0, 2);
        gridEndereco.add(txtRua, 1, 2);

        gridEndereco.add(lblCep, 0, 3);
        gridEndereco.add(txtCep, 1, 3);

        gridEndereco.add(lblNumero, 0, 4);
        gridEndereco.add(txtNumero, 1, 4);

        gridEndereco.add(lblComplemento, 0, 5);
        gridEndereco.add(areaComplemento, 1, 5);

        HBox cadastro = new HBox(100);
        cadastro.getChildren().addAll(gridDados, gridEndereco);

        Arrays.asList(header, cadastro).forEach(linha -> linha.setAlignment(Pos.CENTER));

        VBox vertical = new VBox(50);
        vertical.setAlignment(Pos.CENTER);
        vertical.getChildren().addAll(header, cadastro);

        txtCpf.setText(Home.getTxtCpf().getText());

        return vertical;
    }

    public static Cliente pegarDados(List<Pet> p) {
        String nome = txtNome.getText();
        LocalDate dataNascimento = dpIdade.getValue();
        String cpf = txtCpf.getText();
        String email = txtEmail.getText();
        String telefone = txtTelefone.getText();
        String ddd = txtDdd.getText();
        String bairro = txtBairro.getText();
        String rua = txtRua.getText();
        int numero = Integer.parseInt(txtNumero.getText());
        String cep = txtCep.getText();
        String complemento = areaComplemento.getText();
        Endereco e = new Endereco(bairro, rua, numero, cep, complemento);
        Telefone t = new Telefone(ddd, telefone);

        return new Cliente(nome, dataNascimento, cpf, e, t, email, p);
    }

    public static void setDpIdade(LocalDate dpIdade) {
        CadastroCliente.dpIdade.setValue(dpIdade);
    }

    public static boolean temCampoVazioEndereco() {
        return txtBairro.getText().trim().isEmpty() || txtCep.getText().trim().isEmpty() || txtNumero.getText().trim().isEmpty() || txtRua.getText().trim().isEmpty();
    }

    public static boolean temCampoVazioDados() {
        return txtCpf.getText().trim().isEmpty() || txtDdd.getText().trim().isEmpty() || dpIdade.getValue() == null || txtEmail.getText().trim().isEmpty() || txtNome.getText().trim().isEmpty() || txtTelefone.getText().trim().isEmpty();
    }


    public static boolean emailInvalido() {
        String emailDigitado = txtEmail.getText().trim();
        String regraEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        return !emailDigitado.matches(regraEmail);
    }


}

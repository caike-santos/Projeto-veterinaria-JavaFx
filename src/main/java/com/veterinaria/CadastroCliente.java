package com.veterinaria;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javafx.util.StringConverter;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
public class CadastroCliente {
    private static TextField txtNome;
    private static DatePicker dpIdade;
    private static TextField txtCpf;
    private static TextField txtEmail;
    private static TextField txtDdd;
    private static TextField txtTelefone;
    private static TextField txtBairro;
    private static TextField txtRua;
    private static TextField txtCep;
    private static TextField txtNumero;
    private static TextArea areaComplemento;
    public static Parent criarCena(String atendente){
         //header
        Image logo = new Image(CadastroPet.class.getResource("/imagens/pet.png").toExternalForm());
        ImageView visulogoCadastro = new ImageView(logo);
        visulogoCadastro.setFitHeight(60);
        visulogoCadastro.setPreserveRatio(true);

        Label lblTituloCadastro = new Label("Cadastro do Cliente");
        lblTituloCadastro.getStyleClass().add("titulo");

        HBox header = new HBox(20);
        header.getChildren().addAll(visulogoCadastro, lblTituloCadastro);
        header.getStyleClass().add("header");
        //fimHeader

        GridPane gridDados = new GridPane();
        gridDados.setHgap(10);
        gridDados.setVgap(15);

        Label lblSubTituloDados = new Label("Dados Pessoais");
        lblSubTituloDados.getStyleClass().add("subtitulo");

        //HBox linhaSubTituloDados = new HBox(10);
        //linhaSubTituloDados.getChildren().add(lblSubTituloDados);

        Label lblNome = new Label("Nome:");
        txtNome = new TextField();
        txtNome.setMaxWidth(150);
        //HBox linhaNome = new HBox(10);
        //linhaNome.getChildren().addAll(lblNome, txtNome);

        Label lblIdade = new Label("Data de Nascimento:");
        dpIdade = new DatePicker();
        dpIdade.setMaxWidth(150);
        dpIdade.setPromptText("dd/mm/aaaa");
        TextField editorData = dpIdade.getEditor();

        // 2. Cria o "Ouvinte" que espia tudo o que é digitado em tempo real
        editorData.textProperty().addListener((observavel, valorAntigo, valorNovo) -> {
            
            if (valorNovo == null) return;

            // Remove tudo que não for número (Impede o usuário de digitar letras)
            String apenasNumeros = valorNovo.replaceAll("[^\\d]", "");

            // Limita a 8 números no máximo (ddMMyyyy)
            if (apenasNumeros.length() > 8) {
                apenasNumeros = apenasNumeros.substring(0, 8);
            }

            // Monta o texto colocando a barra nas posições certas
            StringBuilder formatado = new StringBuilder();
            for (int i = 0; i < apenasNumeros.length(); i++) {
                if (i == 2 || i == 4) {
                    formatado.append("/");
                }
                formatado.append(apenasNumeros.charAt(i));
            }

            // Se o texto digitado for diferente da máscara, ele substitui e joga o cursor pro final
            if (!valorNovo.equals(formatado.toString())) {
                
                // O Platform.runLater é um truque para o JavaFX não bugar a posição do cursor no teclado
                javafx.application.Platform.runLater(() -> {
                    editorData.setText(formatado.toString());
                    editorData.positionCaret(formatado.length()); 
                });
            }
});
        DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // 2. Coloca o tradutor no DatePicker
        dpIdade.setConverter(new StringConverter<LocalDate>() {
    
        // Transforma a Data (Objeto) em Texto para mostrar na tela
        @Override
        public String toString(LocalDate data) {
            if (data != null) {
                return formatoBR.format(data);
            }
            return "";
        }

        // Transforma o Texto digitado no teclado em Data (Objeto)
        @Override
public LocalDate fromString(String texto) {
    if (texto != null && !texto.trim().isEmpty()) {
        try {
            // 1. Primeiro tenta ler a data no formato BR
            LocalDate dataDigitada = LocalDate.parse(texto, formatoBR);
            
            // 2. A MÁGICA: Verifica se a data exata cai no futuro
            if (dataDigitada.isAfter(LocalDate.now())) {
                return null; // A data é do futuro (idade negativa)! Devolve nulo para barrar.
            }
            
            // 3. Se a data for válida e no passado/hoje, devolve ela pronta!
            return dataDigitada;
            
        } catch (Exception e) {
            // Cai aqui se digitar letras ou 32/15/2020
            return null; 
        }
    }
    return null;
}
});
        //HBox linhaIdade = new HBox(10);
        //linhaIdade.getChildren().addAll(lblIdade, txtIdade);

        Label lblCpf = new Label("Cpf:");
        txtCpf = new TextField();
        txtCpf.setMaxWidth(150);
        txtCpf.setPromptText("000.000.000-00");
        
        txtCpf.textProperty().addListener((observable, valorAntigo, valorNovo) -> {

            if (valorNovo == null) return;

            // Remove tudo que não for número (Impede o usuário de digitar letras)
            String apenasNumeros = valorNovo.replaceAll("[^\\d]", "");

            // Limita a 11 números no máximo (ddMMyyyy)
            if (apenasNumeros.length() > 11) {
                apenasNumeros = apenasNumeros.substring(0, 11);
            }

            // Monta o texto colocando a barra nas posições certas
            StringBuilder formatado = new StringBuilder();
            for (int i = 0; i < apenasNumeros.length(); i++) {
                if (i == 3 || i == 6){
                    formatado.append(".");
                }else if (i == 9){
                    formatado.append("-");
                }

                formatado.append(apenasNumeros.charAt(i));
            }

            // Se o texto digitado for diferente da máscara, ele substitui e joga o cursor pro final
            if (!valorNovo.equals(formatado.toString())) {
                
                // O Platform.runLater é um truque para o JavaFX não bugar a posição do cursor no teclado
                javafx.application.Platform.runLater(() -> {
                    txtCpf.setText(formatado.toString());
                    txtCpf.positionCaret(formatado.length()); 
                });
            }
         });
        //HBox linhaCpf = new HBox(10);
        //linhaCpf.getChildren().addAll(lblCpf, txtCpf);

        Label lblEmail = new Label("Email:");
        txtEmail = new TextField();
        txtEmail.setMaxWidth(150);
        txtEmail.setPromptText("exemplo@email.com");
        //HBox linhaEmail = new HBox(10);
        //linhaEmail.getChildren().addAll(lblEmail, txtEmail);

        Label lblDdd = new Label("Ddd:");
        txtDdd = new TextField();
        txtDdd.setMaxWidth(150);
        txtDdd.setPromptText("00");
        txtDdd.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
        
            if (valorNovo.length() > 2) {
            txtDdd.setText(valorAntigo);
        }   

        if (!valorNovo.matches("\\d*")) {
            txtDdd.setText(valorNovo.replaceAll("[^\\d]", ""));
        }
         });

        Label lblTelefone = new Label("Telefone:");
        txtTelefone = new TextField();
        txtTelefone.setMaxWidth(150);
        txtTelefone.setPromptText("00000-0000");
        txtTelefone.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            if (valorNovo == null) return;

            // Remove tudo que não for número (Impede o usuário de digitar letras)
            String apenasNumeros = valorNovo.replaceAll("[^\\d]", "");

            // Limita a 9 números no máximo (ddMMyyyy)
            if (apenasNumeros.length() > 9) {
                apenasNumeros = apenasNumeros.substring(0, 9);
            }

            // Monta o texto colocando a barra nas posições certas
            StringBuilder formatado = new StringBuilder();
            for (int i = 0; i < apenasNumeros.length(); i++) {
                if (i == 5){
                    formatado.append("-");
                }

                formatado.append(apenasNumeros.charAt(i));
            }

            // Se o texto digitado for diferente da máscara, ele substitui e joga o cursor pro final
            if (!valorNovo.equals(formatado.toString())) {
                
                // O Platform.runLater é um truque para o JavaFX não bugar a posição do cursor no teclado
                javafx.application.Platform.runLater(() -> {
                    txtTelefone.setText(formatado.toString());
                    txtTelefone.positionCaret(formatado.length()); 
                });
            }
         });
        //HBox linhaTelefone = new HBox(10);
        //linhaTelefone.getChildren().addAll(lblTelefone, txtTelefone);

        //VBox dadosPessoais = new VBox(20);
        //dadosPessoais.getChildren().addAll(linhaSubTituloDados, linhaNome, linhaIdade, linhaCpf, linhaEmail, linhaTelefone);

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

        //HBox linhaSubTituloEndereco = new HBox(10);
        //linhaSubTituloEndereco.getChildren().add(lblSubTituloEndereco);

        Label lblBairro = new Label("Bairro:");
        txtBairro = new TextField();
        txtBairro.setMaxWidth(150);
        //HBox linhaBairro = new HBox(10);
        //linhaBairro.getChildren().addAll(lblBairro, txtBairro);

        Label lblRua = new Label("Rua:");
        txtRua = new TextField();
        txtRua.setMaxWidth(150);
       // HBox linhaRua = new HBox(10);
        //linhaRua.getChildren().addAll(lblRua, txtRua);

        Label lblNumero = new Label("Numero:");
        txtNumero = new TextField();
        txtNumero.setMaxWidth(150);
        txtNumero.textProperty().addListener((observable, valorAntigo, valorNovo) -> {

        if (!valorNovo.matches("\\d*")) {
            txtNumero.setText(valorNovo.replaceAll("[^\\d]", ""));
        }
         });
        //HBox linhaNumero = new HBox(10);
        //linhaNumero.getChildren().addAll(lblNumero, txtNumero);

        Label lblCep = new Label("Cep:");
        txtCep = new TextField();
        txtCep.setMaxWidth(150);
        txtCep.setPromptText("00000-000");
        txtCep.textProperty().addListener((observable, valorAntigo, valorNovo) -> {

        if (valorNovo == null) return;

            // Remove tudo que não for número (Impede o usuário de digitar letras)
            String apenasNumeros = valorNovo.replaceAll("[^\\d]", "");

            // Limita a 11 números no máximo (ddMMyyyy)
            if (apenasNumeros.length() > 8) {
                apenasNumeros = apenasNumeros.substring(0, 8);
            }

            // Monta o texto colocando a barra nas posições certas
            StringBuilder formatado = new StringBuilder();
            for (int i = 0; i < apenasNumeros.length(); i++) {
                if (i == 5){
                    formatado.append("-");
                }

                formatado.append(apenasNumeros.charAt(i));
            }

            // Se o texto digitado for diferente da máscara, ele substitui e joga o cursor pro final
            if (!valorNovo.equals(formatado.toString())) {
                
                // O Platform.runLater é um truque para o JavaFX não bugar a posição do cursor no teclado
                javafx.application.Platform.runLater(() -> {
                    txtCep.setText(formatado.toString());
                    txtCep.positionCaret(formatado.length()); 
                });
            }
         });
        //HBox linhaCep = new HBox(10);
        //linhaCep.getChildren().addAll(lblCep, txtCep);

        Label lblComplemento = new Label("Complemento:");
        areaComplemento = new TextArea();
        areaComplemento.setEditable(true);
        areaComplemento.setMaxWidth(150);
        areaComplemento.setPrefHeight(75);
        //VBox colunaComplemento = new VBox(10);
       // colunaComplemento.getChildren().addAll(lblComplemento, areaComplemento);
        //HBox linhaComplemento = new HBox(10);
        //linhaComplemento.getChildren().addAll(lblComplemento, areaComplemento);

        //VBox endereco = new VBox(20);
        //endereco.getChildren().addAll(linhaSubTituloEndereco, linhaBairro, linhaRua, linhaNumero, linhaCep, linhaComplemento);

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

    public static Cliente pegarDados(List<Pet> p){
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
    Endereço e = new Endereço(bairro, rua, numero, cep, complemento);
    Telefone t = new Telefone(ddd, telefone);
    
    
    return new Cliente(nome, dataNascimento, cpf, e, t, email, p);
    }

    public static TextArea getAreaComplemento() {
        return areaComplemento;
    }
    public static TextField getTxtBairro() {
        return txtBairro;
    }
    public static TextField getTxtCep() {
        return txtCep;
    }
    public static TextField getTxtCpf() {
        return txtCpf;
    }
    public static TextField getTxtEmail() {
        return txtEmail;
    }
    public static DatePicker getDpIdade() {
        return dpIdade;
    }
    public static TextField getTxtNome() {
        return txtNome;
    }
    public static TextField getTxtNumero() {
        return txtNumero;
    }
    public static TextField getTxtRua() {
        return txtRua;
    }
    public static TextField getTxtTelefone() {
        return txtTelefone;
    }
    public static TextField getTxtDdd() {
        return txtDdd;
    }
    public static void setDpIdade(LocalDate dpIdade) {
        CadastroCliente.dpIdade.setValue(dpIdade);
    }

    public static void mostarNaTela(String header, String texto){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText(header);
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    public static boolean temCampoVazioEndereco(){
        if(txtBairro.getText().trim().isEmpty() || txtCep.getText().trim().isEmpty() || txtNumero.getText().trim().isEmpty() || txtRua.getText().trim().isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean temCampoVazioDados(){
        if(txtCpf.getText().trim().isEmpty() || txtDdd.getText().trim().isEmpty() || dpIdade.getValue() == null || txtEmail.getText().trim().isEmpty() || txtNome.getText().trim().isEmpty() || txtTelefone.getText().trim().isEmpty()){
            return true;
        }
        return false;
    }


    public static boolean emailInvalido(){
        String emailDigitado = txtEmail.getText().trim();
        String regraEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    
    if (!emailDigitado.matches(regraEmail)) {
        return true;
    }
    return false;
    }

    
}

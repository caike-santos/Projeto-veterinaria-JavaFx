package com.veterinaria;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.StringConverter;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CadastroPet {
    private static TextField txtNome;
    private static DatePicker dpIdade;
    private static TextField txtRaca;
    private static  ComboBox<String> cbmSexo;
    private static List<Pet> listaPet = new ArrayList<>();
    private static TextArea cadastro;
    private static VBox layout;
    public static Parent criarCena(){
        //header
        

        Image logo = new Image(CadastroPet.class.getResource("/imagens/pet.png").toExternalForm());
        ImageView visulogoCadastro = new ImageView(logo);
        visulogoCadastro.setFitHeight(60);
        visulogoCadastro.setPreserveRatio(true);

        Label lblTituloCadastro = new Label("Cadastro do Pet");
        lblTituloCadastro.getStyleClass().add("titulo");

        

        HBox header = new HBox(20);
        header.getChildren().addAll(visulogoCadastro, lblTituloCadastro);
        header.getStyleClass().add("header");
        //fimHeader

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
        dpIdade.setPromptText("dd/mm/aaaa");
        // 1. Pega a "caixinha de texto" que fica dentro do DatePicker
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

        Label lblRaca = new Label("Raça do pet:");
        txtRaca = new TextField();
        txtRaca.setMaxWidth(250);

        Label lblSexo = new Label("Sexo do pet:");
        cbmSexo = new ComboBox<>();
        cbmSexo.getItems().addAll("Macho", "Femea");
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

        btnCadastrar.setOnAction(evento -> 
            {
                try {
                    dpIdade.setValue(dpIdade.getConverter().fromString(dpIdade.getEditor().getText()));
                } catch (Exception ex) {
                    dpIdade.setValue(null); // Se deu erro na conversão, vira nulo para ser barrado no if
                }
                

                if(txtNome.getText().trim().isEmpty() || txtRaca.getText().trim().isEmpty() || cbmSexo.getValue() == null){
                    CadastroCliente.mostarNaTela("Faltam dados do Pet!", "Por favor, preencha todos os campos para cadastrar o pet.");
                }else if(dpIdade.getValue() == null){
                    CadastroCliente.mostarNaTela("Data de nascimento inválida!", "Por favor, preencha a data de nascimento com uma data válida.");
                }
                else{
                     int idadeNum = LocalDate.now().getYear() - dpIdade.getValue().getYear();
                        if(idadeNum < 0 || idadeNum > 100){
                        CadastroCliente.mostarNaTela("Idade do pet inválida!", "Por favor, preencha a data de nascimento com uma data válida. O pet deve ter entre 0 e 100 anos.");
                        return;
                        }

                         Pet pet = CadastroPet.dadosPet();
                         listaPet.add(pet);

                        System.out.println("nome: "+ pet.getNome());
                        System.out.println("idade: "+ pet.getIdade());
                        System.out.println("raca: "+ pet.getRaca());
                        System.out.println("sexo: "+ pet.getSexo());

                        cadastro.appendText("\nCadastro concluido...\n");
                        cadastro.appendText("nome: "+ pet.getNome()+"\n");
                        cadastro.appendText("idade: "+ pet.getIdade()+"\n");
                        cadastro.appendText("raca: "+ pet.getRaca()+"\n");
                        cadastro.appendText("sexo: "+ pet.getSexo()+"\n");

                        dpIdade.setValue(null);
                        txtNome.clear();
                        txtRaca.clear();
                }
                
                
    });
        //fimCadastro
        

        //montandoCena
        //HBox linhaNome = new HBox(10);
        //HBox linhaIdade = new HBox(10);
        //HBox linhaRaca = new HBox(10);
        //HBox linhaSexo = new HBox(10); 
        HBox linhaBtn = new HBox();
        HBox linhaCadastro = new HBox();

        //linhaNome.getChildren().addAll(lblNome, txtNome);
        //linhaIdade.getChildren().addAll(lblIdade, txtIdade);
        //linhaRaca.getChildren().addAll(lblRaca, txtRaca);
        //linhaSexo.getChildren().addAll(lblSexo, cbmSexo);
        linhaBtn.getChildren().add(btnCadastrar);
        linhaCadastro.getChildren().add(cadastro);
       

        layout = new VBox(20);

        Arrays.asList(header, linhaBtn, linhaCadastro)
      .forEach(linha -> linha.setAlignment(Pos.CENTER));
        
        layout.setAlignment(Pos.CENTER); 

        layout.getChildren().addAll(header, gridPet, linhaBtn, linhaCadastro);
        

        //VBox.setVgrow(linhaCadastro, Priority.ALWAYS);
        //HBox.setHgrow(linhaNome, Priority.ALWAYS);

        //vertical.setMaxWidth(2000);
         return layout;
         
        //fimMontandoCena
        //fimCadastro
    }

    public static Pet dadosPet(){
        String nome = txtNome.getText();
        LocalDate dataIdade = dpIdade.getValue();
        String raca = txtRaca.getText();
        String sexo = cbmSexo.getValue();

        return new Pet(nome, dataIdade, sexo, raca);

    }

    public static TextArea getCadastro() {
        return cadastro;
    }
    public static ComboBox<String> getCbmSexo() {
        return cbmSexo;
    }
    public static DatePicker getDpIdade() {
        return dpIdade;
    }
    public static TextField getTxtNome() {
        return txtNome;
    }
    public static TextField getTxtRaca() {
        return txtRaca;
    }
    public static List<Pet> getListaPet() {
        return listaPet;
    }
    public static VBox getLayout() {
        return layout;
    }
}

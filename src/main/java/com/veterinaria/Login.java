package com.veterinaria;


import java.util.Arrays;


import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Login{
    
    private static String atendente;
    public static Parent criarCena(){
        
        Image logo = new Image(Login.class.getResource("/imagens/pet.png").toExternalForm());
        ImageView mostarLogo = new ImageView(logo);
        mostarLogo.setFitHeight(60);
        mostarLogo.setPreserveRatio(true);

        Label lblTitulo = new Label("Login do Atendente");
        lblTitulo.getStyleClass().add("titulo");

        HBox linhaHeader = new HBox(10);
        linhaHeader.getChildren().addAll(mostarLogo, lblTitulo);
        
        Label lblUsuario = new Label("Usuario:");
        TextField txtUsuario = new TextField();
        txtUsuario.setMaxWidth(250);

        HBox linhaUsuario = new HBox(10);
        linhaUsuario.getChildren().addAll(lblUsuario, txtUsuario);

        Label lblSenha = new Label("Senha:");
        PasswordField txtSenha = new PasswordField();
        txtSenha.setMaxWidth(250);

        HBox linhaSenha = new HBox(10);
        linhaSenha.getChildren().addAll(lblSenha, txtSenha);

        txtUsuario.clear();
        txtSenha.clear();

        Button btnLogar = new Button("Entrar");

        HBox linhaBtn = new HBox(10);
        linhaBtn.getChildren().addAll(btnLogar);

        Arrays.asList(linhaBtn, linhaHeader, linhaSenha, linhaUsuario).forEach(linha -> linha.setAlignment(Pos.CENTER));

        VBox vertical = new VBox(20);
        vertical.getChildren().addAll(linhaHeader, linhaUsuario, linhaSenha, linhaBtn);
        vertical.setAlignment(Pos.CENTER);

        btnLogar.setOnAction(evento -> {
        
            atendente = txtUsuario.getText();
            if(txtUsuario.getText().equals("caike") && txtSenha.getText().equals("123")){
                App.inserirCena(Home.criarCena(atendente));
            }else{
                Alert aviso = new Alert(Alert.AlertType.WARNING);
                aviso.setTitle("Erro");
                aviso.setHeaderText("Usuario ou senha invalido");
                aviso.setContentText("Insira os dados corretamente");
                aviso.showAndWait();
            }
            
        });

        return vertical;
    }
    public static String getAtendente() {
        return atendente;
    }
}

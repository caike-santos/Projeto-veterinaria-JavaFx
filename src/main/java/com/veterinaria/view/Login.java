package com.veterinaria.view;


import java.util.Arrays;


import com.veterinaria.App;
import com.veterinaria.model.Atendente;
import com.veterinaria.model.Funcionario;
import com.veterinaria.service.FuncionarioService;
import jakarta.validation.ConstraintViolationException;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Login{
    
    private static Atendente atendente;
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
        try {
            String nome = txtUsuario.getText();
            String senha = txtSenha.getText();

            FuncionarioService funcionarioService = App.getBean(FuncionarioService.class);

            Funcionario funcionario = funcionarioService.login(nome, senha);
            if(funcionario instanceof Atendente atendenteLogado){
                atendente = atendenteLogado;
                log.info("Atendente {} logado(a)", atendente.getNome());
                App.inserirCena(Home.criarCena(atendenteLogado));
            }
        } catch (IllegalArgumentException | ConstraintViolationException e) {
                log.warn("Erro de validação: {}", e.getMessage());
            } catch (Exception e) {
                log.error("Erro inesperado:", e);
            }
        });

        return vertical;
    }
    public static Atendente getAtendente() {
        return atendente;
    }
}

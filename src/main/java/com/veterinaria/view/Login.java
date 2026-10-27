package com.veterinaria.view;


import java.util.Arrays;


import com.veterinaria.App;
import com.veterinaria.model.Atendente;
import com.veterinaria.model.Funcionario;
import com.veterinaria.service.FuncionarioService;
import com.veterinaria.utils.Acoes;
import com.veterinaria.utils.Alertas;
import com.veterinaria.utils.Componentes;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Login {

    @Setter
    @Getter
    private static Atendente atendente;

    public static Parent criarCena() {
        HBox linhaHeader = Componentes.header("Login do Atendente");

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
        btnLogar.setDefaultButton(true);

        HBox linhaBtn = new HBox(10);
        linhaBtn.getChildren().addAll(btnLogar);

        Arrays.asList(linhaBtn, linhaHeader, linhaSenha, linhaUsuario).forEach(linha -> linha.setAlignment(Pos.CENTER));

        VBox vertical = new VBox(20);
        vertical.getChildren().addAll(linhaHeader, linhaUsuario, linhaSenha, linhaBtn);
        vertical.setAlignment(Pos.CENTER);

        btnLogar.setOnAction(evento -> Acoes.executar(() -> {
            String nome = txtUsuario.getText();
            String senha = txtSenha.getText();

            FuncionarioService funcionarioService = App.getBean(FuncionarioService.class);

            Funcionario funcionario = funcionarioService.login(nome, senha);
            if (funcionario instanceof Atendente atendenteLogado) {
                atendente = atendenteLogado;
                log.info("Atendente {} logado(a)", atendente.getNome());
                App.inserirCena(Home.criarCena(atendenteLogado));
            } else {
                Alertas.info("Espere", "Suas telas ainda não foram criadas");
            }
        }));

        return vertical;
    }

}

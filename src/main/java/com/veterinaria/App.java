package com.veterinaria;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage palcoPrincipal;

    @Override
    public void start(Stage stage) {
        BancoDeDados.carregarDadosDeTeste();
        palcoPrincipal = stage;
        palcoPrincipal.setTitle("Pagina do Atendente");
        inserirCena(Login.criarCena());

        //palcoPrincipal.setMaximized(true);

       palcoPrincipal.show();
    }

    public static void inserirCena(Parent novoLayout){
        if (palcoPrincipal.getScene() != null) {
            
            // Agora sim! Como o novoLayout não tem dono, podemos colocar ele aqui!
            palcoPrincipal.getScene().setRoot(novoLayout);
            
        } else {
            // Se for a primeira vez (Tela de Login)
            Scene cenaInicial = new Scene(novoLayout, 1200, 800);
            
            // Aplica o CSS global na nossa única cena do aplicativo
            String cssGlobal = App.class.getResource("/styles/style.css").toExternalForm();
            cenaInicial.getStylesheets().add(cssGlobal);
            
            palcoPrincipal.setScene(cenaInicial);
            
            // Maximiza a janela para sempre
            //palcoPrincipal.setMaximized(true);
        }
    }
    

    public static void main(String[] args) {
        launch();
    }

}
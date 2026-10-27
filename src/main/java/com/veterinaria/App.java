package com.veterinaria;

import com.veterinaria.view.Login;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class App extends Application {
    private static Stage palcoPrincipal;
    private static ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        // 1. Inicia o Spring Boot ANTES de abrir a tela do JavaFX
        springContext = new SpringApplicationBuilder(SpringApp.class)
                .headless(false)
                .run();
    }

    @Override
    public void start(Stage stage) {
        // 2. O Spring já está rodando! Agora inicia o JavaFX normalmente
        palcoPrincipal = stage;
        palcoPrincipal.setTitle("Pagina do Atendente");
        inserirCena(Login.criarCena());
        palcoPrincipal.show();
    }

    @Override
    public void stop() {
        // 3. Quando fechar a janela, encerra o Spring Boot e o banco com segurança
        if (springContext != null) {
            springContext.close();
        }
    }

    // Método auxiliar para pegar qualquer @Service ou @Repository nas suas telas atuais
    public static <T> T getBean(Class<T> classeDoBean) {
        return springContext.getBean(classeDoBean);
    }

    public static void inserirCena(Parent novoLayout) {
        if (palcoPrincipal.getScene() != null) {
            palcoPrincipal.getScene().setRoot(novoLayout);
        } else {
            Scene cenaInicial = new Scene(novoLayout, 1200, 800);
            String cssGlobal = App.class.getResource("/styles/style.css").toExternalForm();
            cenaInicial.getStylesheets().add(cssGlobal);
            palcoPrincipal.setScene(cenaInicial);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

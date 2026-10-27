package com.veterinaria.utils;

import java.util.List;
import java.util.function.Function;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class Componentes {

    public static HBox header(String titulo) {
        Image logo = new Image(Componentes.class.getResource("/imagens/pet.png").toExternalForm());
        ImageView imgLogo = new ImageView(logo);
        imgLogo.setFitHeight(60);
        imgLogo.setPreserveRatio(true);

        Label lblTitulo = new Label(titulo);
        lblTitulo.getStyleClass().add("titulo");

        HBox header = new HBox(20, imgLogo, lblTitulo);
        header.getStyleClass().add("header");
        header.setAlignment(Pos.CENTER);
        return header;
    }

    public static VBox listaSanfona(List<TitledPane> paineis) {
        Accordion sanfona = new Accordion();
        sanfona.setMaxWidth(500);
        for (TitledPane painel : paineis) {
            painel.setMaxHeight(25);
        }
        sanfona.getPanes().setAll(paineis);

        ScrollPane rolagem = new ScrollPane(sanfona);
        rolagem.setFitToWidth(true);
        rolagem.setMaxWidth(500);
        rolagem.addEventFilter(ScrollEvent.SCROLL, evento -> {
            double velocidade = 0.005;
            double movimento = evento.getDeltaY() * velocidade;
            rolagem.setVvalue(rolagem.getVvalue() - movimento);
            evento.consume();
        });

        VBox coluna = new VBox(25, rolagem);
        coluna.setPadding(new Insets(30, 20, 20, 20));
        coluna.setAlignment(Pos.CENTER);
        VBox.setVgrow(rolagem, Priority.ALWAYS);
        VBox.setVgrow(sanfona, Priority.ALWAYS);
        return coluna;
    }

    public static <T> void configurarComboBox(ComboBox<T> combo, Function<T, String> extratorTexto) {
        combo.setConverter(new StringConverter<T>() {
            @Override
            public String toString(T item) {
                return item == null ? "" : extratorTexto.apply(item);
            }

            @Override
            public T fromString(String string) {
                return null;
            }
        });
    }
}

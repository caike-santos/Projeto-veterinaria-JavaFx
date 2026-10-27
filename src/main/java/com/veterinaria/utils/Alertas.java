package com.veterinaria.utils;

import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class Alertas {

    public static void erro(String header, String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText(header);
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    public static void info(String header, String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informação");
        alerta.setHeaderText(header);
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    public static boolean confirmar(String header, String texto) {
        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.setTitle("Atenção");
        confirmar.setHeaderText(header);
        confirmar.setContentText(texto);

        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmar.getButtonTypes().setAll(btnSim, btnNao);

        return confirmar.showAndWait().filter(resposta -> resposta == btnSim).isPresent();
    }

    public static void mostrarViolacoes(ConstraintViolationException e) {
        String mensagens = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .distinct()
                .collect(Collectors.joining("\n• ", "• ", ""));

        erro("Dados inválidos", mensagens);
    }
}

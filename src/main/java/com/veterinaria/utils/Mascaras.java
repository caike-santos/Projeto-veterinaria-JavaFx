package com.veterinaria.utils;
import javafx.application.Platform;
import javafx.scene.control.TextField;
public class Mascaras {
    public static void aplicar(TextField campo, String mascara) {
        campo.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            if (valorNovo == null) return;
            // 1. Pega apenas os números digitados
            String apenasNumeros = valorNovo.replaceAll("[^\\d]", "");
            // 2. Monta o texto seguindo o molde da máscara
            StringBuilder formatado = new StringBuilder();
            int indiceNumero = 0;
            for (int i = 0; i < mascara.length(); i++) {
                // Se já acabaram os números que o usuário digitou, para de montar
                if (indiceNumero >= apenasNumeros.length()) {
                    break;
                }
                char caractereMascara = mascara.charAt(i);
                if (caractereMascara == '#') {
                    // Coloca o próximo número digitado
                    formatado.append(apenasNumeros.charAt(indiceNumero));
                    indiceNumero++;
                } else {
                    // Coloca o símbolo fixo da máscara (., -, /, :, etc.)
                    formatado.append(caractereMascara);
                }
            }
            // 3. Atualiza o TextField se o texto mudou
            String resultado = formatado.toString();
            if (!valorNovo.equals(resultado)) {
                Platform.runLater(() -> {
                    campo.setText(resultado);
                    campo.positionCaret(resultado.length());
                });
            }
        });
    }
}
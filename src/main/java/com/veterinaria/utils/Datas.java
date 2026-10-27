package com.veterinaria.utils;

import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Datas {

    private static final DateTimeFormatter FORMATO_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter[] FORMATOS_HORA = {
            DateTimeFormatter.ofPattern("H:mm"),
            DateTimeFormatter.ofPattern("HH:mm"),
            DateTimeFormatter.ofPattern("H:mm:ss"),
            DateTimeFormatter.ofPattern("HH:mm:ss")
    };

    public static void configurarPickerBR(DatePicker picker, boolean permiteFuturo) {
        picker.setPromptText("dd/mm/aaaa");
        Mascaras.aplicar(picker.getEditor(), "##/##/####");

        picker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate data) {
                return (data != null) ? FORMATO_BR.format(data) : "";
            }

            @Override
            public LocalDate fromString(String texto) {
                if (texto == null || texto.trim().isEmpty()) {
                    return null;
                }
                try {
                    LocalDate dataDigitada = LocalDate.parse(texto.trim(), FORMATO_BR);
                    if (!permiteFuturo && dataDigitada.isAfter(LocalDate.now())) {
                        return null;
                    }
                    return dataDigitada;
                } catch (Exception e) {
                    return null;
                }
            }
        });
    }

    public static Optional<LocalTime> parseHora(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return Optional.empty();
        }
        String horaLimpa = texto.trim();
        for (DateTimeFormatter fmt : FORMATOS_HORA) {
            try {
                return Optional.of(LocalTime.parse(horaLimpa, fmt));
            } catch (Exception ignored) {
            }
        }
        return Optional.empty();
    }

    public static int idade(LocalDate dataNascimento){
        if (dataNascimento == null) return 0;
        LocalDate dataAtual = LocalDate.now();
        Period periodo = Period.between(dataNascimento, dataAtual);
        return periodo.getYears();
    }
}
